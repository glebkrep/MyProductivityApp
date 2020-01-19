package com.example.myproductivityapp.Repository

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "type_table")
data class Type(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @ColumnInfo(name = "daily_time")
    var desiredDailyTime:Long,
    @ColumnInfo(name = "name")
    var name:String
):Parcelable