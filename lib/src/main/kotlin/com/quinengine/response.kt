package com.quinengine

import kotlinx.serialization.Serializable

typealias ResponseHandler = (Response?) -> Unit

@Serializable
data class Response(
    val content: Content? = null,
    val message: String,
    val responseCode: Int = 0
)

@Serializable
data class Content(
    val sessionId: String = "",
    val userId: String = "",
    val token: String = "",
    val interaction: Action? = null
) {
    fun user(): User {
        return User(id = userId, token = token, googleClientId = "")
    }
}