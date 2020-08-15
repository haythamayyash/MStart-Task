package com.haythamayyash.mstarttask.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Department {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Long = 0

    @ColumnInfo(name = "Name")
    var name: String = ""

    @ColumnInfo(name = "Server_Date_Time")
    var serverDateTime: Long = 0

    @ColumnInfo(name = "DateTime_UTC")
    var dateTimeUTC: Long = 0

    @ColumnInfo(name = "Update_DateTime_UTC")
    var updateDateTimeUTC: Long = 0
}