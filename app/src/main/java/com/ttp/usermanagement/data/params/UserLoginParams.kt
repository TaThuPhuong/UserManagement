package com.ttp.usermanagement.data.params

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginParams(
    val userId: String,
    val password: String
)

