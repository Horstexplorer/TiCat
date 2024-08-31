package de.hypercdn.ticat.server.helper.rule

import de.hypercdn.ticat.server.helper.exception.RuntimeExceptionWithoutStacktrace
import mu.two.KLogger
import mu.two.KotlinLogging

class ContextStopEvaluationException(description: String) : RuntimeExceptionWithoutStacktrace(description)

class ConditionResult(val description: String, val success: Boolean)

interface RuleInput

open class RuleContext<T>(val input: T?) where T : RuleInput {

    private val resultCache = ArrayList<ConditionResult>()
    private val logger: KLogger = KotlinLogging.logger {}

    fun results(): List<ConditionResult> = resultCache.toList()
    fun failedConditions(): List<ConditionResult> = resultCache.filter { !it.success }
    fun booleanResult(): Boolean = resultCache.isNotEmpty() && resultCache.all { it.success }

    fun condition(
        description: String,
        conditionSucceedsWith: Boolean = true,
        terminateIfNotSuccess: Boolean = true,
        terminateIfSuccess: Boolean = false,
        condition: () -> Boolean?
    ) {
        val result = registerResult(description, conditionSucceedsWith == condition())
        if (result.success) {
            logger.trace { "Condition ${result.description} matched." }
            if (terminateIfSuccess)
                exitEvaluation("Caused by matching condition $description")
        } else {
            logger.trace { "Condition ${result.description} failed to match. ($conditionSucceedsWith was not ${!conditionSucceedsWith})" }
            if (terminateIfNotSuccess)
                exitEvaluation("Caused by failure to match condition $description")
        }
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
            terminateIfNotSuccess = false,
            terminateIfSuccess = true,
            condition
        )

    fun exitWithSuccessIfTrue(description: String, condition: () -> Boolean?) =
        exitWithSuccess(description, conditionSucceedsWith = true, condition)

    fun exitWithSuccessIfFalse(description: String, condition: () -> Boolean?) =
        exitWithSuccess(description, conditionSucceedsWith = false, condition)

    fun exitWithFailure(description: String, conditionSucceedsWith: Boolean = true, condition: () -> Boolean?) =
        condition(
            description,
            conditionSucceedsWith = conditionSucceedsWith,
            terminateIfNotSuccess = true,
            terminateIfSuccess = false,
            condition
        )

    fun exitWithFailureIfTrue(description: String, condition: () -> Boolean?) =
        exitWithFailure(description, conditionSucceedsWith = false, condition)

    fun exitWithFailureIfFalse(description: String, condition: () -> Boolean?) =
        exitWithFailure(description, conditionSucceedsWith = true, condition)

}

abstract class Rule<T, I> where T : RuleContext<I>, I : RuleInput {

    fun evaluate(input: I?): Boolean {
        val context = newContext(input)
        try {
            context.apply(definition())
        } catch (_: ContextStopEvaluationException) {}
        return context.booleanResult()
    }

    abstract fun newContext(input: I?): T

    abstract fun definition(): T.() -> Unit
}