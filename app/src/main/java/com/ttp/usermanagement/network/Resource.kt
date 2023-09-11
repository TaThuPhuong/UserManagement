package com.ttp.usermanagement.network


sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
    data class Error(val message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}