package com.ttp.usermanagement.repository.user

import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow
import ttp.dev.data.model.Params.UpdateUserParams

interface UserRepository {
    suspend fun getUsers(
        familyName: String?, firstName: String?, authorityId: Int
    ): Flow<Resource<List<UserInfo?>>>

    suspend fun getStatistics(): Flow<Resource<Map<String, Int>>>

    suspend fun deleteUser(userId: String, loggedInUserId: String): Flow<Resource<Int>>
    suspend fun updateUser(user: UpdateUserParams): Flow<Resource<Int>>
}