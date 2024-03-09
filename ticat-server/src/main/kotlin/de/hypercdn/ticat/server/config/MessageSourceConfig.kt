package de.hypercdn.ticat.server.config

import de.hypercdn.ticat.server.helper.locale.ExtendedReloadableResourceBundleMessageSource
import de.hypercdn.ticat.server.helper.locale.YamlPropertiesLoader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*


@Configuration
class MessageSourceConfig {

    @Autowired
    lateinit var localizationConfig: LocalizationConfig

    @Bean
    fun messageSource(): MessageSource = ExtendedReloadableResourceBundleMessageSource(localizationConfig).apply {
        setBasenames("classpath:messages/translation")
        setFileExtensions(listOf(".yaml"))
        setPropertiesPersister(YamlPropertiesLoader())
        setDefaultEncoding("UTF-8")
        setUseCodeAsDefaultMessage(true)
        setFallbackToSystemLocale(false)
    }

}

fun MessageSource.getMessage(code: String, locale: Locale): String = getMessage({ arrayOf(code) }, locale)

class MessageKeys {
    companion object {
        const val DETAILS_PREFIX: String = "details"
        const val SERVER_PREFIX: String = "server"
        const val FRONTEND_PREFIX: String = "frontend"

        const val DETAILS_LANGUAGE: String = "$DETAILS_PREFIX.language"
        const val DETAILS_ENABLED: String = "$DETAILS_PREFIX.enabled"
    }
}