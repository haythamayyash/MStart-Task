package com.haythamayyash.mstarttask.employee.db

import androidx.room.Dao
import androidx.room.Query
import com.haythamayyash.mstarttask.common.model.Department

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM Department WHERE ID = :id ")
    suspend fun getDepartment(id: Long): Department


}