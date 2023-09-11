package com.ttp.usermanagement.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Gender(
    val genderId: Int,
    val genderName: String,
    val createUserId: String,
    val updateUserId: String,
    val createDate: Long,
    val updateDate: Long,
)