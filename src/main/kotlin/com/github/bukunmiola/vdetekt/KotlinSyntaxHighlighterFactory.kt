package com.github.bukunmiola.vdetekt

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory
import com.intellij.openapi.fileTypes.SyntaxHighlighter

class KotlinSyntaxHighlighterFactory : SingleLazyInstanceSyntaxHighlighterFactory() {
    override fun createHighlighter(): SyntaxHighlighter {
        return KotlinSyntaxHighlighter()
    }
}