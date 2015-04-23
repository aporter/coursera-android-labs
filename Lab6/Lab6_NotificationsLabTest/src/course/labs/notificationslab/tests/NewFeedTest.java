package course.labs.notificationslab.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import course.labs.notificationslab.TestFrontEndActivity;

public class NewFeedTest extends
		ActivityInstrumentationTestCase2<TestFrontEndActivity> {
	private Solo solo;

	public NewFeedTest() {
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
		int shortDelay = 5000;
		int longDelay = 10000;

		// Wait for activity:
		// 'course.labs.notificationslab.TestFrontEndActivity'
		solo.waitForActivity(
				course.labs.notificationslab.TestFrontEndActivity.class,
				shortDelay);

		// Click on Make Tweets New
		solo.clickOnView(solo
				.getView(course.labs.notificationslab.R.id.rejuv_tweets_button));

		// Click on Start Main Activity
		solo.clickOnView(solo
				.getView(course.labs.notificationslab.R.id.start_main_button));

		// Wait for activity:
		// 'course.labs.threadslab.MainActivity'
		solo.waitForActivity(course.labs.notificationslab.MainActivity.class,
				shortDelay);
		
		solo.waitForView(android.R.id.list);
		
		final View listView = solo.getView(android.R.id.list);
		solo.waitForCondition(new Condition() {
			@Override
			public boolean isSatisfied() {
				return listView.isEnabled();
			}
		}, longDelay);

		// Click on taylorswift13
		solo.clickOnView(solo.getView(android.R.id.text1));

		// Assert that: 'feed_view' is shown
		assertTrue("feed_view not shown!", solo.waitForView(solo
				.getView(course.labs.notificationslab.R.id.feed_view)));

		// Assert that: 'Taylor Swift' is shown
		assertTrue("'Taylor Swift' is not shown!",
				solo.searchText("Taylor Swift"));

	}
}
