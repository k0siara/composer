package com.patrykkosieradzki.composer.utils

import android.content.res.Resources
import androidx.annotation.StringRes

@Suppress("DataClassPrivateConstructor")
data class TextModel private constructor(
    val text: String?,
    @StringRes val textResId: Int?,
    val args: Array<out Any>?,
) {
    companion object {
        val EMPTY = TextModel(text = "")
    }

    constructor(text: String) : this(text = text, textResId = null, args = null)
    constructor(text: String, vararg args: Any) : this(text = text, textResId = null, args = args)
    constructor(@StringRes textResId: Int, vararg args: Any) : this(text = null, textResId = textResId, args = args)

    fun resolveText(resources: Resources): String = when {
        textResId != null && args != null -> {
            val resolvedArgs = args.resolveTextModelArgs(resources = resources)
            resources.getString(textResId, *resolvedArgs)
        }
        text != null && args != null -> {
            val resolvedArgs = args.resolveTextModelArgs(resources = resources)
            String.format(text, resolvedArgs)
        }
        textResId != null -> resources.getString(textResId)
        text != null -> text
        else -> ""
    }

    private fun Array<out Any>.resolveTextModelArgs(resources: Resources): Array<Any> = map { arg ->
        if (arg is TextModel) {
            arg.resolveText(resources = resources)
        } else {
            arg
        }
    }.toTypedArray()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextModel

        if (text != other.text) return false
        if (textResId != other.textResId) return false
        if (args != null) {
            if (other.args == null) return false
            if (!args.contentEquals(other.args)) return false
        } else if (other.args != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text?.hashCode() ?: 0
        result = 31 * result + (textResId ?: 0)
        result = 31 * result + (args?.contentHashCode() ?: 0)
        return result
    }

}
