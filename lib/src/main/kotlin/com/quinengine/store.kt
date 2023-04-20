package com.quinengine

import android.content.Context

class UserStore private constructor() {
    companion object {
        private const val keyUserId: String = "quin:userId"
        private const val keyToken: String = "quin:token"
        private const val keyGcid: String = "quin:gcid"
        fun load(context: Context): User? {
            val defaults = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val userId = defaults.getString(keyUserId, "")
            if (userId == "") {
                Logger.sharedInstance.log("quin store load: userId is empty")
                return null
            }
            val token = defaults.getString(keyToken, "")
            if (token == "") {
                Logger.sharedInstance.log("quin store load: token is empty")
                return null
            }
            val gcid = defaults.getString(keyGcid, "")
            return User(userId!!, token!!, gcid!!)
        }

        fun save(context: Context, user: User) {
            val defaults = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val editor = defaults.edit()
            editor.putString(keyUserId, user.id)
            editor.putString(keyToken, user.token)
            if (user.googleClientId.isNotEmpty()) {
                editor.putString(keyGcid, user.googleClientId)
            }
            editor.apply()
        }

        fun delete(context: Context) {
            val defaults = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
            val editor = defaults.edit()
            editor.clear()
            editor.apply()
        }
    }
}