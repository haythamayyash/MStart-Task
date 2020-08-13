package com.haythamayyash.mstarttask.employee.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.db.DataBaseManager
import com.haythamayyash.mstarttask.databinding.FragmentAddEmployeeBinding
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import com.haythamayyash.mstarttask.employee.viewmodel.AddEmployeeViewModel

class AddEmployeeFragment : Fragment() {
    var viewModel: AddEmployeeViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddEmployeeBinding>(
            inflater,
            R.layout.fragment_add_employee,
            container,
            false
        )
        val factory = AddEmployeeViewModel.Factory(
            EmployeeRepository(
                DataBaseManager.getInstance(requireContext()).employeeDao(),
                DataBaseManager.getInstance(requireContext()).departmentDao()
            )
        )
        viewModel = ViewModelProvider(this, factory).get(AddEmployeeViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }
}