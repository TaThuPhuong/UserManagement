package com.ttp.usermanagement.repository.user

import com.ttp.usermanagement.data.api.user.UserApi
import com.ttp.usermanagement.data.model.UserInfo
import com.ttp.usermanagement.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ttp.dev.data.model.Params.UpdateUserParams
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun getUsers(
        familyName: String?,
        firstName: String?,
        authorityId: Int
    ): Flow<Resource<List<UserInfo?>>> = flow {
        try {
            emit(Resource.Loading)
            val result = api.getUsers(familyName, firstName, authorityId)
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    override suspend fun getStatistics(): Flow<Resource<Map<String, Int>>> = flow {
        try {
            emit(Resource.Loading)
            val result = api.getStatistics()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    override suspend fun deleteUser(userId: String, loggedInUserId: String): Flow<Resource<Int>> =
        flow {
            try {
                emit(Resource.Loading)
                val result = api.deleteUser(userId, loggedInUserId)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    override suspend fun updateUser(user: UpdateUserParams): Flow<Resource<Int>> =
        flow {
            try {
                emit(Resource.Loading)
                val result = api.updateUser(user)
                emit(Resource.Success(result))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}