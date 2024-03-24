package de.hypercdn.ticat.server.helper.locale

import java.util.*

fun Locale.asLanguageLocale(): Locale = Locale.of(this.language)