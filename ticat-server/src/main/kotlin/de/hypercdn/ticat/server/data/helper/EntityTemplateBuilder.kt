package de.hypercdn.ticat.server.data.helper

abstract class EntityTemplateBuilder<T>(
    private val templateSupplier: ()->T,
    private var template: T = templateSupplier()
) {
    fun manualEdit(edit: (T)->T) {
        template = edit(template)
    }
    fun build(): T = template
    fun reset() {
        template = templateSupplier()
    }
}