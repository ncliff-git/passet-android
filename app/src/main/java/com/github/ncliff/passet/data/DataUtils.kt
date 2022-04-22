package com.github.ncliff.passet.data

object DataUtils {
    const val APP_PREFERENCES = "APP_PREFERENCES"
    const val PIN_CODE_KEY = "PIN_CODE_KEY"
    const val YANDEX_MAP_API_KEY = "1c58a5c1-2ede-48de-8e28-3e80b92107c9"

    fun testAddPasswordList() = listOf<PasswordNote>(
        PasswordNote("Вконтакте", "89033875255", "1234567", "12.12.2002", "13.03.2020", true),
        PasswordNote("Telegram", "ncliff_dev", "1234567", "12.12.2002", "13.03.2020", true),
        PasswordNote("GitHub", "ncliff-git", "1234567", "12.12.2002", "13.03.2020", true),
        PasswordNote("Google", "ainur.shmln@gmail.com", "1234567", "12.12.2002", "13.03.2020", true),
    )
}