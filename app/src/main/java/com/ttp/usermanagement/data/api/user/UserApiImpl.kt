package com.ttp.usermanagement.data.api.user

import android.content.Context
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.BaseResponse
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import ttp.dev.data.model.Params.UpdateUserParams

class UserApiImpl(
    context: Context,
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : UserApi {

    private val getUsersUrl = "${context.getString(R.string.base_url_user)}/all"
    private val getStatisticsUrl = "${context.getString(R.string.base_url_user)}/statistics"
    private val deleteUserUrl = "${context.getString(R.string.base_url_user)}/delete"
    private val updateUserUrl = "${context.getString(R.string.base_url_user)}/update"

    override suspend fun getUsers(
        familyName: String?,
        firstName: String?,
        authorityId: Int
    ): List<UserInfo?> {
        val token = sessionManager.getJwtToken() ?: ""
        val response = client.get(getUsersUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            url {
                parameters.append("familyName", familyName ?: "")
                parameters.append("firstName", firstName ?: "")
                parameters.append("authorityId", authorityId.toString())
            }
        }.bodyAsText()
        return (Json.decodeFromString<BaseResponse<List<UserInfo?>>>(response)).data!!
    }

    override suspend fun updateUser(user: UpdateUserParams): Int {
        val token = sessionManager.getJwtToken() ?: ""
        val response = client.post(updateUserUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            contentType(ContentType.Application.Json)
            setBody(user)
        }.bodyAsText()
        return (Json.decodeFromString<BaseResponse<Int>>(response)).data!!
    }

    override suspend fun deleteUser(userId: String, loggedInUserId: String): Int {
        val token = sessionManager.getJwtToken() ?: ""
        val response = client.post(deleteUserUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
            url {
                parameters.append("userID", userId)
                parameters.append("userIDLogin", loggedInUserId)
            }
        }.bodyAsText()
        return (Json.decodeFromString<BaseResponse<Int>>(response)).data!!
    }

    override suspend fun getStatistics(): Map<String, Int> {
        val token = sessionManager.getJwtToken() ?: ""
        val response = client.get(getStatisticsUrl) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.bodyAsText()
        return (Json.decodeFromString<BaseResponse<Map<String, Int>>>(response)).data!!
    }
}