package com.haythamayyash.mstarttask.employee.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.Constants
import com.haythamayyash.mstarttask.common.MyApplication
import com.haythamayyash.mstarttask.common.model.Department
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.SingleLiveEvent
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class AddEmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {
    var addEmployeeAction: String? = Constants.INSERT
    var employee: Employee? = null
        set(value) {
            field = value
            if (employee?.firstName?.isNotEmpty() == true) {
                firstName = employee?.firstName ?: ""
            }
            if (employee?.lastName?.isNotEmpty() == true) {
                lastName = employee?.lastName ?: ""
            }
            if (employee?.email?.isNotEmpty() == true) {
                email = employee?.email ?: ""
            }
            if (employee?.mobileNumber?.isNotEmpty() == true) {
                mobileNumber = employee?.mobileNumber ?: ""
            }
            if (employee?.password?.isNotEmpty() == true) {
                password = ""
            }
            if (employee?.address?.isNotEmpty() == true) {
                address = employee?.address ?: ""
            }
            viewModelScope.launch {
                val department: Department? =
                    employeeRepository.getDepartment(employee?.departmentId ?: 0)
                departmentName = department?.name ?: ""
            }
        }
    var personalImage: String? = null
    var firstName: String = employee?.firstName ?: ""
    var lastName: String = ""
    var email: String = ""
    var mobileNumber: String = ""
    var password: String = ""
    var reEnterPassword: String = ""
    var address: String = ""
    var departmentName: String = ""
    val firstNameError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val lastNameError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val emailError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val mobileNumberError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val passwordError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val reEnterPasswordError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val addressError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val departmentError: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    val navigateBack: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }
    val openGallery: SingleLiveEvent<Boolean> by lazy { SingleLiveEvent<Boolean>() }

    fun onSaveClick() = viewModelScope.launch {
        if (validateFields()) {
            val department = Department()
            department.name = departmentName
            employee = Employee()
            employee?.firstName = firstName
            employee?.lastName = lastName
            employee?.email = email
            employee?.mobileNumber = mobileNumber
            employee?.password = password
            employee?.address = address
            employee?.photo = personalImage?.let { personalImage }
            if (employee != null) {
                if (addEmployeeAction == Constants.INSERT) {
                    employeeRepository.insertEmployee(employee!!, department)
                } else {
                    employeeRepository.updateEmployee(employee!!, department)
                }
            }
            navigateBack.postValue(true)
        }
    }

    fun onAttachImageClick() {
        openGallery.postValue(true)
    }

    private fun validateFields(): Boolean {
        var isValid = true
        val errorMessage = MyApplication.getContext()?.getString(R.string.required_field)
        if (firstName.isEmpty()) {
            firstNameError.postValue(errorMessage)
            isValid = false
        }
        if (lastName.isEmpty()) {
            lastNameError.postValue(errorMessage)
            isValid = false
        }
        if (email.isEmpty()) {
            emailError.postValue(errorMessage)
            isValid = false
        }
        if (mobileNumber.isEmpty()) {
            mobileNumberError.postValue(errorMessage)
            isValid = false
        }
        if (password.isEmpty()) {
            passwordError.postValue(errorMessage)
            isValid = false
        }
        if (reEnterPassword.isEmpty()) {
            reEnterPasswordError.postValue(errorMessage)
            isValid = false
        }
        if (address.isEmpty()) {
            addressError.postValue(errorMessage)
            isValid = false
        }
        if (departmentName.isEmpty()) {
            departmentError.postValue(errorMessage)
            isValid = false
        }

        if (password != reEnterPassword) {
            reEnterPasswordError.postValue(
                MyApplication.getContext()?.getString(R.string.passwords_not_match)
            )
            isValid = false
        }

        return isValid
    }


    class Factory(
        private val employeeRepository: EmployeeRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return AddEmployeeViewModel(employeeRepository) as T
        }
    }
}