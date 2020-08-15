package com.haythamayyash.mstarttask.employee.db

import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.TimeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class EmployeeRepository(
    private val employeeDao: EmployeeDao,
    private val timeManager: TimeManager
) {
    companion object {
        private const val NO_MORE_ITEMS: Long = -1
    }

    private var lastId: Long = 0
    suspend fun insertEmployee(employee: Employee, department: Department) =
        withContext(Dispatchers.IO) {
            employee.serverDateTime = Calendar.getInstance().timeInMillis
            employee.dateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            department.serverDateTime = Calendar.getInstance().timeInMillis
            department.dateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            employeeDao.insertEmployee(employee, department)
        }

    suspend fun updateEmployee(employee: Employee, department: Department) =
        withContext(Dispatchers.IO) {
            employee.updateDateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            department.serverDateTime = Calendar.getInstance().timeInMillis
            department.dateTimeUTC = timeManager.getUtcTime(Calendar.getInstance().timeInMillis)
            employeeDao.updateEmployee(employee, department)
        }
    suspend fun deleteEmployees(idList: List<Long>) = withContext(Dispatchers.IO) {
        employeeDao.deleteEmployees(idList)
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

    suspend fun getDepartment(id: Long): Department {
        return employeeDao.getDepartment(id)
    }

    suspend fun getEmployeeCount(): Int? {
        return employeeDao.getEmployeeCount()
    }

    fun reset() {
        lastId = 0
    }
}