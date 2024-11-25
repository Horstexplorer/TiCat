package de.hypercdn.ticat.server.config.messages

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConfigurationProperties(prefix = "ticat.localization")
class LocalizationConfig {

    var defaultLanguage: Locale = Locale.ENGLISH
    var allowWorkspaceSpecificLocalizationOverride: Boolean = true
    var allowUserSpecificLocalization: Boolean = true

}