package de.hypercdn.ticat.server.helper.accesscontrol

import de.hypercdn.ticat.server.helper.rule.EvaluationReport
import de.hypercdn.ticat.server.helper.rule.Rule
import de.hypercdn.ticat.server.helper.rule.RuleContext
import de.hypercdn.ticat.server.helper.rule.RuleInput
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging

interface AccessRequest

interface AccessorContainer {
    fun isEmpty(): Boolean
}

class AccessRuleInput<E, R, A>(
    val entity: E?,
    val request: R,
    val accessor: A
): RuleInput

class AccessRuleContext<E, R, A>(input: AccessRuleInput<E, R, A>?): RuleContext<AccessRuleInput<E, R, A>>(input)

abstract class AccessRule<E, R, A>: Rule<AccessRuleContext<E, R, A>, AccessRuleInput<E, R, A>>() where R : AccessRequest, A : AccessorContainer {
    abstract fun isApplicableForRequest(request: R): Boolean
    fun testGrant(entity: E?, request: R, accessorContainer: A): EvaluationReport = evaluate(AccessRuleInput(entity, request, accessorContainer))

    override fun newContext(input: AccessRuleInput<E, R, A>?): AccessRuleContext<E, R, A> = AccessRuleContext(input)
}

abstract class AccessController<T, R, A> where R : AccessRequest, A : AccessorContainer {
    protected val logger: KLogger = KotlinLogging.logger{}
    abstract val accessRules: List<AccessRule<T, R, A>>

    fun hasAccess(instance: T?, request: R, accessor: A): Boolean {
        logger.debug{"Requesting access for $request on $instance by $accessor"}
        val applicableRules = accessRules
            .filter { it.isApplicableForRequest(request) }
        if (applicableRules.isNotEmpty()) {
            logger.debug{"Found ${applicableRules.size} access rules to match $request"}
        } else {
            logger.warn { "No access rules found for $request" }
        }
        val evaluationReports = applicableRules.map { it.testGrant(instance, request, accessor) }
        logger.trace{ evaluationReports.joinToString { "\n" } }
        return evaluationReports.all { it.booleanResult() }
    }
}