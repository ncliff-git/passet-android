package com.github.ncliff.passet.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CRATE TABLE $TABLE_NAME ($COLUMN_ID)"
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        const val DATABASE_NAME = "Passet.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "myPass"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "pass_name"
        const val COLUMN_LOGIN = "pass_login"
        const val COLUMN_PASSWORD = "pass_password"
        const val COLUMN_MFG = "pass_mfg"
        const val COLUMN_EXP = "pass_exp"
        const val COLUMN_LOCATION = "pass_location"
    }
}