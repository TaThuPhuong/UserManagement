package com.ttp.usermanagement.data.api.user

import com.ttp.usermanagement.data.model.UserInfo
import ttp.dev.data.model.Params.UpdateUserParams

interface UserApi {
    suspend fun getUsers(familyName: String?, firstName: String?, authorityId: Int): List<UserInfo?>
    suspend fun updateUser(user: UpdateUserParams): Int
    suspend fun deleteUser(userId: String, loggedInUserId: String): Int
    suspend fun getStatistics(): Map<String, Int>
}