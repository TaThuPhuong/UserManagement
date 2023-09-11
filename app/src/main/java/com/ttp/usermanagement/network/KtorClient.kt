package com.ttp.usermanagement.network


import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.jackson.jackson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Inject


class KtorClient @Inject constructor() {

    fun getHttpClient() = HttpClient(Android) {

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v(TAG_KTOR_LOGGER, message)
                }

            }
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
            jackson()
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d(TAG_HTTP_STATUS_LOGGER, "${response.status.value}")
            }
        }

//        install(DefaultRequest) {
//            header(HttpHeaders.ContentType, ContentType.Application.Json)
//        }

    }

    companion object {
        private const val TAG_KTOR_LOGGER = "ktor_logger:"
        private const val TAG_HTTP_STATUS_LOGGER = "http_status:"
    }
}


