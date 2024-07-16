package com.github.bukunmiola.vdetekt.feature

import com.github.bukunmiola.vdetekt.CustomHighlightingAttributes
import com.github.bukunmiola.vdetekt.extensions.hasUnsanitizedText
import com.github.bukunmiola.vdetekt.extensions.isSQLQuery
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry

class SQLInjectionCheck: Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtLiteralStringTemplateEntry) {
            val literalExpression: KtLiteralStringTemplateEntry = element
            val value: String = literalExpression.text
            if (value.isSQLQuery()) {
                if (value.hasUnsanitizedText()) {
                    holder.newAnnotation(HighlightSeverity.ERROR, "Unsafe usage of SQL query")
                        .range(element)
                        .textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
                        .create()
//                    holder.newSilentAnnotation(HighlightSeverity.ERROR)
//                        .textAttributes(CustomHighlightingAttributes.BACKGROUND_COLOR)
//                        .create()
                    holder.newSilentAnnotation(HighlightSeverity.ERROR)
                        .range(element)
                        .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
                        .create()
                }
            }
        }
    }

}