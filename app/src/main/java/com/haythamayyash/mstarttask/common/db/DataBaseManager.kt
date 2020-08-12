package com.haythamayyash.mstarttask.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.employee.db.DepartmentDao
import com.haythamayyash.mstarttask.employee.db.EmployeeDao

@Database(entities = [Employee::class, Department::class], version = 1)
abstract class DataBaseManager : RoomDatabase() {
    companion object {
        private var instance: DataBaseManager? = null

        @Synchronized
        fun getInstance(context: Context): DataBaseManager {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseManager::class.java, "employee.db"
                ).build()
            }
            return instance as DataBaseManager
        }
    }

    abstract fun employeeDao(): EmployeeDao
    abstract fun departmentDao(): DepartmentDao


}