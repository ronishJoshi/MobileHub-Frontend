package com.example.mobilehub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.mobilehub.R
import com.example.mobilehub.ui.RegisterActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class SignUpTest {
    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        RegisterActivity::class.java
    )

    @Test
    @Throws(Exception::class)
    fun checkSignup() {
        Espresso.onView(ViewMatchers.withId(R.id.etFname))
            .perform(ViewActions.typeText("sirish"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.etLname))
            .perform(ViewActions.typeText("khadka"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.etUsername))
            .perform(ViewActions.typeText("Anamnagar"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("sirish"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.etConfirmPassword))
            .perform(ViewActions.typeText("9805328518"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.btnAddProduct))
            .perform(ViewActions.click())
    }
}