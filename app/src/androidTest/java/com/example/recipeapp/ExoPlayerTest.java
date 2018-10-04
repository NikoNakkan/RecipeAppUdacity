package com.example.recipeapp;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.MissingFormatArgumentException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ExoPlayerTest
{
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;



    @Before
    public void registerIdlingResource() {
        mIdlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //here we make sure that the exo layout is inflated
    @Test
    public void TestingExo() {
    onView(withId(R.id.rv_recipies)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
    onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
    onView(withId(R.id.playerView)).check(matches(isDisplayed()));
}
//test ensures that all 4 recepies have been loaded and that the respective ingredient list exists

    @Test
    public void TestingExoxi() {
        onView(withId(R.id.rv_recipies)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.tv_step_to_ingredient)).perform(click());

    }


    @After
    public void unregisterIdlingResource ()
    {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}





