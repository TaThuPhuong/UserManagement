package com.ttp.usermanagement.repository.auth

import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.RegisterUserParams
import com.ttp.usermanagement.data.params.UserLoginParams
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun userLogin(params: UserLoginParams): Flow<User?>
    suspend fun userRegister(params: RegisterUserParams): Flow<User?>
}