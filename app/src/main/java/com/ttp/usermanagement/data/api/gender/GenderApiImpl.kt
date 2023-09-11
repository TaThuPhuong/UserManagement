package com.ttp.usermanagement.data.api.gender

import android.content.Context
import com.ttp.usermanagement.R
import com.ttp.usermanagement.base.BaseResponse
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.model.Gender
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

class GenderApiImpl(
    context: Context,
    private val client: HttpClient,
    private val sessionManager: SessionManager
) : GenderApi {
    private val url = "${context.getString(R.string.base_url)}/gender"

    override suspend fun getGender(): List<Gender> {
        val token = sessionManager.getJwtToken() ?: ""
        val response = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }.bodyAsText()
        return (Json.decodeFromString<BaseResponse<List<Gender>>>(response)).data!!
    }
}