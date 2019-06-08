package com.example.wishapp

import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import android.support.test.espresso.Espresso.onView as onView
import android.support.test.rule.ActivityTestRule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityInstumentalTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<NewWishActivity> = ActivityTestRule(NewWishActivity::class.java)

    @Test
    fun testTextOnButtonDisplay() {
        onView(withId(R.id.button_camera)).check(ViewAssertions.matches(withText("CAMERA")))
    }

    @Test
    fun testAddNewWish() {
        onView(withId(R.id.input_name)).perform(typeText("test text 2"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.input_description)).perform(typeText("description text"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.button_save)).perform(click())
    }

}

