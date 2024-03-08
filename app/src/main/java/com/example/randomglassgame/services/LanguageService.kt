package com.example.randomglassgame.services

import java.util.Locale
import android.content.Context

enum class Language {
    ENGLISH,
    ARABIC,
    GERMAN,
    ESPERANTO,
    SPANISH,
    HINDI,
    JAPANESE,
    POLISH,
    PORTUGUESE,
    UKRAINIAN,
    CHINESE
}

class LanguageService {

    companion object {

        private var languages: Map<Language, String> =
            mutableMapOf(
                Language.ENGLISH to "en",
                Language.ARABIC to "ar",
                Language.GERMAN to "de",
                Language.ESPERANTO to "eo",
                Language.SPANISH to "es",
                Language.HINDI to "hi",
                Language.JAPANESE to "ja",
                Language.POLISH to "pl",
                Language.PORTUGUESE to "pt",
                Language.UKRAINIAN to "uk",
                Language.CHINESE to "zh"
            )

        fun setLocale(language: Language, context: Context) {
            val config = context.resources.configuration

            Locale(languages[language]!!).apply {
                Locale.setDefault(this@apply)
                config.setLocale(this@apply)
            }

            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }

    }


}