package com.quinengine

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String,
    val name: String,
    val category: String,
    val categoryId: String,
    val price: Double,
    val currency: String,
    private var _customAttributes: MutableMap<String, String> = mutableMapOf()
) {
    val customAttributes: Map<String, String>
        get() = _customAttributes.toMap()

    fun withCustomAttribute(key: String, value: String): Item {
        val updatedAttributes = _customAttributes.toMutableMap()
        updatedAttributes[key] = value
        return this.copy(_customAttributes = updatedAttributes)
    }
}