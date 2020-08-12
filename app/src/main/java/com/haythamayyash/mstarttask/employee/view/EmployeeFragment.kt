package com.haythamayyash.mstarttask.employee.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.db.DataBaseManager
import com.haythamayyash.mstarttask.databinding.FragmentEmployeeBinding
import com.haythamayyash.mstarttask.employee.adapter.EmployeeAdapter
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import com.haythamayyash.mstarttask.employee.viewmodel.EmployeeViewModel

class EmployeeFragment : Fragment() {
    var viewModel: EmployeeViewModel? = null
    var employeeAdapter: EmployeeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentEmployeeBinding>(
            inflater,
            R.layout.fragment_employee,
            container,
            false
        )
        //TODO  add this to module when add dagger
        val factory = EmployeeViewModel.Factory(
            EmployeeRepository(
                DataBaseManager.getInstance(requireContext()).employeeDao(),
                DataBaseManager.getInstance(requireContext()).departmentDao()
            )
        )
        viewModel =
            ViewModelProvider(this, factory).get(EmployeeViewModel::class.java)
        employeeAdapter = EmployeeAdapter()
        binding.recyclerViewEmployee.adapter = employeeAdapter
        observeGetEmployee()
        viewModel?.getEmployee()
        return binding.root
    }

    private fun observeGetEmployee() {
        viewModel?.employeeLiveData?.observe(viewLifecycleOwner, Observer {
            employeeAdapter?.refresh(it)
        })
    }
}