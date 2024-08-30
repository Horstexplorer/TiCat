package de.hypercdn.ticat.server.helper.exception

open class ExceptionWithoutStacktrace : Exception {
    constructor() : super(null, null, true, false)
    constructor(cause: Throwable) : super(null, cause, true, false)
    constructor(message: String?) : super(message, null, true, false)

    override fun fillInStackTrace(): Throwable = this
}

open class RuntimeExceptionWithoutStacktrace : RuntimeException {
    constructor() : super(null, null, true, false)
    constructor(cause: Throwable) : super(null, cause, true, false)
    constructor(message: String?) : super(message, null, true, false)

    override fun fillInStackTrace(): Throwable = this
}