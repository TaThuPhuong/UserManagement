package com.ttp.usermanagement.data.params

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserParams(
    val userId: String,
    val password: String,
    val familyName: String,
    val firstName: String,
    val genderId: Int,
    val age: Int?,
    val authorityId: Int?,
    val admin: Int,
    val createUserId: String
)
