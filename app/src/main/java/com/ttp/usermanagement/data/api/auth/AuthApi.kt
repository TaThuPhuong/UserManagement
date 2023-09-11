package com.ttp.usermanagement.data.api.auth

import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.RegisterUserParams
import com.ttp.usermanagement.data.params.UserLoginParams

interface AuthApi {
    suspend fun userLogin(params: UserLoginParams): User?
    suspend fun userRegister(params: RegisterUserParams): User?
}