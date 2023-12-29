package de.hypercdn.ticat.server.data.helper

abstract class EntityTemplateBuilder<B : EntityTemplateBuilder<B, T>, T>(
    private val templateSupplier: ()->T,
    private var template: T = templateSupplier()
) {
    fun modify(skip: Boolean = false, modificationScope: (T)->Unit): B = this.apply {
        if (!skip)
            modificationScope(template)
    } as B

    fun build(): T = template
    fun reset() {
        template = templateSupplier()
    }
}