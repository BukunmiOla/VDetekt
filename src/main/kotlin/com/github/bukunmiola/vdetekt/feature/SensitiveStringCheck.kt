package com.github.bukunmiola.vdetekt.feature

import com.github.bukunmiola.vdetekt.extensions.isEncryptedString
import com.github.bukunmiola.vdetekt.extensions.isPossibleApiKeyOrSecretKey
import com.github.bukunmiola.vdetekt.utils.CustomHighlightingAttributes
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtStringTemplateExpression

class SensitiveStringCheck: Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtStringTemplateExpression) {
            element.checkIsSpecial(holder)
            element.checkIsSecretKey(holder)
            element.checkIsNotEncrypted(holder)
        }
    }


    private fun KtStringTemplateExpression.checkIsSpecial(holder: AnnotationHolder){
        val value: String = text.removeSurrounding("\"")
        if (value=="special") {
//            holder.newAnnotation(HighlightSeverity.ERROR, "Hardcoded Secret key, use local properties")
//                .range(this)
//                .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
//                .highlightType(ProblemHighlightType.ERROR)
//                .create()
        }
    }

    private fun KtStringTemplateExpression.checkIsSecretKey(holder: AnnotationHolder){
        val value: String = text.removeSurrounding("\"")
        if (value.isPossibleApiKeyOrSecretKey()) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Hardcoded Secret key, use local properties")
                .range(this)
                .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
                .highlightType(ProblemHighlightType.ERROR)
                .create()
        }
    }

    private fun KtStringTemplateExpression.checkIsNotEncrypted(holder: AnnotationHolder){
        val value: String = text.removeSurrounding("\"")
        if (!value.isEncryptedString()) {
//            holder.newAnnotation(HighlightSeverity.ERROR, "Encrypted string")
//                .range(this)
//                .textAttributes(CustomHighlightingAttributes.RED_UNDERLINE)
//                .highlightType(ProblemHighlightType.ERROR)
//                .create()
        }
    }

}