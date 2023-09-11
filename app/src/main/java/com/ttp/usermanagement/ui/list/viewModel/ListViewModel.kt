package com.ttp.usermanagement.ui.list.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.role.RoleRepository
import com.ttp.usermanagement.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val roleRepo: RoleRepository,
    private val userRepo: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _roleState = MutableLiveData<Resource<List<Role>>>()
    val roleState: LiveData<Resource<List<Role>>> = _roleState

    private val _userState = MutableLiveData<Resource<List<UserInfo?>>>()
    val userState: LiveData<Resource<List<UserInfo?>>> = _userState

    private val _deleteState = MutableLiveData<Resource<Int>>()
    val deleteState: LiveData<Resource<Int>> = _deleteState

    fun getRoles() {
        viewModelScope.launch {
            _roleState.value = Resource.Loading
            roleRepo.getRole().collect {
                _roleState.value = it
            }
        }
    }

    fun getUser(
        familyName: String?,
        firstName: String?,
        authorityId: Int
    ) {
        viewModelScope.launch {
            _userState.value = Resource.Loading
            userRepo.getUsers(familyName, firstName, authorityId).collect {
                _userState.value = it
            }
        }
    }

    fun deleteUser(userID: String?) {
        if (userID == null) {
            _deleteState.value = Resource.Loading
        } else {
            viewModelScope.launch {
                val loggedInUserId = sessionManager.getCurrentUserLogin() ?: ""
                _deleteState.value = Resource.Loading
                userRepo.deleteUser(userID, loggedInUserId).collect {
                    _deleteState.value = it
                }
            }
        }
    }
}