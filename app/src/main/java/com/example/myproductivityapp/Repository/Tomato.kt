package com.example.myproductivityapp.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tomato_table")
data class Tomato(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,
    @ColumnInfo(name = "is_current")
    var isCurrent: Boolean = false,
    @ColumnInfo(name = "length")
    val length: Long? = null,
    @ColumnInfo(name = "start_time")
    val startTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time")
    var endTime: Long? = null,
    @ColumnInfo(name = "type")
    val type: Int
)