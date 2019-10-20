package com.maryang.testablearc

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.maryang.testablearc.ui.repo.GithubRepoActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GithubRepoActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(GithubRepoActivity::class.java)

    @Test
    fun onCreate() {
        activityRule.launchActivity(null)
    }
}
