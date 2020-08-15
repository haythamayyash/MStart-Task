package com.haythamayyash.mstarttask.common.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
class Employee() : Parcelable {
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
    var departmentId: Long = 0

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

    /**
     * Since Blob is not recommended and cause problems and crashes when the image is big ,
     * i use the uri image instead
     */
    @ColumnInfo(name = "Photo")
    var photo: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        departmentId = parcel.readLong()
        serverDateTime = parcel.readLong()
        dateTimeUTC = parcel.readLong()
        updateDateTimeUTC = parcel.readLong()
        firstName = parcel.readString().toString()
        lastName = parcel.readString().toString()
        email = parcel.readString().toString()
        mobileNumber = parcel.readString().toString()
        password = parcel.readString().toString()
        gender = parcel.readString().toString()
        address = parcel.readString().toString()
        photo = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(departmentId)
        parcel.writeLong(serverDateTime)
        parcel.writeLong(dateTimeUTC)
        parcel.writeLong(updateDateTimeUTC)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(email)
        parcel.writeString(mobileNumber)
        parcel.writeString(password)
        parcel.writeString(gender)
        parcel.writeString(address)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Employee> {
        override fun createFromParcel(parcel: Parcel): Employee {
            return Employee(parcel)
        }

        override fun newArray(size: Int): Array<Employee?> {
            return arrayOfNulls(size)
        }
    }
}