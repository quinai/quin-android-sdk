# Quin Audience Engine Android SDK

![image (1)](https://user-images.githubusercontent.com/112876992/222405013-487c28f7-5bfc-4265-8f0c-1b8a890101f7.png)

***

1. What is Quin Quin Audience Engine ?

Quin Audience Engine is a real-time digital customer analytics tool that helps e-commerce websites predict visitors' behavior after only 3-clicks, and engage them in real-time.

2. What is Quin Audience Engine Android SDK ?

Quin Audience Engine Android SDK is used to create and send events to the Quin AI backend. In result of tracked events, you will get actions.

***

## SETUP

Quin Engine Android SDK is distributed through [Maven Central(Sonatype)](https://central.sonatype.com/artifact/com.quinengine/android-sdk). In order to use it you need to add the following Gradle dependency to the build.gradle file.

### Gradle 

```groovy
implement 'com.quinengine:android-sdk:<version_number>'
```

Then import the library by following lines.

```kotlin
import com.quinengine.*
```

To use the sdk, first you need to initialize it by setting configs and the user with the following lines.

```kotlin
Quin.setConfig(<testApiKey>, <testApiKey>, debug = false)
Quin.setUser(context, <google_client_id>)
 ```

> Debug option is used to print logs to the console. 

> Context is your current context. We are taking contexts as parameters in order to prevent any leaks.

After the initialization is done, you can start sending events.

***

## Structures

### Item
The item structure holds the information about the item such as name, category, price etc. Item class is sent inside event to give item information of that event. 

```kotlin
Item {
 id: String,
 name: String,
 category: String,
 price: Decimal,
 currency: String,
}
```

Here is an example of Item creation.

```kotlin
Item(id= "1250353863",
 name= "wooden chair",
 category= "Garden",
 price= 39.99,
 currency= "USD"
```

### Action
Action is the structure that tracker or send functions returns. Action holds the properties such as category, promotion code, display etc.
Resulted action which has the following structure can be used to show pop-ups on the screen.

```kotlin
Action {
   actionId: String?
   actionName: String?
   actionType: String?
   category: String?
   promotionCode: String?
   custom: Boolean?
   avgPrice: Double?
   display: Display?
   html: String?
}
```

### Display
Display structure holds the actions display properties that is required to draw the pop-up.

```kotlin
Display {
    paddle: Boolean?
    position: String?
    fields: Map<String,DisplayField>?
    properties: Map<String,DisplayProperty>?
}
```

### DisplayField
DisplayField structure holds the field entity of an display object.

```kotlin
DisplayField {
    name: String?
    text: String?
    color: String?
    url: String?
    position: String?
}
```

### DisplayProperty
DisplayProperty structure holds the property entity of an display object.

```kotlin
DisplayProperty {
    propertyType: String?
    label: String?
    placeholder: String?
    required: String?
    options: Array<String>?
}
```

***

## Sending Events

SDK allows us to send events and recieve actions in result. There are 2 ways to send events to the Quin AI's backend service which are using predefined send event functions and creating custom events and sending them manually. These sending functions take a completion parameter as type of ActionHandler which is simply:

```kotlin
typealias ActionHandler = (Action?) -> Unit
```

You can pass completions to those functions and use resulted actions in your code.

### Sending Predefined events

In Quin we have a set of predefined event sender functions that require minimum set of data for the backend such as widely known e-commerce events.
Functions that send predefined events by Quin SDK are listed below.

```kotlin
sendPageViewHomeEvent(context: Context, completion: ActionHandler)
sendPageViewListingEvent(context: Context, label: String, completion: ActionHandler)
sendAddToCartListingEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
sendFilterEvent(context: Context, completion: ActionHandler)
sendPageViewDetailEvent(context: Context, item: Item?, completion: ActionHandler)
sendAddToCartDetailEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
sendAddToFavouritesEvent(context: Context, item: Item?, completion: ActionHandler)
sendProductInfoEvent(context: Context, item: Item?, completion: ActionHandler)
sendCommentsEvent(context: Context, completion: ActionHandler)
sendQuantityDetailEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
sendQuantityCartEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
sendGoToCartEvent(context: Context, completion: ActionHandler)
sendContinueShoppingEvent(context: Context, completion: ActionHandler)
sendRemoveFromCartEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
sendEmptyCartEvent(context: Context, completion: ActionHandler)
sendCheckoutEvent(context: Context, completion: ActionHandler)
sendLoginEvent(context: Context, completion: ActionHandler)
sendDiscountCodeEvent(context: Context, discountCode: String, completion: ActionHandler)
sendDeliveryFeeEvent(context: Context, completion: ActionHandler)
sendAddressEvent(context: Context, completion: ActionHandler)
sendPaymentTypeEvent(context: Context, completion: ActionHandler)
sendPurchaseCompletedEvent(context: Context, totalBasketSize: Float, completion: ActionHandler)
sendAddToCartServiceEvent(context: Context, item: Item?, quantity: Int, completion: ActionHandler)
```

All predefined functions above are defined inside e-commerce interface to create an abstraction to users. You can simply send events from interface variable inside Quin singleton class. Following lines explain how to use them. 

```kotlin
Quin.eCommerce.sendFilterEvent(context) { action ->
    println(action?.actionId ?: "action is null")
}
```

### Sending Custom events

In order to send custom events first you need to create event to be sent. We have declared some event functions that returns widely known e-commerce events. These events are listed below. 

```kotlin
pageViewHomeEvent()
pageViewListingEvent(label: String)
addToCartListingEvent(item: Item?, quantity: Int)
filterEvent()
pageViewDetailEvent(item: Item?)
addToCartDetailEvent(item: Item?, quantity: Int)
addToFavouritesEvent(item: Item?)
productInfoEvent(item: Item?)
deliveryInfoEvent(item: Item?)    
commentsEvent()
quantityDetailEvent(item: Item?, quantity: Int)
quantityCartEvent(item: Item?, quantity: Int)
goToCartEvent()
continueShoppingEvent()
removeFromCartEvent(item: Item?, quantity: Int)
emptyCartEvent()
checkoutEvent()
loginEvent()
discountCodeEvent(discountCode: String)
deliveryFeeEvent()
adressEvent()
paymentTypeEvent()
purchaseCompletedEvent(totalBasketSize: Int)
addToCartServiceEvent(item: Item?, quantity: Int)
```

All predefined functions above are defined inside e-commerce interface to create an abstraction to users. You can simply crewte events from interface variable inside Event singleton class. Following lines explain how to use them. 

* Returns Event structure with filled with home pageview data.
```kotlin
val event = Event.eCommerce.pageViewHomeEvent()
```

* Takes item as Item struct and quantity as integer. Returns Event structure filled with add to cart, item, and quantity data.
> Item can be nil.
```kotlin
val event = Event.eCommerce.addToCartListingEvent(item: <Item>, quantity: 2)
```

Also adding custom attributes to those events are possible by function ```withCustomAttribute(key: String, value: String)```. Following example shows how to use it.

```kotlin
val event = Event.eCommerce.pageViewHomeEvent().withCustomAttribute("color", "red")
```

After creating the event you can send them to the Quin services using ```track(context: Context, event: Event, completion: ActionHandler)```. Following line shows how to use it.

```kotlin
val event = Event.eCommerce.pageViewHomeEvent()
Quin.track(context, event = event) { action ->
    println(action?.toString() ?: "action is null")
}
```

If those functions does not satisfy your use cases, you can create custom events and send them to Quin services again using track function. Following example demonstrates how to create custom events and send it. 

```kotlin
val item = Item("id", "name", "cat", 5.00, "usd")
val event = Event(category = EventCategory.home.toString(),
                  action = EventAction.click.toString(), 
                  label = "custom label", 
                  url = "ex-screen", 
                  item = item)
Quin.track(context, event = event) { action ->
    println(action?.toString() ?: "action is null")
}
```

***

## Quick Start

After adding library to your project and setting up initialization, we have declared a test function that sends a test event to Quin services, and returns a mock Action filled with below data.


```kotlin
Action: {
    actionId:      "d3da1e3c5b1f8159",
    actionType:    "upsell",
    category:      "Garden > Storage > Storage Wardrobe",
    promotionCode: "QTK1-4RSS-RR38-FTGR",
    custom:        false,
    display: {
        paddle:   true,
	position: "center",
        fields: {
            "actionButton": {
		Name:     "actionButton",
		Text:     "Copy",
		Color:    "#f59f1d",
		Url:      "",
		Position: "",
	    },
            "dismissButton": {
		Name:     "dismissButton",
		Text:     "Close",
		Color:    "#ffffff",
		Url:      "",
		Position: "",
	    },
	    "image": {
		Name:     "image",
		Text:     "",
		Color:    "",
		Url:      "https://cdn.thequin.ai/act/20221221-efab69f589f50a1a62ae5d4a2c785e29.png",
		Position: "top",
	    },
            "background": {
		Name:     "background",
		Text:     "",
		Color:    "#ffe8b3",
		Url:      "",
		Position: "",
	    },
            "description": {
		Name:     "description",
		Text:     "Don't forget to use your %10 discount code. You can get a maximum discount of $100",
		Color:    "#000000",
		Url:      "",
		Position: "",
	    }
        },
    properties: nil
    }
}
```


You can use this action as a reference and use it to draw pop-ups etc. In order to send test event you can use Quin singleton's ```test(context: Context, event: Event, completion: ActionHandler)```function. Following lines shows how to do this.


```Kotlin
    val item = Item(
        id = "testId",
        name = "testName",
        category = "testCategory",
        price = 93.8,
        currency = "TRY"
    )
    val event = Event(
        category = "cat",
        action = "act",
        label = "lab",
        url = "ex-screen",
        item = item)
        .withCustomAttribute("color", "blue")
    Quin.test(context, event = event) { action ->
            // Use action variable here
    }
```

***

## Troubleshooting

For any problems or further questions you can contact us with from hello@quinengine.com address.  

