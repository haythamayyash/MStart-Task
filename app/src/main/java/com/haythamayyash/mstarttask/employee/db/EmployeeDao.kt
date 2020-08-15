package com.haythamayyash.mstarttask.employee.db

import androidx.room.*
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee


@Dao
abstract class EmployeeDao {
    @Query("SELECT * FROM Employee WHERE ID > :lastId LIMIT :pageSize")
    abstract suspend fun getEmployeeList(lastId: Long, pageSize: Int): List<Employee>

    @Query("SELECT * FROM Department WHERE ID = :id ")
    abstract suspend fun getDepartment(id: Long): Department

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: Employee, department: Department) {
        val departmentId = insertDepartment(department)
        employee.departmentId = departmentId
        insertEmployee(employee)
    }

    @Update
    suspend fun updateEmployee(employee: Employee, department: Department) {
        insertDepartment(department)
        employee.departmentId = department.id
        update(employee)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEmployee(employee: Employee): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertDepartment(department: Department): Long


    @Update
    abstract suspend fun update(employee: Employee)

    @Query("delete from Employee where ID in (:idList)")
    abstract suspend fun deleteEmployees(idList: List<Long>)

    @Query("DELETE FROM Employee WHERE ID = :id ")
    abstract suspend fun deleteEmployee(id: Int)

    @Query("SELECT COUNT(ID) FROM Employee")
    abstract suspend fun getEmployeeCount(): Int?


}