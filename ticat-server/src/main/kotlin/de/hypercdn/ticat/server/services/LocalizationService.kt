package de.hypercdn.ticat.server.services

import de.hypercdn.ticat.server.config.LocalizationConfig
import de.hypercdn.ticat.server.config.getMessage
import de.hypercdn.ticat.server.data.sql.entities.user.User
import de.hypercdn.ticat.server.data.sql.entities.workspace.Workspace
import de.hypercdn.ticat.server.helper.ExtendedReloadableResourceBundleMessageSource
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class LocalizationService(
    val localizationConfig: LocalizationConfig,
    val messageSource: ExtendedReloadableResourceBundleMessageSource
) {

    fun localeFromEntityContext(user: User): Locale = localeFromEntityContext(null, user)

    fun localeFromEntityContext(workspace: Workspace): Locale = localeFromEntityContext(workspace, null)

    fun localeFromEntityContext(workspace: Workspace? = null, user: User? = null): Locale {
        if (workspace != null && workspace.settings.locale != null && localizationConfig.allowWorkspaceSpecificLocalizationOverride)
            return workspace.settings.locale!!
        if (user != null && user.settings.locale != null && localizationConfig.allowUserSpecificLocalization)
            return user.settings.locale!!
        return localizationConfig.defaultLanguage
    }

    fun isValidLocale(locale: Locale): Boolean {
        return messageSource.containsLanguageFor(locale)
    }

    fun resolve(key: String, locale: Locale): String {
        return messageSource.getMessage(key, locale)
    }

}