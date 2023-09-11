package com.ttp.usermanagement.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("userId")
    val userId: String,
    @SerialName("password")
    val password: String,
    @SerialName("familyName")
    val familyName: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("genderId")
    val genderId: Int,
    @SerialName("age")
    val age: Int?,
    @SerialName("authorityId")
    val authorityId: Int?,
    @SerialName("admin")
    val admin: Int,
    @SerialName("createUserId")
    val createUserId: String,
    @SerialName("updateUserId")
    val updateUserId: String,
    @SerialName("createDate")
    val createDate: Long,
    @SerialName("updateDate")
    val updateDate: Long,
    @SerialName("genderName")
    val genderName: String? = null,
    @SerialName("authorityName")
    val authorityName: String? = null,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("authToken")
    var authToken: String? = null
)

@Serializable
data class UserInfo(
    @SerialName("userId")
    val userId: String,
    @SerialName("password")
    val password: String,
    @SerialName("familyName")
    val familyName: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("genderId")
    val genderId: Int,
    @SerialName("age")
    val age: Int?,
    @SerialName("authorityId")
    val authorityId: Int?,
    @SerialName("admin")
    val admin: Int,
    @SerialName("genderName")
    val genderName: String? = null,
    @SerialName("authorityName")
    val authorityName: String? = null,
    @SerialName("fullName")
    val fullName: String
)