package com.quinengine

import android.content.Context
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString

interface ECommerce {
    fun sendPageViewHomeEvent(context: Context, completion: ActionHandler)
    fun sendPageViewListingEvent(context: Context, label: String, completion: ActionHandler)
    fun sendAddToCartListingEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )

    fun sendFilterEvent(context: Context, completion: ActionHandler)
    fun sendPageViewDetailEvent(context: Context, item: Item?, completion: ActionHandler)
    fun sendAddToCartDetailEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )

    fun sendAddToFavouritesEvent(context: Context, item: Item?, completion: ActionHandler)
    fun sendProductInfoEvent(context: Context, item: Item?, completion: ActionHandler)
    fun sendCommentsEvent(context: Context, completion: ActionHandler)
    fun sendQuantityDetailEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )

    fun sendQuantityCartEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )

    fun sendGoToCartEvent(context: Context, completion: ActionHandler)
    fun sendContinueShoppingEvent(context: Context, completion: ActionHandler)
    fun sendRemoveFromCartEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )

    fun sendEmptyCartEvent(context: Context, completion: ActionHandler)
    fun sendCheckoutEvent(context: Context, completion: ActionHandler)
    fun sendLoginEvent(context: Context, completion: ActionHandler)
    fun sendDiscountCodeEvent(context: Context, discountCode: String, completion: ActionHandler)
    fun sendDeliveryFeeEvent(context: Context, completion: ActionHandler)
    fun sendAddressEvent(context: Context, completion: ActionHandler)
    fun sendPaymentTypeEvent(context: Context, completion: ActionHandler)
    fun sendPurchaseCompletedEvent(
        context: Context,
        totalBasketSize: Float,
        completion: ActionHandler
    )

    fun sendAddToCartServiceEvent(
        context: Context,
        item: Item?,
        quantity: Int,
        completion: ActionHandler
    )
}

class Quin private constructor() {

    companion object {
        private const val pathSession = "session"
        private const val pathEvent = "event"
        private const val testEvent = "test-event"
        private val sharedInstance = Quin()
        val eCommerce = ECommerceImpl() as ECommerce
        fun setConfig(apiKey: String, domain: String, debug: Boolean = false) {
            Http.setConfig(apiKey, domain)
            Logger.setConfig(debug)
        }

        fun setUser(context: Context, googleClientId: String) {
            val user = sharedInstance.user(context)
            user?.googleClientId = googleClientId
        }

        fun track(context: Context, event: Event, completion: ActionHandler) {
            val user = sharedInstance.user(context)
            if (user == null) {
                Logger.sharedInstance.log("quin track: user is null")
                return
            }
            val req = event.withUser(user)
            val httpBody: String
            try {
                httpBody = Http.sharedInstance.json.encodeToString(req)
            } catch (e: java.lang.Exception) {
                Logger.sharedInstance.log("quin track: encode error")
                return
            }
            runBlocking {
                Http.sharedInstance.post(pathEvent, httpBody) { response ->
                    sharedInstance.saveUser(
                        context,
                        response
                    ); completion(response!!.content!!.interaction)
                }
            }
        }

        fun test(context: Context, event: Event, completion: ActionHandler) {
            val user = sharedInstance.user(context)
            if (user == null) {
                Logger.sharedInstance.log("quin track: user is null")
                return
            }
            val req = event.withUser(user)
            val httpBody: String
            try {
                httpBody = Http.sharedInstance.json.encodeToString(req)
            } catch (e: Exception) {
                Logger.sharedInstance.log("quin track: encode error")
                return
            }

            runBlocking {
                Http.sharedInstance.post(testEvent, httpBody) { response ->
                    sharedInstance.saveUser(
                        context,
                        response
                    );completion(response!!.content!!.interaction)
                }
            }
        }
    }


    internal class ECommerceImpl() : ECommerce {
        override fun sendPageViewHomeEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.pageViewHomeEvent(), completion = completion)
        }

        override fun sendPageViewListingEvent(
            context: Context,
            label: String,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.pageViewListingEvent(label = label),
                completion = completion
            )
        }

        override fun sendAddToCartListingEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.addToCartListingEvent(item = item, quantity = quantity),
                completion = completion
            )
        }

        override fun sendFilterEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.filterEvent(), completion = completion)
        }

        override fun sendPageViewDetailEvent(
            context: Context,
            item: Item?,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.pageViewDetailEvent(item = item),
                completion = completion
            )
        }

        override fun sendAddToCartDetailEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.addToCartDetailEvent(item = item, quantity = quantity),
                completion = completion
            )
        }

        override fun sendAddToFavouritesEvent(
            context: Context,
            item: Item?,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.addToFavouritesEvent(item = item),
                completion = completion
            )
        }

        override fun sendProductInfoEvent(
            context: Context,
            item: Item?,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.productInfoEvent(item = item),
                completion = completion
            )
        }

        override fun sendCommentsEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.commentsEvent(), completion = completion)
        }

        override fun sendQuantityDetailEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.quantityDetailEvent(item = item, quantity = quantity),
                completion = completion
            )
        }

        override fun sendQuantityCartEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.quantityCartEvent(item = item, quantity = quantity),
                completion = completion
            )
        }

        override fun sendGoToCartEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.goToCartEvent(), completion = completion)
        }

        override fun sendContinueShoppingEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.continueShoppingEvent(), completion = completion)
        }

        override fun sendRemoveFromCartEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.removeFromCartEvent(item = item, quantity = quantity),
                completion = completion
            )
        }

        override fun sendEmptyCartEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.emptyCartEvent(), completion = completion)
        }

        override fun sendCheckoutEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.checkoutEvent(), completion = completion)
        }

        override fun sendLoginEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.loginEvent(), completion = completion)
        }

        override fun sendDiscountCodeEvent(
            context: Context,
            discountCode: String,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.discountCodeEvent(discountCode = discountCode),
                completion = completion
            )
        }

        override fun sendDeliveryFeeEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.deliveryFeeEvent(), completion = completion)
        }

        override fun sendAddressEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.addressEvent(), completion = completion)
        }

        override fun sendPaymentTypeEvent(context: Context, completion: ActionHandler) {
            track(context, event = Event.eCommerce.paymentTypeEvent(), completion = completion)
        }

        override fun sendPurchaseCompletedEvent(
            context: Context,
            totalBasketSize: Float,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.purchaseCompletedEvent(totalBasketSize = totalBasketSize),
                completion = completion
            )
        }

        override fun sendAddToCartServiceEvent(
            context: Context,
            item: Item?,
            quantity: Int,
            completion: ActionHandler
        ) {
            track(
                context,
                event = Event.eCommerce.addToCartServiceEvent(item = item, quantity = quantity),
                completion = completion
            )
        }
    }

    private fun user(context: Context): User? {
        val user = UserStore.load(context)
        if (user == null) {
            val mutex = Mutex(false)
            runBlocking {
                mutex.withLock {
                    Http.sharedInstance.post(pathSession, null) { response ->
                        saveUser(context, response)
                    }
                }
            }
        }
        return UserStore.load(context)
    }

    private fun saveUser(context: Context, response: Response?) {
        if (response?.content == null) {
            Logger.sharedInstance.log("quin saveUser: response user is null")
            return
        }
        val user = response.content.user()
        UserStore.save(context, user)
    }
}