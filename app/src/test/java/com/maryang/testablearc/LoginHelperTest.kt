package com.maryang.testablearc

import com.maryang.testablearc.util.LoginHelper
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginHelperTest {

    private lateinit var loginHelper: LoginHelper

    @Before
    fun setUp() {
        loginHelper = LoginHelper()
    }

    @Test
    fun loginTest() {
        loginHelper.login()
        // isLogin 값이 True가 되었는가?
        assertTrue(loginHelper.isLogin)
    }
}
