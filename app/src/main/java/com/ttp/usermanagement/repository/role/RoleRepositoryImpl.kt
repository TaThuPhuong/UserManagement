package com.ttp.usermanagement.repository.role

import com.ttp.usermanagement.data.api.role.RoleApi
import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoleRepositoryImpl @Inject constructor(
    private val api: RoleApi
) : RoleRepository {
    override suspend fun getRole(): Flow<Resource<List<Role>>> = flow {
        try {
            emit(Resource.Loading)
            val result = api.getRole()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}