package com.ttp.usermanagement.data.api.auth

import android.content.Context
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.BaseResponse
import com.ttp.usermanagement.data.model.User
import com.ttp.usermanagement.data.params.RegisterUserParams
import com.ttp.usermanagement.data.params.UserLoginParams
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class AuthApiImpl(context: Context, private val client: HttpClient) : AuthApi {
    private val loginUrl = "${context.getString(R.string.base_url_auth)}/login"
    private val registerUrl = "${context.getString(R.string.base_url_auth)}/register"

    override suspend fun userLogin(params: UserLoginParams): User? {
        val response = client.post(loginUrl) {
            contentType(ContentType.Application.Json)
            setBody(params)
        }.bodyAsText()

        val result = Json.decodeFromString<BaseResponse<User?>>(response)
        return result.data ?: result.exception
    }

    override suspend fun userRegister(params: RegisterUserParams): User? {
        val response = client.post(registerUrl) {
            contentType(ContentType.Application.Json)
            setBody(params)
        }.bodyAsText()

        val result = Json.decodeFromString<BaseResponse<User?>>(response)
        return result.data ?: result.exception
    }
}