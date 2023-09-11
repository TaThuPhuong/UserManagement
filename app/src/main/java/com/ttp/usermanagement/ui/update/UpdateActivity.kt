package com.ttp.usermanagement.ui.update

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
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
import com.ttp.usermanagement.common.util.Constants
import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.databinding.ActivityUpdateBinding
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.ui.update.viewModel.UpdateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class UpdateActivity : BaseActivity<ActivityUpdateBinding>(ActivityUpdateBinding::inflate),
    View.OnClickListener {

    private val viewModel: UpdateViewModel by viewModels()

    private lateinit var userInfo: UserInfo
    private var genderList: List<Gender> = emptyList()
    private var roleList: List<Role> = emptyList()

    private lateinit var edUserId: EditText
    private lateinit var edPass: EditText
    private lateinit var edFamilyName: EditText
    private lateinit var edFirstName: EditText
    private lateinit var edAge: EditText
    private lateinit var cbAdmin: CheckBox
    private lateinit var spnGender: Spinner
    private lateinit var spnRole: Spinner

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
        initListener()
//        setInitialSpinnerSelections()
    }

    override fun setupObserver() {
        viewModel.roleState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    roleList = resource.result
                    setupRoleSpinner(roleList)
                }

                else -> {}
            }
        }

        viewModel.genderState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    genderList = resource.result
                    setupGenderSpinner(genderList)
                }

                else -> {}
            }
        }

        viewModel.updateState.observe(this) { resource ->
            when (resource) {
                is Resource.Success -> {
                    when (resource.result) {
                        1 -> {
                            Handler(mainLooper).postDelayed({
                                onBackPressed()
                            }, 2000)
                            binding.root.showSnackBar(Constants.SUCCESS)
                            binding.progressBar.gone()
                        }
                    }
                }

                is Resource.Loading -> {
                    binding.progressBar.visible()
                }

                else -> {
                    binding.root.showSnackBar(" $resource")
                    showAutoDismissDialog(
                        "Đã xảy ra lỗi trong quá trình cập nhật. \n Vui lòng thử lại sau",
                        2000
                    )
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.imvBack -> {
                onBackPressed()
            }

            binding.btnUpdate -> {
                if (validateUpdate()) {
                    DialogConfirm(this, getString(R.string.update_confirm)) { result ->
                        when (result) {
                            1 -> {
                                viewModel.updateUser(
                                    userInfo.userId,
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


    private fun initData() {
        viewModel.fetchRolesAndGenders()

        val userInfoJson = intent.getStringExtra("userInfoJson")
        if (userInfoJson != null) {
            userInfo = Json.decodeFromString(UserInfo.serializer(), userInfoJson)
        }
        Log.d("phuongtt", "userInfo: $userInfo")
        binding.user = userInfo
        selectedRoleId = userInfo.authorityId!!
        selectedGenderId = userInfo.genderId
        binding.cbIsAdmin.isChecked = userInfo.admin == 1
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

    private fun initListener() {
        binding.imvBack.setOnClickListener(this)
        binding.btnUpdate.setOnClickListener(this)
    }

    private fun setupRoleSpinner(roles: List<Role>) {
        val placeholder = getString(R.string.select_role)
        val roleNames = listOf(placeholder) + roles.map { it.authorityName }
        val roleAdapter = SpinnerCustom(this, roleNames)
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spnRole.adapter = roleAdapter

        binding.spnRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedRoleId = if (position == 0) {
                    -1
                } else {
                    roles[position - 1].authorityId
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        val rolePosition = roles.indexOfFirst { role -> role.authorityId == selectedRoleId }
        if (rolePosition != -1) {
            binding.spnRole.setSelection(rolePosition + 1)
        }
    }

    private fun setupGenderSpinner(genders: List<Gender>) {
        val genderNames = genders.map { it.genderName }
        val genderAdapter = SpinnerCustom(this, genderNames)
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spnGender.adapter = genderAdapter

        spnGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedGenderId = genders[position].genderId
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        val genderPosition = genders.indexOfFirst { gender -> gender.genderId == selectedGenderId }
        if (genderPosition != -1) {
            binding.spnGender.setSelection(genderPosition)
        }
    }

    private fun validateUpdate(): Boolean {
        pass = edPass.text.toString()
        familyName = edFamilyName.text.toString()
        firstName = edFirstName.text.toString()
        age = edAge.text.toString().takeIf { it.isNotEmpty() }?.toIntOrNull()
        isAdmin = if (cbAdmin.isChecked) 1 else 0
        binding.edPass.isPasswordVisibilityToggleEnabled = true

        val passError = "${getString(R.string.pass)} ${getString(R.string.emty_err)}"
        val familyNameError = "${getString(R.string.family_name)} ${getString(R.string.emty_err)}"
        val firstNameError = "${getString(R.string.name)} ${getString(R.string.emty_err)}"
        val maxLengthPass = 8
        val maxLengthFamilyName = 10
        val maxLengthFirstName = 10

        var isValid = true

        if (!setErrorIfInvalid(edPass, passError)) {
            binding.edPass.isPasswordVisibilityToggleEnabled = false
        }

        isValid = setErrorIfInvalid(edPass, passError) && isValid
        isValid = setErrorIfInvalid(edFamilyName, familyNameError) && isValid
        isValid = setErrorIfInvalid(edFirstName, firstNameError) && isValid

        isValid = setErrorIfLengthExceeded(edPass, maxLengthPass, passError, this) && isValid
        isValid = setErrorIfLengthExceeded(
            edFamilyName, maxLengthFamilyName, familyNameError, this
        ) && isValid
        isValid = setErrorIfLengthExceeded(
            edFirstName, maxLengthFirstName, firstNameError, this
        ) && isValid

        return isValid
    }
}