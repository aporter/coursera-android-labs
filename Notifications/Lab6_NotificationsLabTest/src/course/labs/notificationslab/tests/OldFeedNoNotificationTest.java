package course.labs.notificationslab.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import course.labs.notificationslab.TestFrontEndActivity;

public class OldFeedNoNotificationTest extends
		ActivityInstrumentationTestCase2<TestFrontEndActivity> {
	private Solo solo;

	public OldFeedNoNotificationTest() {
		super(TestFrontEndActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int shortDelay = 2000;
		int longDelay = 10000;

		// Wait for activity:
		// 'course.labs.notificationslab.TestFrontEndActivity'
		solo.waitForActivity(
				course.labs.notificationslab.TestFrontEndActivity.class, shortDelay);

		// Click on Make Tweets Old
		solo.clickOnView(solo
				.getView(course.labs.notificationslab.R.id.age_tweets_button));

		// Click on Start Main Activty
		solo.clickOnView(solo
				.getView(course.labs.notificationslab.R.id.start_main_button));

		// Wait for activity: 'course.labs.notificationslab.MainActivity'
		assertTrue("course.labs.notificationslab.MainActivity is not found!",
				solo.waitForActivity(
						course.labs.notificationslab.MainActivity.class, shortDelay));

		// Assert that: Toast message is shown
		String msg = getActivity().getString(course.labs.notificationslab.R.string.download_in_progress_string);
		assertTrue("'" + msg + "	' is not shown!",
				solo.searchText(msg));

		solo.waitForView(android.R.id.list);
		
		final View listView = solo.getView(android.R.id.list);
		solo.waitForCondition(new Condition() {
			@Override
			public boolean isSatisfied() {
				return listView.isEnabled();
			}
		}, longDelay);

		String failMsg = getActivity().getString(course.labs.notificationslab.R.string.download_failed_string);
		String successMsg = getActivity().getString(course.labs.notificationslab.R.string.download_succes_string);
		assertTrue("Toast message did not appear", solo.searchText("(" + failMsg + "|" + successMsg + ")"));

		// Click on taylorswift13
		solo.clickOnView(solo.getView(android.R.id.text1));

		// Assert that: 'feed_view' is shown
		assertTrue("feed_view' is not shown!", solo.waitForView(solo
				.getView(course.labs.notificationslab.R.id.feed_view)));

	}
}
