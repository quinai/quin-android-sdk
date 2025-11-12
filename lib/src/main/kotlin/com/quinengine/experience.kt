package com.quinengine



import kotlinx.serialization.Serializable

typealias ExperienceHandler = (Experience?) -> Unit

@Serializable
class Campaign(
    val paddle: Boolean? = null,
    val position: String? = null,
    val contentType: String? = null,
    val code: String? = null
){
    override fun equals(other: Any?): Boolean {
        if(this==other) return true
        if (javaClass != other?.javaClass) return false

        other as Campaign
        if(paddle != other.paddle) return false
        if(position != other.position) return false
        if(contentType != other.contentType) return false
        return code != other.code

    }

    override fun hashCode(): Int {
        var result = paddle?.hashCode() ?: 0
        result = 31 * result + (position?.hashCode() ?: 0)
        result = 31 * result + (contentType?.hashCode() ?: 0)
        result = 31 * result + (code?.hashCode() ?: 0)

        return result
    }

    override fun toString(): String {
        return "Campaign(paddle=$paddle," +
                " position=$position," +
                " contentType=$contentType," +
                " code=$code" + ")"
    }
}

@Serializable
class Experience(
    val campaignContent: Campaign? = null,
    val type: String? = null,
    val promotionCode: String? = null
){
    override fun equals(other: Any?): Boolean {
        if(this==other) return true
        if (javaClass != other?.javaClass) return false

        other as Experience
        if(campaignContent != other.campaignContent) return false
        if(type != other.type) return false

        return promotionCode != other.promotionCode

    }

    override fun hashCode(): Int {
        var result = campaignContent?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (promotionCode?.hashCode() ?: 0)

        return result
    }

    override fun toString(): String {
        return "Experience(campaignContent=$campaignContent," +
                " type=$type," +
                " promotionCode=$promotionCode," +")"
    }
}