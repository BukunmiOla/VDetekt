package com.github.bukunmiola.vdetekt.feature

import com.github.bukunmiola.vdetekt.extensions.hasUnsanitizedText
import com.github.bukunmiola.vdetekt.extensions.isEncryptedString
import com.github.bukunmiola.vdetekt.extensions.isPossibleApiKeyOrSecretKey
import com.github.bukunmiola.vdetekt.extensions.isSQLQuery
import com.github.bukunmiola.vdetekt.utils.CustomHighlightingAttributes
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.completion.argList
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.psiUtil.referenceExpression

class MethodAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtNamedFunction) {
            if (element.referenceExpression()?.text == "setJavaScriptEnabled" && element.argList?.args?.firstChild?.text == "true") {
                val message = "Enabling JavaScript can lead to XSS vulnerabilities"
                holder.newAnnotation(HighlightSeverity.WARNING, message)
                    .range(element)
                    .textAttributes(CustomHighlightingAttributes.YELLOW_UNDERLINE)
                    .highlightType(ProblemHighlightType.WARNING)
                    .create()
            }
            if (
                (element.referenceExpression()?.text == "putString" || element.referenceExpression()?.text == "saveString") &&
                element.argList?.args?.arguments?.get(1)?.text?.isEncryptedString() == false
            ) {
                val message = "Storing data without encryption \nEncrypt the data before saving to ensure raw data aren't fetched directly."
                holder.newAnnotation(HighlightSeverity.WARNING, message)
                    .range(element)
                    .textAttributes(CustomHighlightingAttributes.YELLOW_UNDERLINE)
                    .highlightType(ProblemHighlightType.WARNING)
                    .create()
            }

            if (element.referenceExpression()?.text.equals("Log", true) &&
                element.argList?.args?.arguments?.get(1)?.text?.contains("user", true) == true &&
                element.argList?.args?.arguments?.get(2)?.text?.isEncryptedString() == false
            ){
                val message = "Writing sensitive information to log files may potentially" +
                        "guide an attacker or expose sensitive user information"
                holder.newAnnotation(HighlightSeverity.WARNING, message)
                    .range(element)
                    .textAttributes(CustomHighlightingAttributes.YELLOW_UNDERLINE)
                    .highlightType(ProblemHighlightType.WARNING)
                    .create()
            }
        }
    }
}