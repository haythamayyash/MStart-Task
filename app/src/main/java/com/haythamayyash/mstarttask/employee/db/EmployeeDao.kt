package com.haythamayyash.mstarttask.employee.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee

@Dao
abstract class EmployeeDao {
    @Query("SELECT * FROM Employee WHERE ID > :lastId LIMIT :pageSize")
    abstract suspend fun getEmployeeList(lastId: Long, pageSize: Int): List<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee, department: Department) {
        employee.departmentId = department.id
        insert(employee)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(employee: Employee)

    @Query("DELETE FROM Employee WHERE ID = :id ")
    abstract suspend fun deleteEmployee(id: Int)


}