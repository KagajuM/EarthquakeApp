package com.example.mkagaju.earthquakeapp_20;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {
    //Recycler View
    public static final int LOCATOR_RECYCLER_VIEW = R.id.recyclerView;
    public static final int LOCATOR_LOCATION_TV = R.id.earthquake_location_tv;
    public static final int LOCATOR_TIME_TV = R.id.earthquake_time_tv;
    public static final int LOCATOR_MAGNITUDE_TV = R.id.earthquake_magnitude_tv;
    public RecyclerViewInternalMatcher matcher
            = new RecyclerViewInternalMatcher(LOCATOR_RECYCLER_VIEW);


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = activityTestRule.getActivity().getIdlingResource();
    }
    @Test
    public void testRecyclerView(){
        //1. Recycler View is Visible
        onView(withId(LOCATOR_RECYCLER_VIEW)).check(matches(isDisplayed()));
        for (int i = 0 ; i <= 20; i++)
            testFirstItems(i);
        //2. Test first 20 items of recycler view

    }

    public void testFirstItems(int position){
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

    }
}
