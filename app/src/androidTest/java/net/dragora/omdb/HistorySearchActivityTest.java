package net.dragora.omdb;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.bluelinelabs.logansquare.LoganSquare;

import net.dragora.omdb.models.ResponseSearch;
import net.dragora.omdb.ui.history.HistorySearchActivity;
import net.dragora.omdb.ui.history.HistorySearchActivity_;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;

/**
 * Created by nietzsche on 19/02/16.
 */
@RunWith(AndroidJUnit4.class)
public class HistorySearchActivityTest {
    @Rule
    public ActivityTestRule<HistorySearchActivity_> historyActivity = new ActivityTestRule<HistorySearchActivity_>(HistorySearchActivity_.class);

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void testNetworkError() {
        MockWebServer mockWebServer = historyActivity.getActivity().networkApi.constructForTest();
        mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody("Not found"));

        onView(withId(R.id.action_search))
                .perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("giant"))
                .perform(pressImeActionButton());
        onView(withText("Not found"))
                .check(matches(isDisplayed()));


    }

    @Test
    public void testServerError() {
        MockWebServer mockWebServer = historyActivity.getActivity().networkApi.constructForTest();
        ResponseSearch search = new ResponseSearch();
        search.setResponse("false");
        search.setError("timeout error");
        String json = "";
        try {
            json = LoganSquare.serialize(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(json));

        onView(withId(R.id.action_search))
                .perform(click());
        onView(isAssignableFrom(EditText.class))
                .perform(typeText("giant"))
                .perform(pressImeActionButton());
        onView(withText("timeout error"))
                .check(matches(isDisplayed()));


    }
}
