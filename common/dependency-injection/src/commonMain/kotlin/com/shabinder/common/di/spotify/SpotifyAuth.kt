package com.shabinder.common.di.spotify

import com.shabinder.common.di.kotlinxSerializer
import com.shabinder.common.models.spotify.TokenData
import com.shabinder.database.Database
import io.ktor.client.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.datetime.Clock

suspend fun authenticateSpotify(): TokenData {
    return spotifyAuthClient.post("https://accounts.spotify.com/api/token"){
        body = FormDataContent(Parameters.build { append("grant_type","client_credentials") })
    }
}

private val spotifyAuthClient by lazy {
    HttpClient {
        val clientId = "694d8bf4f6ec420fa66ea7fb4c68f89d"
        val clientSecret = "02ca2d4021a7452dae2328b47a6e8fe8"

        install(Auth) {
            basic {
                sendWithoutRequest = true
                username = clientId
                password = clientSecret
            }
        }
        install(JsonFeature) {
            serializer = kotlinxSerializer
        }
    }
}