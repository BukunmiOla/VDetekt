package com.github.bukunmiola.vdetekt

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtConstantExpression
import org.jetbrains.kotlin.psi.KtLiteralStringTemplateEntry
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtProperty

class KotlinAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element){
            is KtNamedFunction -> annotateFunction(element, holder)
            is KtProperty -> annotateProperty(element, holder)
            is KtLiteralStringTemplateEntry -> annotateString(element, holder)
            is KtConstantExpression -> annotateConstantExpression(element, holder)
        }
    }

    private fun annotateString(element: KtLiteralStringTemplateEntry, holder: AnnotationHolder) {
        val literalExpression: KtLiteralStringTemplateEntry = element
        val value: String = literalExpression.text
        if (value.contains("specialKeyword")) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Special keyword not allowed here")
                .range(element)
                .textAttributes(DefaultLanguageHighlighterColors.KEYWORD)
                .create()
            holder.newSilentAnnotation(HighlightSeverity.ERROR)
                .textAttributes(CustomHighlightingAttributes.BACKGROUND_COLOR)
                .create()
            holder.newSilentAnnotation(HighlightSeverity.ERROR)
                .range(element)
                .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
                .create()
        }
    }

    private fun annotateFunction(function: KtNamedFunction, holder: AnnotationHolder) {
        val functionName = function.nameIdentifier
        if (functionName != null) {
            holder.newAnnotation(HighlightSeverity.INFORMATION, "Function: ${function.name}")
                .range(function.nameIdentifier!!)
                .textAttributes(CustomHighlightingAttributes.FUNCTION_NAME)
                .create()
        }
    }

    private fun annotateProperty(property: KtProperty, holder: AnnotationHolder) {
        val propertyName = property.nameIdentifier
        if (propertyName != null) {
            holder.newAnnotation(HighlightSeverity.INFORMATION, "Property: ${property.name}")
                .range(property.nameIdentifier!!)
                .textAttributes(CustomHighlightingAttributes.PROPERTY_NAME)
                .create()
        }
    }

    private fun annotateConstantExpression(expression: KtConstantExpression, holder: AnnotationHolder) {
        val message = "Constant: ${expression.text}"
        holder.newAnnotation(HighlightSeverity.INFORMATION, message)
            .textAttributes(CustomHighlightingAttributes.CONSTANT)
            .create()
    }
}
