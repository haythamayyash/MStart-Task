package com.haythamayyash.mstarttask.employee.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.TimeManager
import com.haythamayyash.mstarttask.databinding.EmployeeRowItemBinding
import com.haythamayyash.mstarttask.databinding.ProgressRowItemBinding
import java.io.File

class EmployeeAdapter(val timeManager: TimeManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_ITEM: Int = 1
        const val TYPE_PROGRESS: Int = 0
    }

    private val employeeList: MutableList<Employee> by lazy { mutableListOf<Employee>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var binding: ViewDataBinding

        if (viewType == TYPE_ITEM) {
            binding = DataBindingUtil.inflate<EmployeeRowItemBinding>(
                layoutInflater,
                R.layout.employee_row_item,
                parent,
                false
            )
            return EmployeeViewHolder(binding)

        } else {
            binding = DataBindingUtil.inflate<ProgressRowItemBinding>(
                layoutInflater, R.layout.progress_row_item, parent, false
            )
            return ProgressViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            holder as EmployeeViewHolder
            holder.bind(employeeList[position])
        } else {
            holder as ProgressViewHolder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position == itemCount - 1)) TYPE_PROGRESS else TYPE_ITEM
    }

    fun refresh(employeeList: List<Employee>) {
        this.employeeList.addAll(employeeList)
        notifyDataSetChanged()
    }

    inner class EmployeeViewHolder(val itemBinding: EmployeeRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(employee: Employee?) {
            itemBinding.employee = employee
            itemBinding.textViewCreatedOnValue.text = timeManager.formatDate(
                timeManager.getLocalTime(employee?.serverDateTime ?: 0),
                "yyyy/MM/dd"
            )
            val file = File(employee?.photo ?: "")
            itemBinding.imageViewPersonalImage.setImageURI(Uri.fromFile(file))
        }

    }

    inner class ProgressViewHolder(val itemBinding: ProgressRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
}