package com.haythamayyash.mstarttask.employee.db

import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.TimeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class EmployeeRepository(
    private val employeeDao: EmployeeDao,
    private val departmentDao: DepartmentDao,
    private val timeManager: TimeManager
) {
    companion object {
        private const val NO_MORE_ITEMS: Long = -1
    }

    var lastId: Long = 0
    suspend fun insertEmployee(employee: Employee, department: Department) =
        withContext(Dispatchers.IO) {
            employee.serverDateTime = Calendar.getInstance().timeInMillis
            employee.dateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            department.serverDateTime = Calendar.getInstance().timeInMillis
            department.dateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            employeeDao.insertEmployee(employee, department)
        }

    suspend fun deleteEmployee(id: Int) = withContext(Dispatchers.IO) {
        employeeDao.deleteEmployee(id)
    }


    suspend fun getEmployeeList(pageSize: Int): List<Employee> {
        val employeeList = if (lastId != NO_MORE_ITEMS) {
            employeeDao.getEmployeeList(lastId, pageSize)
        } else {
            listOf()
        }
        lastId = try {
            employeeList[employeeList.size - 1].id
        } catch (e: Exception) {
            NO_MORE_ITEMS
        }
        return employeeList
    }

    suspend fun insertDepartment(department: Department) = withContext(Dispatchers.IO) {
        departmentDao.insertDepartment(department)
    }

    suspend fun deleteDepartment(id: Int) = withContext(Dispatchers.IO) {
        departmentDao.deleteDepartment(id)
    }

    suspend fun getDepartment(id: Long) {
        departmentDao.getDepartment(id)
    }

    fun reset() {
        lastId = 0
    }
}