package com.ttp.usermanagement.repository.gender

import com.ttp.usermanagement.data.api.gender.GenderApi
import com.ttp.usermanagement.data.model.Gender
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenderRepositoryImpl @Inject constructor(
    private val api: GenderApi
) : GenderRepository {
    override suspend fun getGender(): Flow<Resource<List<Gender>>> = flow {
        try {
            emit(Resource.Loading)
            val result = api.getGender()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}