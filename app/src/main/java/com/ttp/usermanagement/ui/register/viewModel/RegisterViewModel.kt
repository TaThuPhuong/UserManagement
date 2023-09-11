package com.ttp.usermanagement.ui.register.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.gender.GenderRepository
import com.ttp.usermanagement.repository.role.RoleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val roleRepo: RoleRepository,
    private val genderRepo: GenderRepository,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _roleState = MutableLiveData<Resource<List<Role>>>()
    val roleState: LiveData<Resource<List<Role>>> = _roleState

    private val _genderState = MutableLiveData<Resource<List<Gender>>>()
    val genderState: LiveData<Resource<List<Gender>>> = _genderState

    private val _registerState = MutableStateFlow<Resource<User>?>(null)
    val registerState: StateFlow<Resource<User>?> = _registerState

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

    fun registerUser(
        userId: String,
        pass: String,
        familyName: String,
        firstName: String,
        selectedGenderId: Int,
        age: Int?,
        selectedRoleId: Int,
        isAdmin: Int
    ) = viewModelScope.launch {
        registerUseCase(
            userId,
            pass,
            familyName,
            firstName,
            selectedGenderId,
            age,
            selectedRoleId,
            isAdmin,
        )
            .collectLatest {
                _registerState.value = it
            }
    }


    fun getRoles() {
        viewModelScope.launch {
            _roleState.value = Resource.Loading
            roleRepo.getRole().collect {
                _roleState.value = it
            }
        }
    }

    fun getGenders() {
        viewModelScope.launch {
            _genderState.value = Resource.Loading
            genderRepo.getGender().collect {
                _genderState.value = it
            }
        }
    }
}