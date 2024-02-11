package de.hypercdn.ticat.server.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.hypercdn.ticat.server.helper.NULL_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER
import de.hypercdn.ticat.server.helper.UninitializedLateInitFieldAwareFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
                .addFilter(OMIT_UNINITIALIZED_LATEINIT_FIELDS_FILTER, UninitializedLateInitFieldAwareFilter(
                    UninitializedLateInitFieldAwareFilter.UninitializedLateInitHandling.OMIT_UNINITIALIZED_LATE_INIT
                )
                )
                .addFilter(NULL_UNINITIALIZED_LATEINIT_FIELDS_FILTER, UninitializedLateInitFieldAwareFilter(
                    UninitializedLateInitFieldAwareFilter.UninitializedLateInitHandling.NULL_UNINITIALIZED_LATE_INIT
                ))
        )
}