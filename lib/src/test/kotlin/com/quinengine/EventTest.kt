package com.quinengine

import android.content.Context
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock


class EventTest {
    private lateinit var ctx: Context

    private val quin: Quin.Companion = Quin.Companion

    /*Mock SharedPreferences.Editor and SharedPreferences before the test*/
    @BeforeEach
    fun setup() {
        val ctx = mock<Context>()
        Quin.getInstance().setConfig("test", "https://demo.quinengine.com", true)
        Quin.getInstance().setUser(ctx, "test-google-client-id")
    }

    @Test
    fun sendTestEvent() {
        val handler: ActionHandler = { a -> assert(a != null) }
        Quin.getInstance().eCommerce().sendTestEvent(ctx, handler)
    }
}
