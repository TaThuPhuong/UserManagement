package com.ttp.usermanagement.ui.register


import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.view.BaseActivity
import com.ttp.usermanagement.common.custom.view.DialogConfirm
import com.ttp.usermanagement.common.custom.view.SpinnerCustom
import com.ttp.usermanagement.common.custom.view.showAutoDismissDialog
import com.ttp.usermanagement.common.exstension.gone
import com.ttp.usermanagement.common.exstension.setErrorIfInvalid
import com.ttp.usermanagement.common.exstension.setErrorIfLengthExceeded
import com.ttp.usermanagement.common.exstension.showSnackBar
import com.ttp.usermanagement.common.exstension.visible
import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.databinding.ActivityRegisterBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.register.viewModel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(ActivityRegisterBinding::inflate),
    View.OnClickListener {

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var edUserId: EditText
    private lateinit var edPass: EditText
    private lateinit var edFamilyName: EditText
    private lateinit var edFirstName: EditText
    private lateinit var edAge: EditText
    private lateinit var cbAdmin: CheckBox
    private lateinit var spnGender: Spinner
    private lateinit var spnRole: Spinner

    private lateinit var userId: String
    private lateinit var pass: String
    private lateinit var familyName: String
    private lateinit var firstName: String
    private var age: Int? = null
    private var isAdmin = 0
    private var selectedRoleId = -1
    private var selectedGenderId = -1

    override fun setupData() {
        initView()
        initData()
    }

    override fun setupObserver() {
        viewModel.roleState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val roles = resource.result
                    setupRoleSpinner(roles)
                }

                else -> {}
            }
        }

        viewModel.genderState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    val genders = resource.result
                    setupGenderSpinner(genders)
                }

                else -> {}
            }
        }

        lifecycleScope.launch {
            viewModel.registerState.collectLatest { state ->
                if (state != null) {
                    when (state) {
                        is Resource.Error -> {
                            binding.progressBar.gone()
                            binding.root.showSnackBar(state.message)
                        }

                        is Resource.Loading -> {
                            binding.progressBar.visible()
                        }

                        is Resource.Success -> {
                            binding.progressBar.gone()
                            binding.root.showSnackBar(getString(R.string.success))
//                            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                        }

                        else -> {
                            binding.progressBar.visible()
                            showAutoDismissDialog(
                                "Đã xảy ra lỗi trong quá trình đăng kí. \n Vui lòng thử lại sau",
                                3000
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.btnRegister -> {
                if (validateRegister()) {
                    DialogConfirm(this, getString(R.string.register_confirm)) { result ->
                        when (result) {
                            1 -> {
                                viewModel.registerUser(
                                    userId,
                                    pass,
                                    familyName,
                                    firstName,
                                    selectedGenderId,
                                    age,
                                    selectedRoleId,
                                    isAdmin
                                )
                            }
                        }
                    }.show()
                }
            }
        }
    }

    private fun initView() {
        edUserId = binding.edUserId.editText!!
        edPass = binding.edPass.editText!!
        edFamilyName = binding.edFamilyName.editText!!
        edFirstName = binding.edFirstName.editText!!
        edAge = binding.edAge.editText!!

        cbAdmin = binding.cbIsAdmin
        spnGender = binding.spnGender
        spnRole = binding.spnRole
    }

    private fun initData() {
        viewModel.fetchRolesAndGenders()
        binding.btnRegister.setOnClickListener(this)
        validateRegister()
    }

    private fun setupRoleSpinner(roles: List<Role>) {
        val placeholder = getString(R.string.select_role)
        val roleNames = listOf(placeholder) + roles.map { it.authorityName }
        val roleAdapter = SpinnerCustom(this, roleNames)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnRole.adapter = roleAdapter

        spnRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedRoleId = if (position == 0) {
                    -1
                } else {
                    roles[position - 1].authorityId
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupGenderSpinner(genders: List<Gender>) {
        val placeholder = getString(R.string.select_gender)
        val genderNames = listOf(placeholder) + genders.map { it.genderName }
        val genderAdapter = SpinnerCustom(this, genderNames)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnGender.adapter = genderAdapter

        spnGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position == 0) {
                    selectedGenderId = -1
                    binding.tvErrGender.visible()
                } else {
                    selectedGenderId = genders[position - 1].genderId
                    binding.tvErrGender.gone()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun validateRegister(): Boolean {
        userId = edUserId.text.toString()
        pass = edPass.text.toString()
        familyName = edFamilyName.text.toString()
        firstName = edFirstName.text.toString()
        age = edAge.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
        isAdmin = if (cbAdmin.isChecked) 1 else 0

        val userIdError = "${getString(R.string.user_id)} ${getString(R.string.emty_err)}"
        val passError = "${getString(R.string.pass)} ${getString(R.string.emty_err)}"
        val familyNameError = "${getString(R.string.family_name)} ${getString(R.string.emty_err)}"
        val firstNameError = "${getString(R.string.name)} ${getString(R.string.emty_err)}"
        val maxLengthUserId = 8
        val maxLengthPass = 8
        val maxLengthFamilyName = 10
        val maxLengthFirstName = 10

        var isValid = true

        isValid = setErrorIfInvalid(edUserId, userIdError) && isValid
        isValid = setErrorIfInvalid(edPass, passError) && isValid
        isValid = setErrorIfInvalid(edFamilyName, familyNameError) && isValid
        isValid = setErrorIfInvalid(edFirstName, firstNameError) && isValid

        isValid = setErrorIfLengthExceeded(edUserId, maxLengthUserId, userIdError, this) && isValid
        isValid = setErrorIfLengthExceeded(edPass, maxLengthPass, passError, this) && isValid
        isValid = setErrorIfLengthExceeded(
            edFamilyName, maxLengthFamilyName, familyNameError, this
        ) && isValid
        isValid = setErrorIfLengthExceeded(
            edFirstName, maxLengthFirstName, firstNameError, this
        ) && isValid

        if (selectedGenderId == -1) {
            binding.tvErrGender.visible()
            isValid = false
        } else {
            binding.tvErrGender.gone()
        }

        return isValid
    }

}