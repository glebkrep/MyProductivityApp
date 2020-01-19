package com.example.myproductivityapp.Repository

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tomato_table")
data class Tomato(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @ColumnInfo(name = "is_current")
    var isCurrent: Boolean = true,
    @ColumnInfo(name = "length")
    val length: Long? = null,
    @ColumnInfo(name = "start_time")
    val startTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time")
    var endTime: Long? = null,
    @ColumnInfo(name = "type")
    val type: Int
):Parcelable