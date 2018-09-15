package com.udacity.gradle.builditbigger;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.tamlove.jokeandroidlib.JokeActivity;
import com.tamlove.jokeandroidlib.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class VerifyJokeAsyncTask {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUpResources(){
        Intents.init();
    }

    @Test
    public void checkJokeIsFetched() {
        onView(withId(R.id.tellJoke_button)).check(matches(isDisplayed()));
        onView(withId(R.id.tellJoke_button)).perform(click());
        String errorText = mActivityRule.getActivity().getResources().getString(R.string.joke_error);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intended(allOf(hasComponent(JokeActivity.class.getName()), hasExtra(equalTo(Utils.JOKE_KEY), notNullValue())));
        onView(withId(R.id.joke_textview)).check(matches(isDisplayed()));
        onView(withId(R.id.joke_textview)).check(matches(not(withText(errorText))));
    }

    @After
    public void unregisterResources(){
        Intents.release();
    }

}
