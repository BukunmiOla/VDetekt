package com.github.bukunmiola.vdetekt.extensions

import ai.grazie.nlp.utils.dropWhitespaces
import org.jetbrains.kotlin.idea.base.util.collapseSpaces

fun String.isSQLQuery(): Boolean{
    return when{
        startsWith("select", true) -> true
        startsWith("UPDATE", true) -> true
        startsWith("DELETE", true) -> true
        startsWith("INSERT", true) -> true
        startsWith("CREATE", true) -> true
        startsWith("ALTER", true) -> true
        startsWith("DROP", true) -> true
        else -> false
    }
}

fun String.hasUnsanitizedText(): Boolean{
    val textWithoutSpaces = this.replace(" ", "")
        .replace("\n", "")
    val indices  = textWithoutSpaces.indexesOf("=")

    for (index in indices){
        if (textWithoutSpaces[index + 1] != '?'){
            return true
        }
    }
    return false
}

fun String.indexesOf(substr: String, ignoreCase: Boolean = false): List<Int> {
    tailrec fun String.collectIndexesOf(offset: Int = 0, indexes: MutableList<Int> = mutableListOf()): List<Int> =
        when (val index = indexOf(substr, offset, ignoreCase)) {
            -1 -> indexes
            else -> collectIndexesOf(index + substr.length, indexes.apply { add(index) })
        }

    return this.collectIndexesOf()
}