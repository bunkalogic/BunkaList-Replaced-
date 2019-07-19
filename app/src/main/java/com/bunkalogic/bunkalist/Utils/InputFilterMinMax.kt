package com.bunkalogic.bunkalist.Utils

import android.text.Spanned
import android.text.InputFilter


class InputFilterMinMax(private val min: Double, private val max: Double) : InputFilter {

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {

        try {
            val input =
                Integer.parseInt(dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length))
            if (isInRange(min.toInt(), max.toInt(), input))
                return null
        } catch (nfe: NumberFormatException) {
        }

        return ""
    }

    private fun isInRange(a: Int, b: Int, c: Int): Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}