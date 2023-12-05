package de.hypercdn.ticat.server.data.helper

import lombok.extern.slf4j.Slf4j
import javax.swing.text.html.parser.Entity

@Slf4j
class UpdateContext<T : CopyConstructable<T>>(
    var original: T,
    var snapshotCopies: ArrayList<T> = ArrayList()
) {

    private fun createSnapshot(entity: T): T {
        val copy = entity.copy()
        snapshotCopies.add(copy)
        return copy;
    }

    fun lastSnapshot(): T {
        return snapshotCopies.last()
    }

    fun modifyOriginal(consumer: (original: T) -> Unit): UpdateContext<T> {
        createSnapshot(original)
        consumer(original)
        return this
    }

    fun modifyCopyOfOriginal(consumer: (copy: T) -> Unit): UpdateContext<T> {
        val copy = createSnapshot(original)
        consumer(copy)
        return this
    }

    fun modifyCopyOfLatestSnapshot(consumer: (copy: T) -> Unit): UpdateContext<T> {
        val copy = if (snapshotCopies.isNotEmpty())
            snapshotCopies.last()
        else
            createSnapshot(original)
        consumer(copy)
        return this
    }

}