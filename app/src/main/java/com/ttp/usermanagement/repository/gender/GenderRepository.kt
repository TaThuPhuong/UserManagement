package com.ttp.usermanagement.repository.gender

import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow

interface GenderRepository {
    suspend fun getGender(): Flow<Resource<List<Gender>>>
}