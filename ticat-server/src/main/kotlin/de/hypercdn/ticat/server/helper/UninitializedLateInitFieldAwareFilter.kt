package de.hypercdn.ticat.server.helper

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

const val OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER: String = "omitUninitializedLateInitFields"
const val NULL_UNINITIALIZED_LATEINIT_FIELDS_FILTER: String = "nullUninitializedLateInitFields"

class UninitializedLateInitFieldAwareFilter(val mode: UninitializedLateInitHandling = UninitializedLateInitHandling.OMIT_UNINITIALIZED_LATE_INIT): SimpleBeanPropertyFilter() {
    enum class UninitializedLateInitHandling {
        OMIT_UNINITIALIZED_LATE_INIT, NULL_UNINITIALIZED_LATE_INIT
    }

    override fun serializeAsField(pojo: Any?, jgen: JsonGenerator?, provider: SerializerProvider?, writer: PropertyWriter?) {
        if (!pojo!!::class.declaredMemberProperties.any { it.isLateinit && it.javaField?.get(pojo) == null && it.name == writer?.name }) {
            super.serializeAsField(pojo, jgen, provider, writer)
        } else when (mode) {
            UninitializedLateInitHandling.NULL_UNINITIALIZED_LATE_INIT -> jgen?.writeNullField(writer!!.name)
            UninitializedLateInitHandling.OMIT_UNINITIALIZED_LATE_INIT -> jgen?.writeOmittedField(writer!!.name)
        }
    }
}