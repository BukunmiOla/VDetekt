package com.github.bukunmiola.vdetekt.feature

import com.github.bukunmiola.vdetekt.utils.CustomHighlightingAttributes
import com.github.bukunmiola.vdetekt.extensions.hasUnsanitizedText
import com.github.bukunmiola.vdetekt.extensions.isSQLQuery
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtStringTemplateExpression

class SQLInjectionCheck: Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtStringTemplateExpression) {
            val value: String = element.text.removeSurrounding("\"")
            if (value.isSQLQuery()) {
                if (value.hasUnsanitizedText()) {
                    val message = "Unsafe usage of SQL query.\n" +
                            "Consider extracting the value and do not directly set it in the query."
                    holder.newAnnotation(HighlightSeverity.ERROR, message)
                        .range(element)
                        .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
                        .highlightType(ProblemHighlightType.ERROR)
                        .create()
                }
            }
        }
    }

}