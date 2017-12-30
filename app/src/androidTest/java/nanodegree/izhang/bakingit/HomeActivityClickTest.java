package nanodegree.izhang.bakingit;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityClickTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void homeActivityClickTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.realm_recycler_view),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_recipe_label), withText("Recipe"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cv_serving_ingredients),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Recipe")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tv_step_label), withText("Steps"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.cv_steps),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Steps")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tv_recipe_name), withText("Recipe Introduction"),
                        childAtPosition(
                                allOf(withId(R.id.recipe_card),
                                        childAtPosition(
                                                withId(R.id.rv_step_list),
                                                0)),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("Recipe Introduction")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
