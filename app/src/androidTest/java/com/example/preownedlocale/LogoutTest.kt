package com.example.mobilehub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.mobilehub.R
import com.example.mobilehub.ui.DashboardActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LogoutTest {
    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        DashboardActivity::class.java
    )

    //    @Rule
    //    public ActivityScenarioRule<DashboardActivity>
    //            activity_logoutActivityTestRule = new ActivityScenarioRule<>(DashboardActivity.class);
    @Test
    fun LogoutValidationTest() {
        Espresso.onView(ViewMatchers.withId(R.id.btnLogout)).perform(ViewActions.click())
    }
}