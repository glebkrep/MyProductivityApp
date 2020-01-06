package com.example.myproductivityapp.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type_table")
data class Type(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,
    @ColumnInfo(name = "daily_time")
    var desiredDailyTime:Long,
    @ColumnInfo(name = "name")
    var name:String
)