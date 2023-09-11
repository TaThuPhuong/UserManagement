package com.ttp.usermanagement.ui.main.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.common.util.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val sessionManager: SessionManager) : ViewModel() {

    private val _nameUser = MutableLiveData<String>()
    val nameUser: LiveData<String> = _nameUser

    fun logOut(context: Context) = viewModelScope.launch {
        SharedPref.setLogin(context, false)
        sessionManager.logout()
    }

    fun getFullName() = viewModelScope.launch {
        _nameUser.value = sessionManager.getNameUserLogin()
    }

}