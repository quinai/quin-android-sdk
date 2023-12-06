package com.quinengine

import kotlinx.serialization.Serializable

typealias ActionHandler = (Action?) -> Unit

enum class ActionType {
    form,
    discount,
    upsell,
    information,
    retargeting,
    badge
}

enum class ActionPosition {
    center,
    topLeft,
    topRight,
    bottomLeft,
    bottomRight
}

@Serializable
class Action(
    val actionId: String? = null,
    val actionName: String? = null,
    val actionType: String? = null,
    val category: String? = null,
    val promotionCode: String? = null,
    val custom: Boolean? = null,
    val avgPrice: Double? = null,
    val display: Display? = null,
    val html: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Action
        if (actionId != other.actionId) return false
        if (actionName != other.actionName) return false
        if (actionType != other.actionType) return false
        if (category != other.category) return false
        if (promotionCode != other.promotionCode) return false
        if (custom != other.custom) return false
        if (avgPrice != other.avgPrice) return false
        if (display != other.display) return false
        return html == other.html
    }

    override fun hashCode(): Int {
        var result = actionId?.hashCode() ?: 0
        result = 31 * result + (actionName?.hashCode() ?: 0)
        result = 31 * result + (actionType?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (promotionCode?.hashCode() ?: 0)
        result = 31 * result + (custom?.hashCode() ?: 0)
        result = 31 * result + (avgPrice?.hashCode() ?: 0)
        result = 31 * result + (display?.hashCode() ?: 0)
        result = 31 * result + (html?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Action(actionId=$actionId," +
                " actionName=$actionName," +
                " actionType=$actionType," +
                " category=$category," +
                " promotionCode=$promotionCode," +
                " custom=$custom," +
                " avgPrice=$avgPrice," +
                " display=$display," +
                " html=$html)"
    }
}

@Serializable
data class Display(
    val paddle: Boolean? = null,
    val position: String? = null,
    val fields: Map<String, DisplayField>? = null,
    val properties: Map<String, DisplayProperty>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Display
        if (paddle != other.paddle) return false
        if (position != other.position) return false
        if (fields != other.fields) return false
        return properties == other.properties
    }

    override fun hashCode(): Int {
        var result = paddle?.hashCode() ?: 0
        result = 31 * result + (position?.hashCode() ?: 0)
        result = 31 * result + (fields?.hashCode() ?: 0)
        result = 31 * result + (properties?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Display(paddle=$paddle, position=$position, fields=$fields, properties=$properties)"
    }
}

@Serializable
data class DisplayField(
    val name: String? = null,
    val text: String? = null,
    val color: String? = null,
    val url: String? = null,
    val position: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DisplayField
        if (name != other.name) return false
        if (text != other.text) return false
        if (color != other.color) return false
        if (url != other.url) return false
        return position == other.position
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (position?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "DisplayField(name=$name, text=$text, color=$color, url=$url, position=$position)"
    }
}

@Serializable
data class DisplayProperty(
    val propertyType: String? = null,
    val label: String? = null,
    val placeholder: String? = null,
    val required: String? = null,
    val options: Array<String>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DisplayProperty
        if (propertyType != other.propertyType) return false
        if (label != other.label) return false
        if (placeholder != other.placeholder) return false
        if (required != other.required) return false
        if (options != null) {
            if (other.options == null) return false
            if (!options.contentEquals(other.options)) return false
        } else if (other.options != null) return false
        return true
    }

    override fun hashCode(): Int {
        var result = propertyType?.hashCode() ?: 0
        result = 31 * result + (label?.hashCode() ?: 0)
        result = 31 * result + (placeholder?.hashCode() ?: 0)
        result = 31 * result + (required?.hashCode() ?: 0)
        result = 31 * result + (options?.contentHashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "DisplayProperty(propertyType=$propertyType," +
                " label=$label," +
                " placeholder=$placeholder," +
                " required=$required," +
                " options=${options?.contentToString()})"
    }
}