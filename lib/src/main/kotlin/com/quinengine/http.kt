package com.quinengine


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class Http private constructor() {
    companion object {
        private var configuration = HttpConfiguration()
        val sharedInstance = Http()
        fun setConfig(apiKey: String, domain: String) {
            configuration.apiKey = apiKey
            configuration.domain = domain
        }
    }

    internal val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
        }
    }

    private fun request(path: String, m: HttpMethod, b: String?): HttpRequestBuilder {
        return HttpRequestBuilder().apply {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Origin", configuration.domain)
            header("Referer", configuration.domain)
            header("X-Api-Key", configuration.apiKey)
            method = m
            url {
                protocol = URLProtocol.HTTPS
                host = configuration.url
                encodedPath = path
            }
            if (b != null) {
                setBody(b)
            }
        }
    }

    private suspend fun execute(request: HttpRequestBuilder, completion: ResponseHandler) {
        val res = client.request(request).body<Response>()
        completion(res)
    }

    suspend fun post(path: String, b: String? = null, completion: ResponseHandler) {
        val request = request(path, HttpMethod.Post, b)
        execute(request, completion)
    }

    fun closeConnection() {
        client.close()
    }

}