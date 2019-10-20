package com.maryang.testablearc.util

class LoginHelper {
    var isLogin: Boolean = false

    fun login() {
        isLogin = true
    }

    fun logout() {
        isLogin = false
    }
}
