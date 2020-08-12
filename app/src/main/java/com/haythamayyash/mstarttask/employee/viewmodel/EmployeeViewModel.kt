package com.haythamayyash.mstarttask.employee.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    val employeeLiveData: MutableLiveData<List<Employee>> by lazy { MutableLiveData<List<Employee>>() }

    fun insertEmployee(employee: Employee) = viewModelScope.launch {
        employeeRepository.insertEmployee(employee)
    }

    fun deleteEmployee(id: Int) = viewModelScope.launch {
        employeeRepository.deleteEmployee(id)
    }

    fun getEmployee(pageSize: Int = 10) = viewModelScope.launch {
        employeeLiveData.value = employeeRepository.getEmployeeList(pageSize)
    }

    fun insertDepartment(department: Department) = viewModelScope.launch {
        employeeRepository.insertDepartment(department)
    }

    fun deleteDepartment(id: Int) = viewModelScope.launch {
        employeeRepository.deleteDepartment(id)
    }

    fun getDepartmentList(id: Long) = viewModelScope.launch {
        employeeRepository.getDepartment(id)
    }

    class Factory(
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepository) as T
        }
    }
}