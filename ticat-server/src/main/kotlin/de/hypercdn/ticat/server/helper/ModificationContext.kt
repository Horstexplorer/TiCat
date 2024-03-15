package de.hypercdn.ticat.server.helper

import de.hypercdn.ticat.server.data.sql.base.entity.BaseEntity
import de.hypercdn.ticat.server.helper.constructor.CopyConstructable
import de.hypercdn.ticat.server.helper.difference.Difference
import de.hypercdn.ticat.server.helper.difference.EntityDifference
import java.util.function.BiFunction

class ModificationContext<T>(
    private var original: T,
    private var snapshotCopies: ArrayList<T> = ArrayList()
) where T : CopyConstructable<T> {

    private fun createSnapshot(entity: T): T {
        val copy = entity.copy()
        snapshotCopies.add(copy)
        return copy
    }

    fun lastSnapshot(): T = snapshotCopies.last()

    fun original(): T = original

    fun modifyOriginal(consumer: (original: T) -> Unit): ModificationContext<T> {
        createSnapshot(original)
        consumer(original)
        return this
    }

    fun modifyCopy(consumer: (copy: T) -> Unit): ModificationContext<T> {
        val base = if (snapshotCopies.isNotEmpty())
            snapshotCopies.last()
        else
            createSnapshot(original)
        val copy = createSnapshot(base)
        consumer(copy)
        return this
    }

    fun modifyCopyOfLatestSnapshot(consumer: (copy: T) -> Unit): ModificationContext<T> {
        val copy = if (snapshotCopies.isNotEmpty())
            snapshotCopies.last()
        else
            createSnapshot(original)
        consumer(copy)
        return this
    }

    fun getAbsoluteDifference(mappingLeft: BiFunction<T,T,T> = BiFunction { l, _ -> l }, mappingRight: BiFunction<T,T,T> = BiFunction { _, r -> r }): EntityDifference<T> =
        Difference.between(mappingLeft.apply(original, lastSnapshot()), mappingRight.apply(original, lastSnapshot()))

    fun getIncrementalDifference(mappingLeft: BiFunction<T,T,T> = BiFunction { l, _ -> l }, mappingRight: BiFunction<T,T,T> = BiFunction { _, r -> r }): List<EntityDifference<T>> = ArrayList<T>()
        .apply {
            add(original)
            addAll(snapshotCopies)
        }
        .zipWithNext()
        .map {  Difference.between(mappingLeft.apply(it.first, it.second), mappingRight.apply(it.first, it.second)) }

}

fun <T> T.modifyWithContext() : ModificationContext<T> where T: BaseEntity<T> = ModificationContext(this)