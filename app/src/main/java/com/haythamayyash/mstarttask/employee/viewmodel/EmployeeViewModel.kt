package com.haythamayyash.mstarttask.employee.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.haythamayyash.mstarttask.common.Constants
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.SingleLiveEvent
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class EmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    val employeeLiveData: MutableLiveData<List<Employee>> by lazy { MutableLiveData<List<Employee>>() }
    val employeeCountLiveData: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    val navigateToInsertEmployee: SingleLiveEvent<String> by lazy { SingleLiveEvent<String>() }
    val navigateToUpdateEmployee: SingleLiveEvent<Employee> by lazy { SingleLiveEvent<Employee>() }

    fun deleteEmployees(idList: List<Long>) = viewModelScope.launch {
        employeeRepository.deleteEmployees(idList)
    }


    fun getEmployee(pageSize: Int = 10) = viewModelScope.launch {
        employeeLiveData.value = employeeRepository.getEmployeeList(pageSize)
        employeeCountLiveData.value = employeeRepository.getEmployeeCount()
    }

    fun onAddEmployeeClick() {
        navigateToInsertEmployee.postValue(Constants.INSERT)
    }

    fun onItemListClick(employee: Employee) {
        navigateToUpdateEmployee.postValue(employee)
    }


    fun reset() {
        employeeRepository.reset()
    }

    class Factory(
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepository) as T
        }
    }
}