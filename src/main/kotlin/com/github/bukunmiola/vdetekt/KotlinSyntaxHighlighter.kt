package com.github.bukunmiola.vdetekt

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.kotlin.lexer.KotlinLexer
import org.jetbrains.kotlin.lexer.KtTokens

class KotlinSyntaxHighlighter : SyntaxHighlighterBase() {

    override fun getHighlightingLexer(): Lexer {
        return KotlinLexer()
    }

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            KtTokens.FUN_KEYWORD -> arrayOf(CustomHighlightingAttributes.FUN_KEYWORD)
            KtTokens.VAR_KEYWORD -> arrayOf(CustomHighlightingAttributes.VAR_KEYWORD)
            // Add more cases here for other keywords or tokens you want to highlight
            else -> emptyArray()
        }
    }
}