package com.github.bukunmiola.vdetekt

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralValue

class MyAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is PsiLiteralValue) {
            val literalExpression: PsiLiteralValue = element
            val value: String = literalExpression.text
            if (value.contains("specialKeyword")) { // Your condition here
                holder.newAnnotation(HighlightSeverity.INFORMATION, "Highlight message")
                    .range(element)
                    .textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
                    .create()
            }
        }
    }
}
