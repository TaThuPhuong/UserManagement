package com.ttp.usermanagement.ui.register.viewModel

import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.RegisterUserParams
import com.ttp.usermanagement.network.Resource
import com.ttp.usermanagement.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val sessionManager: SessionManager
) {
    suspend operator fun invoke(
        userId: String,
        pass: String,
        familyName: String,
        firstName: String,
        selectedGenderId: Int,
        age: Int?,
        selectedRoleId: Int,
        isAdmin: Int
    ): Flow<Resource<User>?> = flow {
        try {
            emit(Resource.Loading)
            val createUserId = sessionManager.getCurrentUserLogin() ?: ""

            val params = RegisterUserParams(
                userId,
                pass,
                familyName,
                firstName,
                selectedGenderId,
                age,
                selectedRoleId,
                isAdmin,
                createUserId
            )

            repository.userRegister(params).collect { user ->
                if (user != null) {
                    emit(Resource.Success(user))
                } else {
                    emit(Resource.Error("UserID này đã tồn tại"))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Unknown Error"))
        }
    }
}