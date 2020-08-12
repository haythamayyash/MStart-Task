package com.haythamayyash.mstarttask.employee.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haythamayyash.mstarttask.common.model.Department

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM Department WHERE ID = :id ")
    suspend fun getDepartment(id: Long): Department

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartment(department: Department)

    @Query("DELETE FROM Department WHERE ID = :id ")
    suspend fun deleteDepartment(id: Int)

}