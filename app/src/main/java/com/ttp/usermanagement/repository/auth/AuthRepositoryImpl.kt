package com.ttp.usermanagement.repository.auth

import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.api.auth.AuthApi
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.RegisterUserParams
import com.ttp.usermanagement.data.params.UserLoginParams
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sessionManager: SessionManager
) : AuthRepository {

    override suspend fun userLogin(params: UserLoginParams): Flow<User?> = flow {
        try {
            val result = api.userLogin(params)!!
            emit(result)
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            throw Exception(e.response.status.description)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            throw Exception(e.response.status.description)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            throw Exception(e.response.status.description)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

    override suspend fun userRegister(params: RegisterUserParams): Flow<User?> =
        flow {
            try {
                val result = api.userRegister(params)
                emit(result)
            } catch (e: RedirectResponseException) {
                // 3xx - responses
                throw Exception(e.response.status.description)
            } catch (e: ClientRequestException) {
                // 4xx - responses
                throw Exception(e.response.status.description)
            } catch (e: ServerResponseException) {
                // 5xx - responses
                throw Exception(e.response.status.description)
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
}