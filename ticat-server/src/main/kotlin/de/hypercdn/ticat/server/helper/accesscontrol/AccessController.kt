package de.hypercdn.ticat.server.helper.accesscontrol

import de.hypercdn.ticat.server.helper.rule.Rule
import de.hypercdn.ticat.server.helper.rule.RuleContext
import de.hypercdn.ticat.server.helper.rule.RuleInput
import mu.two.KLogger
import mu.two.KotlinLogging

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
    fun testGrant(entity: E?, request: R, accessorContainer: A): Boolean = evaluate(AccessRuleInput(entity, request, accessorContainer))

    override fun newContext(input: AccessRuleInput<E, R, A>?): AccessRuleContext<E, R, A> = AccessRuleContext(input)
}

abstract class AccessController<T, R, A> where R : AccessRequest, A : AccessorContainer {
    protected val logger: KLogger = KotlinLogging.logger{}
    abstract val accessRules: List<AccessRule<T, R, A>>

    fun hasAccess(instance: T?, request: R, accessor: A): Boolean {
        logger.debug{"Requesting access for $request on $instance by $accessor"}
        val applicableRules = accessRules
            .filter { it.isApplicableForRequest(request) }
        logger.debug{"Found ${applicableRules.size} access rules to match"}
        return applicableRules
            .all { it.testGrant(instance, request, accessor) }
    }
}