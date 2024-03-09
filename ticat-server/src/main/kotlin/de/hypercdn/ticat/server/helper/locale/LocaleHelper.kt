package de.hypercdn.ticat.server.helper.locale

import java.util.Locale

fun Locale.asLanguageLocale(): Locale = Locale.of(this.language)