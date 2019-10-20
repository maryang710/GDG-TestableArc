package com.maryang.testablearc

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferenceTest {

    @Test
    fun preferenceTest() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val pref = context.getSharedPreferences("preference", Context.MODE_PRIVATE)
        val userName = pref.getString("pref_user_name", "userName")
        assertEquals("userName", userName)
    }
}
