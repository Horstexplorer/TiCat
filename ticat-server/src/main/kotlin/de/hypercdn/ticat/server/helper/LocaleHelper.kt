package de.hypercdn.ticat.server.helper

import java.util.Locale

fun Locale.asLanguageLocale(): Locale = Locale.of(this.language)