package com.quinengine

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var id: String,
    var token: String,
    var googleClientId: String
) {}