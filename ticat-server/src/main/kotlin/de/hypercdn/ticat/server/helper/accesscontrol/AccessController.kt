package de.hypercdn.ticat.server.helper.accesscontrol

interface AccessRequest

interface AccessorContainer {
    fun isEmpty(): Boolean
}

interface AccessRule<T, R, A> where R : AccessRequest, A : AccessorContainer{
    fun isApplicableForRequest(request: R): Boolean
    fun testGrant(instance: T?, request: R, accessorContainer: A): Boolean
}

abstract class AccessController<T, R, A> where R : AccessRequest, A : AccessorContainer {

    abstract val accessRules: List<AccessRule<T, R, A>>

    fun hasAccess(instance: T?, request: R, accessor: A): Boolean = accessRules
        .filter { it.isApplicableForRequest(request) }
        .all { it.testGrant(instance, request, accessor) }
}