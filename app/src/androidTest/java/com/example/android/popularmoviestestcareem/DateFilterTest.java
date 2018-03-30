package com.example.android.popularmoviestestcareem;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class DateFilterTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickFilterButton_OpensNumberPicker() {

        // Locate and clicks on a movie
        onView(withId(R.id.action_filter)).perform(click());

        // Checks if the ChildActivity is displayed by asserting that the release date is displayed
        onView(withId(R.id.number_picker)).check(matches(isDisplayed()));

    }

    @Test
    public void clickMoviePoster_OpensDetails() {

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Locate and clicks on a movie
        onView(withId(R.id.rv_images)).perform(click());

        // Checks if the ChildActivity is displayed by asserting that the release date is displayed
        onView(withId(R.id.release_date)).check(matches(isDisplayed()));

    }

}