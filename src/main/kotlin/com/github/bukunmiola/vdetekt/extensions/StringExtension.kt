package com.github.bukunmiola.vdetekt.extensions

import ai.grazie.nlp.utils.dropWhitespaces
import org.jetbrains.kotlin.idea.base.util.collapseSpaces
import java.util.*
import kotlin.math.log2

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

fun String.isBase64Encoded(): Boolean {
    return try {
        val decoded = Base64.getDecoder().decode(this)
        Base64.getEncoder().encodeToString(decoded) == this
    } catch (e: IllegalArgumentException) {
        false
    }
}

fun String.calculateEntropy(): Double {
    val frequency = this.groupingBy { it }.eachCount().mapValues { it.value.toDouble() / length }
    return -frequency.values.sumByDouble { it * log2(it) }
}

fun String.isEncryptedString(): Boolean {
    return isBase64Encoded() || calculateEntropy() > 4.5
}

fun String.isPossibleApiKeyOrSecretKey(): Boolean {
    // Common patterns
    val commonPatterns = listOf(
        "^[A-Za-z0-9_-]{20,128}$", // Alphanumeric with underscores and dashes, length between 32 and 64
        "^sk_live_[A-Za-z0-9]{24,}$", // Example: Stripe secret key
        "^AIza[0-9A-Za-z-_]{35}$", // Example: Google API key
        "^AKIA[0-9A-Z]{16}$", // Example: AWS access key
        "^v1\\.[0-9a-f]{40}\\.[0-9a-f]{40}$" // Example: Generic token with specific format
    )

    // Check against common patterns
    for (pattern in commonPatterns) {
        if (Regex(pattern).matches(this)) {
            return true
        }
    }
    return false
}