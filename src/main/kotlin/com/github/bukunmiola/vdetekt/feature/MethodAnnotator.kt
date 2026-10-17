package com.github.bukunmiola.vdetekt.feature

import com.github.bukunmiola.vdetekt.extensions.isEncryptedString
import com.github.bukunmiola.vdetekt.utils.CustomHighlightingAttributes
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtCallExpression

class MethodAnnotator: Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is KtCallExpression) {
            val methodName = element.calleeExpression?.text
            val arguments = element.valueArguments.map { it.getArgumentExpression() }
            if (methodName == "setJavaScriptEnabled" && arguments.getOrNull(0)?.text == "true") {
                val message = "Enabling JavaScript can lead to XSS vulnerabilities"
                holder.newAnnotation(HighlightSeverity.WARNING, message)
                    .range(element)
                    .textAttributes(CustomHighlightingAttributes.YELLOW_UNDERLINE)
                    .highlightType(ProblemHighlightType.WARNING)
                    .create()
            }
            if (
                (methodName == "putString" || methodName == "saveString") &&
                arguments.getOrNull(1)?.text?.isEncryptedString() == false
            ) {
                val message = "Storing data without encryption \nEncrypt the data before saving to ensure raw data aren't fetched directly."
                holder.newAnnotation(HighlightSeverity.WARNING, message)
                    .range(element)
                    .textAttributes(CustomHighlightingAttributes.YELLOW_UNDERLINE)
                    .highlightType(ProblemHighlightType.WARNING)
                    .create()
            }

            if (methodName.equals("Log", true) &&
                arguments.getOrNull(1)?.text?.contains("user", true) == true &&
                arguments.getOrNull(2)?.text?.isEncryptedString() == false
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
