package com.ttp.usermanagement.di

import android.content.Context
import com.ttp.usermanagement.common.util.SessionManager
import com.ttp.usermanagement.data.api.auth.AuthApi
import com.ttp.usermanagement.data.api.auth.AuthApiImpl
import com.ttp.usermanagement.data.api.gender.GenderApi
import com.ttp.usermanagement.data.api.gender.GenderApiImpl
import com.ttp.usermanagement.data.api.role.RoleApi
import com.ttp.usermanagement.data.api.role.RoleApiImpl
import com.ttp.usermanagement.data.api.user.UserApi
import com.ttp.usermanagement.data.api.user.UserApiImpl
import com.ttp.usermanagement.network.KtorClient
import com.ttp.usermanagement.repository.auth.AuthRepository
import com.ttp.usermanagement.repository.auth.AuthRepositoryImpl
import com.ttp.usermanagement.repository.gender.GenderRepository
import com.ttp.usermanagement.repository.gender.GenderRepositoryImpl
import com.ttp.usermanagement.repository.role.RoleRepository
import com.ttp.usermanagement.repository.role.RoleRepositoryImpl
import com.ttp.usermanagement.repository.user.UserRepository
import com.ttp.usermanagement.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun getHttpClient(httpClient: KtorClient): HttpClient = httpClient.getHttpClient()

    @Singleton
    @Provides
    fun provideSessionManager(@ApplicationContext context: Context) = SessionManager(context)

    //Auth
    @Provides
    @Singleton
    fun provideAuthApi(@ApplicationContext context: Context, client: HttpClient): AuthApi =
        AuthApiImpl(context, client)

    @Provides
    fun provideAuthRepository(
        api: AuthApi, sessionManager: SessionManager
    ): AuthRepository = AuthRepositoryImpl(api, sessionManager)

    //Gender
    @Provides
    @Singleton
    fun provideGenderApi(
        @ApplicationContext context: Context, client: HttpClient, sessionManager: SessionManager
    ): GenderApi = GenderApiImpl(context, client, sessionManager)

    @Provides
    fun provideGenderRepository(api: GenderApi): GenderRepository = GenderRepositoryImpl(api)

    //Role
    @Provides
    @Singleton
    fun provideRoleApi(
        @ApplicationContext context: Context, client: HttpClient, sessionManager: SessionManager
    ): RoleApi = RoleApiImpl(context, client, sessionManager)

    @Provides
    fun provideRoleRepository(api: RoleApi): RoleRepository = RoleRepositoryImpl(api)

    //User
    @Provides
    @Singleton
    fun provideUserApi(
        @ApplicationContext context: Context, client: HttpClient, sessionManager: SessionManager
    ): UserApi = UserApiImpl(context, client, sessionManager)

    @Provides
    fun provideUserRepository(api: UserApi): UserRepository = UserRepositoryImpl(api)
}