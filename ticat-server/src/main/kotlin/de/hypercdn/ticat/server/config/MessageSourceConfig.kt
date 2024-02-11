package de.hypercdn.ticat.server.config

import de.hypercdn.ticat.server.helper.ExtendedReloadableResourceBundleMessageSource
import de.hypercdn.ticat.server.helper.YamlPropertiesLoader
import jakarta.annotation.PostConstruct
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*


@Configuration
class MessageSourceConfig {

    @Bean
    fun messageSource(): MessageSource = ExtendedReloadableResourceBundleMessageSource().apply {
        setBasenames("classpath:messages/translation")
        setFileExtensions(listOf(".yaml"))
        setPropertiesPersister(YamlPropertiesLoader())
        setDefaultEncoding("UTF-8")
        setUseCodeAsDefaultMessage(true)
        setDefaultLocale(null)
        setFallbackToSystemLocale(false)
    }

}

fun MessageSource.getMessage(code: String, locale: Locale): String = getMessage({ arrayOf(code) }, locale)

@Component
class Test(val messageSource: MessageSource) {

    @PostConstruct
    fun x() {
        (messageSource as ExtendedReloadableResourceBundleMessageSource).getAllKeys(Locale.ENGLISH)?.forEach(System.out::println)
        System.exit(0)
    }

}

class MessageKeys {
    companion object {
        const val DETAILS_PREFIX: String = "details"
        const val SERVER_PREFIX: String = "server"
        const val FRONTEND_PREFIX: String = "frontend"

        const val DETAILS_LANGUAGE: String = "$DETAILS_PREFIX.language"
        const val DETAILS_ENABLED: String = "$DETAILS_PREFIX.enabled"
    }
}