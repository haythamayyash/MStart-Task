package com.haythamayyash.mstarttask.employee.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import kotlinx.coroutines.launch

class AddEmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var mobileNumber: String = ""
    var password: String = ""
    var reEnterPassword: String = ""
    var address: String = ""
    var departmentName: String = ""

    fun onSaveClick() = viewModelScope.launch {
        val department = Department()
        department.name = departmentName
        val employee = Employee()
        employee.firstName = firstName
        employee.lastName = lastName
        employee.email = email
        employee.mobileNumber = mobileNumber
        employee.password = password
        employee.address = address
        employeeRepository.insertEmployee(employee)
    }

    class Factory(
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddEmployeeViewModel(employeeRepository) as T
        }
    }
}