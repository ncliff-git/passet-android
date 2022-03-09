package com.github.ncliff.passet.data

class ColorObject(var name: Int, var hex: String, var contrastHex: String) {
    val hexHash : String = "#$hex"
    val contrastHexHash : String = "#$contrastHex"
}