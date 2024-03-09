package de.hypercdn.ticat.server.helper.difference

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.Maps
import de.hypercdn.ticat.server.config.configuredObjectMapper

interface Difference {
    companion object {
        fun <T> between(first: T, second: T, objectMapper: ObjectMapper = configuredObjectMapper()): EntityDifference<T> where T : Any =
            EntityDifference(first.javaClass, Maps.difference(objectMapper.convertValue(first, Map::class.java), objectMapper.convertValue(second, Map::class.java)))
    }
}