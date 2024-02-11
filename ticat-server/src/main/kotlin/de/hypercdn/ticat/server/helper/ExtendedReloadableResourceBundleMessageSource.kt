package de.hypercdn.ticat.server.helper

import de.hypercdn.ticat.server.config.MessageKeys
import de.hypercdn.ticat.server.config.getMessage
import jakarta.annotation.PostConstruct
import mu.two.KLogger
import mu.two.KotlinLogging
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExtendedReloadableResourceBundleMessageSource : ReloadableResourceBundleMessageSource() {

    private lateinit var validLocales: Set<Locale>
    private lateinit var languageLocals: List<Locale>

    private val log: KLogger = KotlinLogging.logger{}

    @PostConstruct
    private fun postConstruct() {
        val foundLocales = ArrayList<Locale>()
        for (locale in Locale.getAvailableLocales()) {
            val isEnabled = getMessage(MessageKeys.DETAILS_ENABLED, locale)
            if (isEnabled != MessageKeys.DETAILS_ENABLED && isEnabled.toBoolean())
                foundLocales.addLast(locale)
        }
        languageLocals = foundLocales.filter { it.language == it.toLanguageTag() }.toSet().toList()
        validLocales = foundLocales.toSet()
        log.info{"Identified message sources for ${languageLocals.size} languages (${validLocales.size} valid locales)."}
        clearCacheIncludingAncestors()
    }

    fun getProperties(locale: Locale): Properties? {
        if (!validLocales.contains(locale))
            return null
        return getMergedProperties(locale).properties
    }

}