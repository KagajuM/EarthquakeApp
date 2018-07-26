package com.example.mkagaju.earthquakeapp_20;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;

import okhttp3.mockwebserver.MockWebServer;



@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static final String PAST_EARTHQUAKES_URL
            = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson";
    public MainActivity mainActivity;
    @Rule
    public ActivityTestRule <MainActivity> mainActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


//    @Test
//    public void testRecyclerView() throws IOException {
//
//        //1. Test Progress Bar
//        onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())));
//
//        //2. Test RecyclerView is Displayed
//        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
//
//        //3. Test Network Loss
//
//
////        RecyclerViewInternalMatcher matcher = new RecyclerViewInternalMatcher(R.id.recyclerView);
////        int midPosition = 7584;
////        Log.e("Mid Position", String.valueOf(midPosition));
////
////
////        //Click an item in the middle of the list
////        onView(withId(R.id.recyclerView))
////                .perform(RecyclerViewActions.actionOnItemAtPosition(midPosition,
////                        click()));
//
//    }
    @Test
    public void testService() throws IOException{
        MockWebServer server = new MockWebServer();



        server.start();
        URL inputURL = getMockURL(PAST_EARTHQUAKES_URL);
        URL mockURL = server.url(inputURL.getPath()).newBuilder()
                            .encodedQuery(inputURL.getQuery())
                            .build()
                            .url();



    }

    public URL getMockURL(String url){
        return new Networking().buildUrl(url);
    }


    private static Matcher<Adapter.ViewHolder> isInTheMiddle() {
        return new TypeSafeMatcher<Adapter.ViewHolder>() {
            @Override
            protected boolean matchesSafely(Adapter.ViewHolder customHolder) {
                return customHolder.getInTheMiddle();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");
            }
        };
    }


}
