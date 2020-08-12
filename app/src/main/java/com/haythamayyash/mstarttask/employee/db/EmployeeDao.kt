package com.haythamayyash.mstarttask.employee.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM Employee WHERE ID > :lastId LIMIT :pageSize")
    suspend fun getEmployeeList(lastId: Long, pageSize: Int): List<Employee>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee)

    @Query("DELETE FROM Employee WHERE ID = :id ")
    suspend fun deleteEmployee(id: Int)


}