package com.github.ncliff.passet.data

import java.security.SecureRandom

class PasswordManager {
    companion object {
        fun generatePassword(
            len: Int,
            withNumbers: Boolean,
            withUpperCase: Boolean,
            withLowerCase: Boolean,
            withSpecialSymbols: Boolean,
            withAdditionalSpecialSymbols: Boolean
        ): String {
            var symbols = ""

            if (withNumbers) symbols += numberChars
            if (withUpperCase) symbols += upperCaseChars
            if (withLowerCase) symbols += lowerCaseChars
            if (withSpecialSymbols){
                symbols += specialSymbolsChars
                if (withAdditionalSpecialSymbols) symbols += additionalSpecialSymbolsChars
            }

            val random = SecureRandom.getInstance("SHA1PRNG")

            val res = StringBuilder(len)

            (0 until len).forEach { _ ->
                val randomInt: Int = random.nextInt(symbols.length)
                res.append(symbols[randomInt])
            }

            return res.toString()
        }

        private const val numberChars = "0123456789"
        private const val upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val lowerCaseChars = "abcdefghijklmnopqrstuvwxyz"
        private const val additionalSpecialSymbolsChars = "!\"&'+,-./:;<=>[\\]^_`{|}"
        private const val specialSymbolsChars = "%*()?@#\$~"
    }
}