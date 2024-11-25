package de.hypercdn.ticat.server.helper.locale

import de.hypercdn.ticat.server.config.messages.LocalizationConfig
import de.hypercdn.ticat.server.config.messages.MessageKeys
import de.hypercdn.ticat.server.config.messages.getMessage
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.util.*
import kotlin.collections.ArrayList

class ExtendedReloadableResourceBundleMessageSource(
    val localizationConfig: LocalizationConfig
) : ReloadableResourceBundleMessageSource() {

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
        log.info{"Identified message sources for ${languageLocals.size} languages: ${languageLocals.joinToString()} (${validLocales.size} valid locales)."}
        clearCacheIncludingAncestors()
        if (!containsLanguageFor(localizationConfig.defaultLanguage)) {
            throw IllegalArgumentException("Configured default language ${localizationConfig.defaultLanguage} has not been found after loading localization filed.")
        }
        defaultLocale = localizationConfig.defaultLanguage
    }

    fun containsLanguageFor(locale: Locale): Boolean {
        return languageLocals.any { it.language == locale.language } || validLocales.contains(locale)
    }

    fun getProperties(locale: Locale): Properties? {
        if (!validLocales.contains(locale))
            return null
        return getMergedProperties(locale).properties
    }

}