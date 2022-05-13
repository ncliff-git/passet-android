package com.github.ncliff.passet.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "note_name") var name: String,
    @ColumnInfo(name = "note_login") var login: String,
    @ColumnInfo(name = "note_password") var password: String,
    @ColumnInfo(name = "note_date_start") var dateStart: String,
    @ColumnInfo(name = "note_date_end") var dateEnd: String,
    @ColumnInfo(name = "note_bookworm") var bookworm: Boolean = false,
    @ColumnInfo(name = "note_location_longitude") var locationLongitude: Double?,
    @ColumnInfo(name = "note_location_latitude") var locationLatitude: Double?,
) : Parcelable
