package com.ttp.usermanagement.ui.login.viewModel

import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.UserLoginParams
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(userId: String, password: String): Flow<Resource<User?>> = flow {
        try {
            if (userId.isEmpty() || password.isEmpty()) {
                emit(Resource.Error("Không được để trống Tên đăng nhập và Mật khẩu"))
                return@flow
            }
            emit(Resource.Loading)

            val request = UserLoginParams(
                userId = userId,
                password = password
            )

            repository.userLogin(request).collect { result ->
                if (result != null) {
                    sessionManager.updateSession(result.authToken!!, result.userId, result.fullName)
                    emit(Resource.Success(result))
                } else {
                    emit(Resource.Error("Tên đăng nhập hoặc mật khẩu không đúng!"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }
}