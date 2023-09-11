package com.ttp.usermanagement.repository.role

import com.ttp.usermanagement.data.model.Role
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow

interface RoleRepository {
    suspend fun getRole(): Flow<Resource<List<Role>>>
}