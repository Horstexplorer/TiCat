package de.hypercdn.ticat.server.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.PropertyWriter
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaField

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return configuredObjectMapper()
    }

}

fun configuredObjectMapper(): ObjectMapper {
    return jacksonObjectMapper()
        .setFilterProvider(
            SimpleFilterProvider()
                .addFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER, OmitUninitializedLateInitFieldsFilter())
        )
}

const val OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER: String = "omitUninitializedLateInitFields"
class OmitUninitializedLateInitFieldsFilter : SimpleBeanPropertyFilter() {
    override fun serializeAsField(pojo: Any?, jgen: JsonGenerator?, provider: SerializerProvider?, writer: PropertyWriter?) {
        if (!pojo!!::class.declaredMemberProperties.any { it.isLateinit && it.javaField?.get(pojo) == null && it.name == writer?.name }) {
            super.serializeAsField(pojo, jgen, provider, writer)
        }
    }

}
