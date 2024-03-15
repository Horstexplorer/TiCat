package de.hypercdn.ticat.server.helper.difference

import com.google.common.collect.MapDifference

class EntityDifference<T> (
    val clazz: Class<T>,
    val left: T,
    val right: T,
    val difference: MapDifference<*,*>
) where T : Any

