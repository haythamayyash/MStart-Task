package com.haythamayyash.mstarttask.employee.db

import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeRepository(
    private val employeeDao: EmployeeDao,
    private val departmentDao: DepartmentDao
) {
    var lastId: Long = 0
    suspend fun insertEmployee(employee: Employee) = withContext(Dispatchers.IO) {
        employeeDao.insertEmployee(employee)
    }

    suspend fun deleteEmployee(id: Int) = withContext(Dispatchers.IO) {
        employeeDao.deleteEmployee(id)
    }

    suspend fun getEmployeeList(pageSize: Int): List<Employee> {
        val employeeList = employeeDao.getEmployeeList(lastId, pageSize)
        lastId = employeeList.last().id
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
}