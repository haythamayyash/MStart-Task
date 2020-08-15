package com.haythamayyash.mstarttask.employee.view

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.haythamayyash.mstarttask.R
import com.haythamayyash.mstarttask.common.Constants
import com.haythamayyash.mstarttask.common.db.DataBaseManager
import com.haythamayyash.mstarttask.common.model.Employee
import com.haythamayyash.mstarttask.common.util.TimeManager
import com.haythamayyash.mstarttask.databinding.FragmentAddEmployeeBinding
import com.haythamayyash.mstarttask.employee.db.EmployeeRepository
import com.haythamayyash.mstarttask.employee.viewmodel.AddEmployeeViewModel
import java.io.FileNotFoundException


class AddEmployeeFragment : Fragment() {
    companion object {
        private const val RESULT_LOAD_IMAGE: Int = 0
        private const val TAG: String = "AddEmployeeFragment"
    }


    private var addEmployeeAction: String? = null
    private var employee: Employee? = null

    private lateinit var binding: FragmentAddEmployeeBinding
    private var viewModel: AddEmployeeViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEmployeeAction = arguments?.get(Constants.ADD_EMPLOYEE_ACTION) as? String?
        employee = arguments?.get(Constants.INTENT_EMPLOYEE) as? Employee?

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_employee,
            container,
            false
        )
        val factory = AddEmployeeViewModel.Factory(
            EmployeeRepository(
                DataBaseManager.getInstance(requireContext()).employeeDao(),
                TimeManager()
            )
        )
        viewModel = ViewModelProvider(this, factory).get(AddEmployeeViewModel::class.java)
        viewModel?.addEmployeeAction = addEmployeeAction
        viewModel?.employee = employee
        binding.viewModel = viewModel
        binding.invalidateAll()
        observeFirstNameError()
        observeLastNameError()
        observeEmailError()
        observeMobileError()
        observePasswordError()
        observeReEnterPasswordError()
        observeAddressError()
        observeDepartmentError()
        observeNavigateBack()
        observeOpenGallery()
        return binding.root
    }


    private fun observeFirstNameError() {
        viewModel?.firstNameError?.observe(viewLifecycleOwner, Observer {
            binding.editTextFirstName.error = it
        })
    }

    private fun observeLastNameError() {
        viewModel?.lastNameError?.observe(viewLifecycleOwner, Observer {
            binding.editTextLastName.error = it
        })
    }

    private fun observeEmailError() {
        viewModel?.emailError?.observe(viewLifecycleOwner, Observer {
            binding.editTextEmail.error = it
        })
    }

    private fun observeMobileError() {
        viewModel?.mobileNumberError?.observe(viewLifecycleOwner, Observer {
            binding.editTextMobileNumber.error = it
        })
    }

    private fun observePasswordError() {
        viewModel?.passwordError?.observe(viewLifecycleOwner, Observer {
            binding.editTextPassword.error = it
        })
    }

    private fun observeReEnterPasswordError() {
        viewModel?.reEnterPasswordError?.observe(viewLifecycleOwner, Observer {
            binding.editTextReEnterPassword.error = it
        })
    }

    private fun observeAddressError() {
        viewModel?.addressError?.observe(viewLifecycleOwner, Observer {
            binding.editTextAddress.error = it
        })
    }

    private fun observeDepartmentError() {
        viewModel?.departmentError?.observe(viewLifecycleOwner, Observer {
            binding.editTextDepartmentName.error = it
        })
    }

    private fun observeNavigateBack() {
        viewModel?.navigateBack?.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, R.string.added_successfully, Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        })
    }

    private fun observeOpenGallery() {
        viewModel?.openGallery?.observe(viewLifecycleOwner, Observer {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            try {
                val imageUri: Uri? = data?.data
                val path = imageUri?.path ?: ""
                binding.textViewAttacheImage.text = path
                Log.d(TAG, imageUri.toString())
//                val imageStream: InputStream? =
//                    imageUri?.let { context?.contentResolver?.openInputStream(it) }
//                val selectedImage = BitmapFactory.decodeStream(imageStream)
                viewModel?.personalImage = path
//                image_view.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }
}