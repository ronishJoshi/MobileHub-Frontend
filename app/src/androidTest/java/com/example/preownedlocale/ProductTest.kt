package com.example.mobilehub

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.mobilehub.R
import com.example.mobilehub.ui.DisplayProductActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@LargeTest
@RunWith(JUnit4::class)
class ProductTest {
    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        DisplayProductActivity::class.java
    )

    //    @Rule
    //    public ActivityScenarioRule<DisplayMobileActivity>
    //            activity_productdetailActivityTestRule = new ActivityScenarioRule<>(DisplayMobileActivity.class);
    @Test
    fun ProductClickTest() {
        Espresso.onView(ViewMatchers.withId(R.id.imgDelete)).perform(ViewActions.click())
    }
}