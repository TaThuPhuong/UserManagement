package com.ttp.usermanagement.ui.login.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttp.usermanagement.common.util.SharedPref
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<Resource<User?>?>(null)
    val loginState: StateFlow<Resource<User?>?> = _loginState

    private var _sessionState = MutableLiveData<Boolean>()
    val sessionState: LiveData<Boolean> = _sessionState

    fun userLogin(userID: String, password: String) = viewModelScope.launch {
        loginUseCase(userID, password)
            .collectLatest {
                _loginState.value = it
            }
    }

    fun checkSession(context: Context) = viewModelScope.launch {
        _sessionState.value = SharedPref.isLogged(context)
    }

}