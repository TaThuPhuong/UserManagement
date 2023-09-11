package com.ttp.usermanagement.base

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("exception")
    val exception: T? = null,
    @SerialName("message")
    val message: String,
    @SerialName("statusCode")
    val statusCode: StatusCode
)

@Serializable
data class StatusCode(
    @SerialName("value")
    val value: Int,
    @SerialName("description")
    val description: String
)