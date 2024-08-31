package de.hypercdn.ticat.server.helper.rule

import de.hypercdn.ticat.server.helper.exception.RuntimeExceptionWithoutStacktrace
import mu.two.KLogger
import mu.two.KotlinLogging

class ContextStopEvaluationException(description: String) : RuntimeExceptionWithoutStacktrace(description)

class ConditionResult(val description: String, val success: Boolean) {
    var skip: Boolean = false
    override fun toString(): String = "Condition: $description ${if (!skip) "($success)" else "(skip)"}"
}

interface RuleInput

class EvaluationReport(private val ruleHint: String?, private val results: List<ConditionResult>) {
    fun results(): List<ConditionResult> = results.toList()
    fun failedConditions(): List<ConditionResult> = results .filter { !it.skip && !it.success }
    fun booleanResult(): Boolean = results.any { !it.skip } && results.filter { !it.skip }.all { it.success }

    override fun toString(): String = "Rule evaluation report:\n$ruleHint > success= ${booleanResult()})\n${results.joinToString("\n>", prefix = ">")}"
}

open class RuleContext<T>(val input: T?) where T : RuleInput {

    private val resultCache = ArrayList<ConditionResult>()
    private val logger: KLogger = KotlinLogging.logger {}

    fun condition(
        description: String,
        conditionSucceedsWith: Boolean = true,
        terminateIfSuccess: Boolean = false,
        terminateIfFailed: Boolean = false,
        skipIfNoExit: Boolean = false,
        condition: () -> Boolean?
    ) {
        val result = registerResult(description, conditionSucceedsWith == condition())
        if (result.success) {
            logger.trace { "Condition ${description} matched." }
            if (terminateIfSuccess)
                exitEvaluation("Caused by matching condition $description")
        } else {
            logger.trace { "Condition ${result.description} failed to match. ($conditionSucceedsWith was not ${!conditionSucceedsWith})" }
            if (terminateIfFailed)
                exitEvaluation("Caused by failure to match condition $description")
        }
        result.skip = skipIfNoExit
    }

    fun exitEvaluation(message: String = "No exit message specified") {
        throw ContextStopEvaluationException(message)
    }

    fun registerResult(description: String, success: Boolean): ConditionResult =
        ConditionResult(description, success).also { resultCache.add(it) }

    fun exitWithSuccess(description: String, conditionSucceedsWith: Boolean = true, condition: () -> Boolean?) =
        condition(
            description,
            conditionSucceedsWith = conditionSucceedsWith,
            terminateIfSuccess = true,
            terminateIfFailed = false,
            skipIfNoExit = true,
            condition
        )

    fun exitWithSuccessIfTrue(description: String, condition: () -> Boolean?) =
        exitWithSuccess(description, conditionSucceedsWith = true, condition)

    fun exitWithSuccessIfFalse(description: String, condition: () -> Boolean?) =
        exitWithSuccess(description, conditionSucceedsWith = false, condition)

    fun exitWithSuccess(description: String = "Exit with success") = exitWithSuccessIfTrue(description){true}

    fun exitWithFailure(description: String, conditionSucceedsWith: Boolean = true, condition: () -> Boolean?) =
        condition(
            description,
            conditionSucceedsWith = conditionSucceedsWith,
            terminateIfSuccess = false,
            terminateIfFailed = true,
            skipIfNoExit = true,
            condition
        )

    fun exitWithFailureIfTrue(description: String, condition: () -> Boolean?) =
        exitWithFailure(description, conditionSucceedsWith = false, condition)

    fun exitWithFailureIfFalse(description: String, condition: () -> Boolean?) =
        exitWithFailure(description, conditionSucceedsWith = true, condition)

    fun exitWithFailure(description: String = "Exit with failure") = exitWithFailureIfFalse(description){false}

    fun asEvaluationResults() : EvaluationReport = EvaluationReport(this::class.simpleName, resultCache)
}

abstract class Rule<T, I> where T : RuleContext<I>, I : RuleInput {

    fun evaluate(input: I?): EvaluationReport {
        val context = newContext(input)
        try {
            context.apply(definition())
        } catch (_: ContextStopEvaluationException) {}
        return context.asEvaluationResults()
    }

    abstract fun newContext(input: I?): T

    abstract fun definition(): T.() -> Unit
}