package course.labs.asynctasklab.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import course.labs.asynctasklab.MainActivity;

public class FeedTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;

	public FeedTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		solo.setActivityOrientation(Solo.PORTRAIT);
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {
		int shortDelay = 5000;
		int longDelay = 10000;

		// Wait for activity:
		// 'course.labs.threadslab.TestFrontEndActivity'
		solo.waitForActivity(course.labs.asynctasklab.MainActivity.class,
				shortDelay);
		
		solo.waitForView(android.R.id.list);
		
		solo.setActivityOrientation(Solo.LANDSCAPE);
		
		solo.waitForActivity(course.labs.asynctasklab.MainActivity.class,
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
				.getView(course.labs.asynctasklab.R.id.feed_view)));

		// Assert that: 'Taylor Swift' is shown
		assertTrue("'Taylor Swift' is not shown!",
				solo.searchText("Taylor Swift"));

	}
}
