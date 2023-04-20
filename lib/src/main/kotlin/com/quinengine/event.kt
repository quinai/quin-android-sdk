package com.quinengine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class EventCategory {
    home,
    listing,
    detail,
    cart,
    checkout,
    service,
    interaction,
    reaction
}

enum class EventLabel {
    addtobasket,
    addtofavourites,
    productinfo,
    deliveryinfo,
    comments,
    quantity,
    gotocart,
    continueshopping,
    removefromcart,
    emptycart,
    checkout,
    login,
    discountcode,
    deliveryfee,
    address,
    paymenttype,
    purchasecompleted
}

enum class EventAction {
    pageview,
    click
}

interface ECommerceEvent {
    fun pageViewHomeEvent(): Event
    fun pageViewListingEvent(label: String): Event
    fun addToCartListingEvent(item: Item?, quantity: Int): Event
    fun filterEvent(): Event
    fun pageViewDetailEvent(item: Item?): Event
    fun addToCartDetailEvent(item: Item?, quantity: Int): Event
    fun addToFavouritesEvent(item: Item?): Event
    fun productInfoEvent(item: Item?): Event
    fun deliveryInfoEvent(item: Item?): Event
    fun commentsEvent(): Event
    fun quantityDetailEvent(item: Item?, quantity: Int): Event
    fun quantityCartEvent(item: Item?, quantity: Int): Event
    fun goToCartEvent(): Event
    fun continueShoppingEvent(): Event
    fun removeFromCartEvent(item: Item?, quantity: Int): Event
    fun emptyCartEvent(): Event
    fun checkoutEvent(): Event
    fun loginEvent(): Event
    fun discountCodeEvent(discountCode: String): Event
    fun deliveryFeeEvent(): Event
    fun addressEvent(): Event
    fun paymentTypeEvent(): Event
    fun purchaseCompletedEvent(totalBasketSize: Float): Event
    fun addToCartServiceEvent(item: Item?, quantity: Int): Event
}

@Serializable
data class Event(
    val category: String,
    val action: String,
    val label: String = "",
    val url: String = "",
    val item: Item? = null,
    private val platform: String = "android",
    @SerialName("customAttributes") private var _customAttributes: MutableMap<String, String> = mutableMapOf()
) {
    internal var userId: String = ""
    internal var token: String = ""
    internal var cid: String = ""

    val customAttributes: Map<String, String>
        get() = _customAttributes.toMap()

    internal fun withUser(user: User): Event {
        this.userId = user.id
        this.token = user.token
        this.cid = user.googleClientId
        return this
    }

    fun withCustomAttribute(key: String, value: String): Event {
        this._customAttributes[key] = value
        return this
    }

    private class ECommerceImpl : ECommerceEvent {
        override fun pageViewHomeEvent(): Event {
            return Event(
                category = EventCategory.home.toString(), action = EventAction.pageview.toString()
            )
        }

        override fun pageViewListingEvent(label: String): Event {
            return Event(
                category = EventCategory.listing.toString(),
                action = EventAction.pageview.toString(),
                label = label
            )
        }

        override fun addToCartListingEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.listing.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.addtobasket.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }

        override fun filterEvent(): Event {
            return Event(
                category = EventCategory.listing.toString(), action = EventAction.click.toString()
            )
        }

        override fun pageViewDetailEvent(item: Item?): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.pageview.toString(),
                label = item!!.category,
                item = item
            )
        }

        override fun addToCartDetailEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.addtobasket.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }

        override fun addToFavouritesEvent(item: Item?): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.addtofavourites.toString(),
                item = item
            )
        }

        override fun productInfoEvent(item: Item?): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.productinfo.toString(),
                item = item
            )
        }

        override fun deliveryInfoEvent(item: Item?): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.deliveryinfo.toString(),
                item = item
            )
        }

        override fun commentsEvent(): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.comments.toString()
            )
        }

        override fun quantityDetailEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.detail.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.quantity.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }

        override fun quantityCartEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.quantity.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }

        override fun goToCartEvent(): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.gotocart.toString()
            )
        }

        override fun continueShoppingEvent(): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.continueshopping.toString()
            )
        }

        override fun removeFromCartEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.removefromcart.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }

        override fun emptyCartEvent(): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.emptycart.toString()
            )
        }

        override fun checkoutEvent(): Event {
            return Event(
                category = EventCategory.cart.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.checkout.toString()
            )
        }

        override fun loginEvent(): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.login.toString()
            )
        }

        override fun discountCodeEvent(discountCode: String): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.discountcode.toString()
            ).withCustomAttribute("discountcode", discountCode)
        }

        override fun deliveryFeeEvent(): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.deliveryfee.toString()
            )
        }

        override fun addressEvent(): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.address.toString()
            )
        }

        override fun paymentTypeEvent(): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.paymenttype.toString()
            )
        }

        override fun purchaseCompletedEvent(totalBasketSize: Float): Event {
            return Event(
                category = EventCategory.checkout.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.purchasecompleted.toString()
            ).withCustomAttribute("totalBasketSize", totalBasketSize.toString())
        }

        override fun addToCartServiceEvent(item: Item?, quantity: Int): Event {
            return Event(
                category = EventCategory.service.toString(),
                action = EventAction.click.toString(),
                label = EventLabel.addtobasket.toString(),
                item = item
            ).withCustomAttribute("quantity", quantity.toString())
        }
    }

    companion object {
        val eCommerce = ECommerceImpl() as ECommerceEvent
    }
}

