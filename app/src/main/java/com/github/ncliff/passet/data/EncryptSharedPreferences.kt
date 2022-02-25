package com.github.ncliff.passet.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKey.KeyScheme

class EncryptSharedPreferences constructor(context: Context) {
    var sharedPreferences: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(KeyScheme.AES256_GCM)
            .build()

        sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data",
            masterKey,
            AES256_SIV,
            AES256_GCM
        )
    }

    companion object :
        SingletonHolder<EncryptSharedPreferences, Context>(::EncryptSharedPreferences)
}