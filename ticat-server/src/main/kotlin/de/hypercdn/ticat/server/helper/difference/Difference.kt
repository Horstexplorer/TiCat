package de.hypercdn.ticat.server.helper.difference

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.Maps
import de.hypercdn.ticat.server.config.mapping.configuredObjectMapper

interface Difference {
    companion object {
        fun <T> between(left: T, right: T, objectMapper: ObjectMapper = configuredObjectMapper()): EntityDifference<T> where T : Any =
            EntityDifference(left.javaClass, left, right, Maps.difference(objectMapper.convertValue(left, Map::class.java), objectMapper.convertValue(right, Map::class.java)))
    }
}