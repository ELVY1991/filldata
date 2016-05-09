package com.example.administrator.intent;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Administrator on 2016/5/4.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AutoCompleteTest {
    MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setActivity(){
         mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void autoCom(){
        // Type "So" to trigger two suggestions.
        onView(withId(R.id.autoCompleteTextView))
                .perform(typeText("So"), closeSoftKeyboard());


        onView(withText("Southern Ocean"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        onView(withText("South China Sea"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void autoCompleteTextView_onDataClickAndCheck() {
        // NB: The autocompletion box is implemented with a ListView, so the preferred way
        // to interact with it is onData(). We can use inRoot here too!
        onView(withId(R.id.autoCompleteTextView))
                .perform(typeText("S"), closeSoftKeyboard());

        // This is useful because some of the completions may not be part of the View Hierarchy
        // unless you scroll around the list.
        onData(allOf(instanceOf(String.class), is("Baltic Sea")))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        // The text should be filled in.
        onView(withId(R.id.autoCompleteTextView))
                .check(matches(withText("Baltic Sea")));
    }
}

