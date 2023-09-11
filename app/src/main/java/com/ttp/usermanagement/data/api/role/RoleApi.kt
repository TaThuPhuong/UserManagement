package com.ttp.usermanagement.data.api.role

import com.ttp.usermanagement.data.model.Role

interface RoleApi {
    suspend fun getRole(): List<Role>
}