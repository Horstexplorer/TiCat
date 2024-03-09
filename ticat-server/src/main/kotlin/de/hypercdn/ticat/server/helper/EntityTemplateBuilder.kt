package de.hypercdn.ticat.server.helper

abstract class EntityTemplateBuilder<B, T>(
    private val templateSupplier: () -> T,
    private var template: T = templateSupplier()
) where B : EntityTemplateBuilder<B, T> {
    fun modify(skip: Boolean = false, modificationScope: (T) -> Unit): B = this.apply {
        if (!skip)
            modificationScope(template)
    } as B

    fun build(): T = template
    fun reset() {
        template = templateSupplier()
    }
}