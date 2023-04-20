package com.quinengine

import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Http private constructor() {
    internal val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    companion object {
        private val configuration = HttpConfiguration()
        val sharedInstance = Http()
        fun setConfig(apiKey: String, domain: String) {
            configuration.apiKey = apiKey
            configuration.domain = domain
        }
    }

    private fun request(path: String, m: HttpMethod, b: String?): HttpRequestBuilder {
        val request = HttpRequestBuilder().apply {
            method = m
            url {
                protocol = URLProtocol.HTTPS
                host = configuration.url
                encodedPath = path
            }
            accept(ContentType.Application.Json)
            header("Origin", configuration.domain)
            header("X-Api-Key", configuration.apiKey)
            if (b != null) {
                body = b
            }
        }
        Logger.sharedInstance.log(msg = request.url.buildString())
        return request
    }

    private suspend fun execute(request: HttpRequestBuilder, completion: ResponseHandler) {
        try {
            Logger.sharedInstance.log(request.body.toString())
            val response = client.request<HttpResponse>(request)
            if (response.status != HttpStatusCode.OK) {
                Logger.sharedInstance.log(
                    "quin httpHandler:" +
                            " error statusCode: ${response.status.value}"
                )
                return
            }
            try {
                val res = json.decodeFromString<Response>(response.readText())
                Logger.sharedInstance.log(res.toString())
                completion(res)
            } catch (e: java.lang.Exception) {
                Logger.sharedInstance.log("quin httpHandler: encode error: ${e.message}")
            }
        } catch (e: ClientRequestException) {
            try {
                val res = json.decodeFromString<Response>(e.response.readText())
                Logger.sharedInstance.log(
                    "quin httpHandler: client request error with " +
                            "status code: ${e.response.status}, " +
                            "response code: ${res.responseCode}, " +
                            "error message: ${res.message}"
                )
            } catch (ee: java.lang.Exception) {
                Logger.sharedInstance.log(
                    "quin httpHandler: " +
                            "client request error: " +
                            "status code: ${e.response.status}"
                )
            }
        } catch (e: Throwable) {
            Logger.sharedInstance.log(e.message ?: "Http.execute has thrown an exception!")
        }
    }

    suspend fun post(path: String, b: String? = null, completion: ResponseHandler) {
        val request = request(path, HttpMethod.Post, b)
        execute(request, completion)
    }

    suspend fun get(path: String, completion: ResponseHandler) {
        val request = request(path, HttpMethod.Get, null)
        execute(request, completion)
    }
}