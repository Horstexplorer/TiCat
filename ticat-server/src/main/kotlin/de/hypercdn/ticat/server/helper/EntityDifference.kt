package de.hypercdn.ticat.server.helper

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.collect.MapDifference
import com.google.common.collect.Maps
import de.hypercdn.ticat.server.config.configuredObjectMapper

fun calculateDifferenceBetween(first: Any, second: Any, mapper: ObjectMapper = configuredObjectMapper()): MapDifference<*, *> {
    return Maps.difference(mapper.convertValue(first, Map::class.java), mapper.convertValue(second, Map::class.java))
}