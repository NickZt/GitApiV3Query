package ua.zt.mezon.gitapiv3query;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    private String mStringToBetyped= "Espresso";

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }
    @Test
    public void testButton() {

        Espresso.onView(withId(R.id.fab)).check(matches(isClickable()));
        Espresso.onView(withId(R.id.search)).check(matches(isClickable()));


   // changeText_sameActivity
        // Type text and then press the button.
        onView(withId(R.id.search))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.search))
                .perform(pressImeActionButton());

        // Check that the text was changed.
        onView(withId(R.id.search))
                .check(matches(withText(mStringToBetyped)));

    //clickItem
        onView(withId(R.id.fab))
                .perform(click());
              //  .check(matches(isDisplayed()));;

        onView(withId(R.id.main_recycle_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(27, click()));


    }
}


//
//
///**
// * Instrumentation test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("ua.zt.mezon.gitapiv3query", appContext.getPackageName());
//    }
//}
