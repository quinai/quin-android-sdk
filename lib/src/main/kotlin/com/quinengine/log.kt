package com.quinengine

interface LoggerListener {
    fun onLog(message: String)
}

class Logger {
    companion object {
        var configuration = LogConfiguration()
        var sharedInstance = Logger()

        private var loggerListener: LoggerListener? = null

        fun setConfig(debug: Boolean) {
            configuration.debug = debug
        }

        fun setLoggerListener(listener: LoggerListener){
            loggerListener = listener
        }
    }

    fun log(msg: String) {
        if (configuration.debug == true) {
            println(msg)
            loggerListener?.onLog(msg)
        }
    }
}