package com.haythamayyash.mstarttask.common.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class Employee {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @ColumnInfo(name = "Department_ID")
    @ForeignKey(
        entity = Department::class,
        parentColumns = ["ID"],
        childColumns = ["Department_ID"],
        onDelete = ForeignKey.NO_ACTION
    )
    var departmentId: Int = 0

    @ColumnInfo(name = "Server_Date_Time")
    var serverDateTime: Long = 0

    @ColumnInfo(name = "DateTime_UTC")
    var dateTimeUTC: Long = 0

    @ColumnInfo(name = "Update_DateTime_UTC")
    var updateDateTimeUTC: Long = 0

    @ColumnInfo(name = "First_Name")
    var firstName: String = ""

    @ColumnInfo(name = "Last_Name")
    var lastName: String = ""

    @ColumnInfo(name = "Email")
    var email: String = ""

    @ColumnInfo(name = "Mobile_Number")
    var mobileNumber: String = ""

    @ColumnInfo(name = "Password")
    var password: String = ""

    @ColumnInfo(name = "Gender")
    var gender: String = ""

    @ColumnInfo(name = "Address")
    var address: String = ""

    @ColumnInfo(name = "Photo", typeAffinity = ColumnInfo.BLOB)
    var photo: ByteArray? = null
}