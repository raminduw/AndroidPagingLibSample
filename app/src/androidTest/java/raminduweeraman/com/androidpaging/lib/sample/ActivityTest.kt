

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import raminduweeraman.com.androidpaging.lib.sample.R
import raminduweeraman.com.androidpaging.lib.sample.api.NetworkState
import raminduweeraman.com.androidpaging.lib.sample.api.Status
import raminduweeraman.com.androidpaging.lib.sample.ui.ManufactureSearchActivity


@RunWith(AndroidJUnit4ClassRunner::class)
class ActivityTest {

    @Rule
    @JvmField
    val activityRule = IntentsTestRule<ManufactureSearchActivity>(ManufactureSearchActivity::class.java, true, false)


    @Test
    fun test_show_loading_indicator() {
        activityRule.launchActivity(Intent())
        val networkState = NetworkState(Status.LOADING)
        activityRule.getActivity().runOnUiThread(Runnable { activityRule.getActivity().setInitialLoadingState(networkState)})
        onView(withId(R.id.loadingProgressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_hide_loading_indicator() {
        activityRule.launchActivity(Intent())
        val networkState = NetworkState(Status.SUCCESS)
        activityRule.getActivity().runOnUiThread(Runnable { activityRule.getActivity().setInitialLoadingState(networkState)})
        onView(withId(R.id.loadingProgressBar)).check(matches(not(isDisplayed())))
    }


    @Test
    fun test_no_message_indicator() {
        activityRule.launchActivity(Intent())
        val networkState = NetworkState(Status.FAILED)
        activityRule.getActivity().runOnUiThread(Runnable { activityRule.getActivity().setInitialLoadingState(networkState)})
        onView(withId(R.id.errorMessageTextView)).check(matches(not(isDisplayed())))
    }

    @Test
    fun test_show_message_indicator() {
        activityRule.launchActivity(Intent())
        val networkState = NetworkState(Status.FAILED,"Network Error")
        activityRule.getActivity().runOnUiThread(Runnable { activityRule.getActivity().setInitialLoadingState(networkState)})
        onView(withId(R.id.errorMessageTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.errorMessageTextView)).check(matches(withText("Network Error")))
    }

}