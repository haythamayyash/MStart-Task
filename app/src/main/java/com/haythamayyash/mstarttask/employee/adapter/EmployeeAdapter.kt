package com.haythamayyash.mstarttask.employee.adapter

import android.content.Context
import android.net.Uri
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.haythamayyash.mstarttask.MainActivity
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.TimeManager
import com.haythamayyash.mstarttask.databinding.EmployeeRowItemBinding
import com.haythamayyash.mstarttask.databinding.ProgressRowItemBinding
import com.haythamayyash.mstarttask.employee.viewmodel.EmployeeViewModel
import java.io.File

class EmployeeAdapter(val viewModel: EmployeeViewModel?, val timeManager: TimeManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ActionMode.Callback {
    companion object {
        const val TYPE_ITEM: Int = 1
        const val TYPE_PROGRESS: Int = 0
    }

    private var context: Context? = null
    var total: Int = 0
    private val employeeList: MutableList<Employee> by lazy { mutableListOf<Employee>() }
    var multiSelect = false
    private val selectedItems = mutableListOf<Long>()
    val selectedItemsPosition = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        lateinit var binding: ViewDataBinding

        return if (viewType == TYPE_ITEM) {
            binding = DataBindingUtil.inflate<EmployeeRowItemBinding>(
                layoutInflater,
                R.layout.employee_row_item,
                parent,
                false
            )
            EmployeeViewHolder(binding)

        } else {
            binding = DataBindingUtil.inflate<ProgressRowItemBinding>(
                layoutInflater, R.layout.progress_row_item, parent, false
            )
            ProgressViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {
            holder as EmployeeViewHolder
            holder.bind(employeeList[position], position)
        } else {
            holder as ProgressViewHolder
            when {
                employeeList.isEmpty() -> {
                    holder.itemBinding.progressbar.visibility = View.GONE
                }
                itemCount - 1 == total -> {
                    holder.itemBinding.progressbar.visibility = View.GONE
                }
                else -> {
                    holder.itemBinding.progressbar.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position == itemCount - 1)) TYPE_PROGRESS else TYPE_ITEM
    }

    override fun getItemId(position: Int): Long {
        return try {
            employeeList[position].id
        } catch (e: Exception) {
            -1
        }
    }

    fun refresh(employeeList: List<Employee>) {
        this.employeeList.addAll(employeeList)
        notifyDataSetChanged()
    }

    fun reset() {
        this.employeeList.clear()
    }

    fun clearSelectedItems() {
        selectedItems.clear()
        selectedItemsPosition.clear()
        notifyDataSetChanged()
    }

    private fun selectItem(
        holder: EmployeeViewHolder,
        employee: Employee?,
        mPosition: Int
    ) {
        if (selectedItems.contains(employee?.id)) {
            selectedItems.remove(employee?.id)
            selectedItemsPosition.remove(mPosition)
            holder.itemView.alpha = 1.0f
        } else {
            employee?.let { selectedItems.add(it.id) }
            selectedItemsPosition.add(mPosition)
            holder.itemView.alpha = 0.3f
        }
    }


    inner class EmployeeViewHolder(private val itemBinding: EmployeeRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private var mPosition: Int = 0

        init {
            itemBinding.root.setOnLongClickListener {
                if (!multiSelect) {
                    multiSelect = true
                    (context as MainActivity).startSupportActionMode(this@EmployeeAdapter)
                    selectItem(this, itemBinding.employee, mPosition)
                }
                true
            }

            itemBinding.root.setOnClickListener {
                if (multiSelect) {
                    selectItem(this, itemBinding.employee, mPosition)
                } else {
                    viewModel?.onItemListClick(itemBinding.employee ?: Employee())
                }
            }
        }

        fun bind(employee: Employee?, position: Int) {
            this.mPosition = position
            itemBinding.employee = employee
            itemBinding.viewModel = viewModel
            itemBinding.textViewCreatedOnValue.text = timeManager.formatDate(
                employee?.serverDateTime ?: 0,
                "yyyy/MM/dd"
            )
            val file = File(employee?.photo ?: "")
            itemBinding.imageViewPersonalImage.setImageURI(Uri.fromFile(file))
        }


    }

    inner class ProgressViewHolder(val itemBinding: ProgressRowItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)


    override fun onActionItemClicked(
        mode: ActionMode?,
        item: MenuItem?
    ): Boolean {
        if (item?.itemId == R.id.delete) {
            viewModel?.deleteEmployees(selectedItemsPosition.map {
                employeeList.removeAt(it)
                employeeList[it].id
            })
            notifyDataSetChanged()
            Toast.makeText(
                context,
                context?.getString(R.string.items_deleted_message),
                Toast.LENGTH_SHORT
            ).show()
            mode?.finish()
        }
        return true
    }

    override fun onCreateActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        val inflater: MenuInflater? = mode?.menuInflater
        inflater?.inflate(R.menu.selection_menu, menu)
        return true
    }

    override fun onPrepareActionMode(
        mode: ActionMode?,
        menu: Menu?
    ): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        multiSelect = false
        selectedItems.clear()
        selectedItemsPosition.clear()
        notifyDataSetChanged()
    }
}