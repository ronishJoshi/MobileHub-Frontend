package com.example.mobilehub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.mobilehub.R
import com.example.mobilehub.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class LoginTest {
    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        LoginActivity::class.java
    )

    //    @Rule
    //    public ActivityScenarioRule<LoginActivity> activityTestRule =
    //            new ActivityScenarioRule<>(LoginActivity.class);
    @Test
    @Throws(Exception::class)
    fun checkLogin() {
        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText(" user"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("123456789"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .perform(ViewActions.click())
    }
}