package com.haythamayyash.mstarttask.employee.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.db.DataBaseManager
import com.haythamayyash.mstarttask.common.util.TimeManager
import com.haythamayyash.mstarttask.databinding.FragmentEmployeeBinding
import com.haythamayyash.mstarttask.employee.adapter.EmployeeAdapter
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import com.haythamayyash.mstarttask.employee.viewmodel.EmployeeViewModel

class EmployeeFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeBinding
    private lateinit var layoutManager: LinearLayoutManager
    var viewModel: EmployeeViewModel? = null
    var employeeAdapter: EmployeeAdapter? = null
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEmployeeBinding>(
            inflater,
            R.layout.fragment_employee,
            container,
            false
        )
        //TODO  add this to module when add dagger
        val factory = EmployeeViewModel.Factory(
            EmployeeRepository(
                DataBaseManager.getInstance(requireContext()).employeeDao(),
                DataBaseManager.getInstance(requireContext()).departmentDao(),
                TimeManager()
            )
        )
        viewModel =
            ViewModelProvider(this, factory).get(EmployeeViewModel::class.java)
        viewModel?.reset()
        employeeAdapter = EmployeeAdapter(TimeManager())
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.viewModel = viewModel
        binding.recyclerViewEmployee.adapter = employeeAdapter
        binding.recyclerViewEmployee.layoutManager = layoutManager
        setupScrolling()
        observeGetEmployee()
        observeNavigateTo()
        viewModel?.getEmployee()
        return binding.root
    }

    private fun setupScrolling() {
        binding.recyclerViewEmployee.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        isLoading = true
//                        currentPage++
                        viewModel?.getEmployee()
                    }
                }
            }
        })
    }

    private fun observeGetEmployee() {
        viewModel?.employeeLiveData?.observe(viewLifecycleOwner, Observer {
            isLoading = false
            employeeAdapter?.refresh(it)
        })
    }

    private fun observeNavigateTo() {
        viewModel?.navigateTo?.observe(viewLifecycleOwner, Observer {
            findNavController().navigate(it)
        })
    }
}