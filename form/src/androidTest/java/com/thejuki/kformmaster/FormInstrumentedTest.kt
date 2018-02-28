package com.thejuki.kformmaster

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.PickerActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatSeekBar
import android.support.v7.widget.SwitchCompat
import android.text.InputType
import android.widget.DatePicker
import android.widget.TimePicker
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Form Instrumented Test
 *
 * The Great Form Instrumented Test
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@RunWith(AndroidJUnit4::class)
class FormInstrumentedTest {

    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule<FormActivityTest>(FormActivityTest::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test
        val appContext = InstrumentationRegistry.getTargetContext()
        Assert.assertEquals("com.thejuki.kformmaster.test", appContext.packageName)
    }

    @Test
    fun header_isDisplayed() {
        // Check if Header 1 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(0))
        onView(withText("Header 1"))
                .check(matches(isDisplayed()))

        // Check if Header 2 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(4))
        onView(withText("Header 2"))
                .check(matches(isDisplayed()))

        // Check if Header 3 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(8))
        onView(withText("Header 3"))
                .check(matches(isDisplayed()))

        // Check if Header 4 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(12))
        onView(withText("Header 4"))
                .check(matches(isDisplayed()))

        // Check if Header 5 is displayed on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(18))
        onView(withText("Header 5"))
                .check(matches(isDisplayed()))
    }

    @Test
    fun editTextElement_shouldHaveInputType() {
        // Check Email type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(1))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Password type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(2))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)))
                .check(matches(isDisplayed()))

        // Check Phone type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(3))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_PHONE or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))

        // Check Single Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(5))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS),
                hasTextViewSingleLine()))
                .check(matches(isDisplayed()))

        // Check Multi Line type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(6))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_FLAG_MULTI_LINE)))
                .check(matches(isDisplayed()))

        // Check Number type
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(7))
        onView(allOf(`is`(instanceOf(AppCompatEditText::class.java)),
                hasTextViewInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun text_shouldNotBeDisplayed_whenVisibleIsFalse() {
        // Text element's visible property is set to false and should not show up on the form
        onView(withId(R.id.recyclerView)).perform(scrollToPosition<ViewHolder>(23))
        onView(withText("Hidden"))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun picker_openDialog_whenClicked() {
        // Open SingleItem Dialog and select "Orange"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(13, click()))
        onView(withText("SingleItem Dialog"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Orange")))
                .perform(click())

        // Open MultiItems Dialog, select "Orange", click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(14, click()))
        onView(withText("MultiItems Dialog"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Orange")))
                .perform(click())
        onView(withId(android.R.id.button1)).perform(click())

        // Open Date Dialog, enter 2/25/2018, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(9, click()))
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setDate(2018, 2, 25))
        onView(withId(android.R.id.button1)).perform(click())

        // Open Time Dialog, enter 12:30 AM, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(10, click()))
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setTime(12, 30))
        onView(withId(android.R.id.button1)).perform(click())

        //** DateTime Dialog */

        // Open Date Dialog, enter 2/25/2018, click "OK"
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(11, click()))
        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setDate(2018, 2, 25))
        onView(withId(android.R.id.button1)).perform(click())

        // Open Time Dialog, enter 12:30 AM, click "OK"
        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed())).perform(PickerActions.setTime(12, 30))
        onView(withId(android.R.id.button1)).perform(click())
    }

    @Test
    fun button_openDialog_whenClicked() {
        // Click button to verify the value observer Unit action works and displays an alert dialog
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(22, click()))
        onView(withText("Confirm?"))
                .inRoot(RootMatchers.isDialog())
                .check(matches(isDisplayed()))
        onView(withId(android.R.id.button1)).perform(click())
    }

    @Test
    fun slider_changes_whenProgressed() {
        // Change slider to check progress value
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(20, click()))
        onView(withClassName(Matchers.equalTo(AppCompatSeekBar::class.java.name)))
                .perform(setProgress(10))
                .check(matches(withProgress(10)))
    }

    @Test
    fun checkBox_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(21, click()))
        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(21, click()))
        onView(`is`(instanceOf(AppCompatCheckBox::class.java)))
                .check(matches(isNotChecked()))
    }

    @Test
    fun switch_becomeChecked_whenClicked() {
        // Check it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(19, click()))
        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isChecked()))

        // UnCheck it
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(19, click()))
        onView(`is`(instanceOf(SwitchCompat::class.java)))
                .check(matches(isNotChecked()))
    }
}
