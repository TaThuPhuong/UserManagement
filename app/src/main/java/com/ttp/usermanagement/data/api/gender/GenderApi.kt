package com.ttp.usermanagement.data.api.gender

import com.ttp.usermanagement.data.model.Gender

interface GenderApi {
    suspend fun getGender(): List<Gender>
}