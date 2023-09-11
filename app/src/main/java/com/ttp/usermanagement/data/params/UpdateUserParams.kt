package ttp.dev.data.model.Params

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserParams(
    val userId: String,
    val password: String,
    val familyName: String,
    val firstName: String,
    val genderId: Int,
    val age: Int?,
    val authorityId: Int?,
    val admin: Int,
    val updateUserId: String
)