package com.github.bukunmiola.vdetekt.utils

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.markup.EffectType
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.ui.JBColor

object CustomHighlightingAttributes {
    val FUN_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_FUN_KEYWORD",
        DefaultLanguageHighlighterColors.KEYWORD
    )

    val VAR_KEYWORD: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_VAR_KEYWORD",
        DefaultLanguageHighlighterColors.KEYWORD
    )
    val FUNCTION_NAME: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_FUNCTION_NAME",
        DefaultLanguageHighlighterColors.FUNCTION_DECLARATION
    )

    val PROPERTY_NAME: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_PROPERTY_NAME",
        DefaultLanguageHighlighterColors.INSTANCE_FIELD
    )
    val CONSTANT: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_CONSTANT",
        DefaultLanguageHighlighterColors.CONSTANT
    )

    val STRING: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_STRING",
        DefaultLanguageHighlighterColors.STRING
    )
    // Define attributes for background color and red underline
    val BACKGROUND_COLOR: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_BACKGROUND_COLOR",
        TextAttributes(JBColor.BLACK, JBColor.YELLOW, null, null, 0)
    )

    val RED_UNDERLINE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_RED_UNDERLINE",
        TextAttributes(JBColor.RED, JBColor.LIGHT_GRAY, JBColor.RED, EffectType.WAVE_UNDERSCORE, 0)
    )
    val YELLOW_UNDERLINE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "CUSTOM_RED_UNDERLINE",
        TextAttributes(null, JBColor.LIGHT_GRAY, JBColor.YELLOW, EffectType.WAVE_UNDERSCORE, 0)
    )
    // Define more attributes as needed
}