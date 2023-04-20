package com.quinengine

class Logger {
    companion object {
        var configuration = LogConfiguration()
        var sharedInstance = Logger()

        fun setConfig(debug: Boolean) {
            configuration.debug = debug
        }
    }

    fun log(msg: String) {
        if (configuration.debug == true) {
            println(msg)
        }
    }
}