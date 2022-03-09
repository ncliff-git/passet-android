package com.github.ncliff.passet.data

import com.github.ncliff.passet.R

class ColorList {
    private val blackHex = "000000"
    private val whiteHex = "FFFFFF"

    val defaultColor: ColorObject = basicColors()[0]

    fun colorPosition(colorObject: ColorObject): Int {
        for (i in basicColors().indices) {
            if (colorObject == basicColors()[i])
                return i
        }
        return 0
    }

    fun basicColors(): List<ColorObject> {
        return listOf(
            ColorObject(R.string.color_gray, "808080", whiteHex),
            ColorObject(R.string.color_black, blackHex, whiteHex),
            ColorObject(R.string.color_red, "FF0000", whiteHex),
            ColorObject(R.string.color_green, "008000", whiteHex),
            ColorObject(R.string.color_yellow, "FFFF00", blackHex),
            ColorObject(R.string.color_blue, "0000FF", whiteHex),
        )
    }
}