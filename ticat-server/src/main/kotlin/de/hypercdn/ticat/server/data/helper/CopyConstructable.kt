package de.hypercdn.ticat.server.data.helper

import java.io.InvalidClassException
import kotlin.reflect.full.createType

interface CopyConstructable<T : CopyConstructable<T>> {
    fun copy(): T {
        val constructor = this::class.constructors
            .firstOrNull() { c ->
                c.parameters.size == 1
                    && c.parameters[0].type == this::class.createType()
            } ?: throw InvalidClassException("Class does not implement a copy constructor")
        return constructor.call(this) as T
    }
}
