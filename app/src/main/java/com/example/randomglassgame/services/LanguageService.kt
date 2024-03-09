package com.example.randomglassgame.services

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

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

        fun setLocale(language: Language) {
            val appLocale = LocaleListCompat.forLanguageTags(languages[language])
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        fun getCurrentLanguage(context: Context): Language {
            val locale = context.resources.configuration.locales[0]
            return when (locale.language) {
                "ar" -> Language.ARABIC
                "de" -> Language.GERMAN
                "eo" -> Language.ESPERANTO
                "es" -> Language.SPANISH
                "hi" -> Language.HINDI
                "ja" -> Language.JAPANESE
                "pl" -> Language.POLISH
                "pt" -> Language.PORTUGUESE
                "uk" -> Language.UKRAINIAN
                "zh" -> Language.CHINESE
                else -> Language.ENGLISH
            }
        }

    }

}