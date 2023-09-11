package com.ttp.usermanagement.ui.update.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.gender.GenderRepository
import com.ttp.usermanagement.repository.role.RoleRepository
import com.ttp.usermanagement.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ttp.dev.data.model.Params.UpdateUserParams
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val roleRepo: RoleRepository,
    private val genderRepo: GenderRepository,
    private val userRepo: UserRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _roleState = MutableLiveData<Resource<List<Role>>>()
    val roleState: LiveData<Resource<List<Role>>> = _roleState

    private val _genderState = MutableLiveData<Resource<List<Gender>>>()
    val genderState: LiveData<Resource<List<Gender>>> = _genderState

    private val _updateState = MutableLiveData<Resource<Int>>()
    val updateState: LiveData<Resource<Int>> = _updateState

    fun fetchRolesAndGenders() {
        viewModelScope.launch {
            val rolesFlow = roleRepo.getRole()
            val gendersFlow = genderRepo.getGender()

            // Combine cả hai Flow lại với nhau và cập nhật LiveData
            combine(rolesFlow, gendersFlow) { rolesResource, gendersResource ->
                _roleState.value = rolesResource
                _genderState.value = gendersResource
            }.collect()
        }
    }

    fun updateUser(
        userId: String, pass: String, familyName: String, firstName: String,
        selectedGenderId: Int, age: Int?, selectedRoleId: Int, isAdmin: Int
    ) {
        viewModelScope.launch {
            _updateState.value = Resource.Loading
            val updateUser = sessionManager.getCurrentUserLogin() ?: ""
            val params = UpdateUserParams(
                userId,
                pass,
                familyName,
                firstName,
                selectedGenderId,
                age,
                selectedRoleId,
                isAdmin,
                updateUser
            )
            userRepo.updateUser(params).collect {
                _updateState.value = it
            }
        }
    }
}