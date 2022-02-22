package com.github.ncliff.passet.presentation.models

import com.github.ncliff.passet.R
import kotlin.random.Random

object DataStorage {
    val SHARED_PREFS = "sharedPreferences"
    fun getVersionsList(): List<PersonalData> {
        return listOf(
            PersonalData("Vk", "Privet", "12345678", "11", SOCIAL_ITEM),
            PersonalData("Ok", "Privet", "12345678", "11", DEVICE_ITEM),
            PersonalData("Inst", "Privet", "12345678", "11", WEB_ITEM),
            PersonalData("Telegram", "Privet", "12345678", "11", SOCIAL_ITEM),
            PersonalData("St", "Privet", "12345678", "11", BANK_ITEM),
        )
    }

    const val SOCIAL_ITEM = 1
    const val WEB_ITEM = 2
    const val SHOP_ITEM = 3
    const val DEVICE_ITEM = 4
    const val EMAIL_ITEM = 5
    const val BANK_ITEM = 6

    fun getHeader(type: Int?): Int {
        return when (type) {
            1 -> R.drawable.ic_social_header
            2 -> R.drawable.ic_web_header
            3 -> R.drawable.ic_shop_header
            4 -> R.drawable.ic_device_header
            5 -> R.drawable.ic_email_header
            6 -> R.drawable.ic_bank_header
            else -> 0
        }
    }

    fun getHeaderColor(type: Int): Int {
        return when (type) {
            1 -> R.color.social_color
            2 -> R.color.web_color
            3 -> R.color.shop_color
            4 -> R.color.device_color
            5 -> R.color.email_color
            6 -> R.color.bank_color
            else -> 0
        }
    }
}

object GenPassword {
    private val numChar: List<Char> = ('0'..'9').toList()
    private val lowercaseChar: List<Char> = ('a'..'z').toList()
    private val uppercaseChar: List<Char> = ('A'..'Z').toList()
    private val specialChar: List<Char> = ('!'..'/') + (':'..'@') + ('['..'`') + ('{'..'~')

    fun genPassword(len: Int, nums: Boolean, lowercase: Boolean, uppercase: Boolean, specialSymbol: Boolean) : String {
        var chars = ('0'..'0').toList()

        if (nums) chars += numChar
        if (lowercase) chars += lowercaseChar
        if (uppercase) chars += uppercaseChar
        if (specialSymbol) chars += specialChar

        return (1..len).map{ Random.nextInt(0, chars.size) }.map(chars::get).joinToString("");
    }
}