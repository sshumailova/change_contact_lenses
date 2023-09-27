package com.android.timetochangecontactlenses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
        @Rule
        public ActivityTestRule<MainActivity> activityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        @Test
        public void checkContainerIsDisplayed() {
            onView(ViewMatchers.withId(R.id.editTextTextPersonName)).check(matches(isDisplayed()));
        }

}