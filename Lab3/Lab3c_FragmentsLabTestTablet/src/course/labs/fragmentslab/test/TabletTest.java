package course.labs.fragmentslab.test;

import course.labs.fragmentslab.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;

public class TabletTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;

	public TabletTest() {
		super(MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());

	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int delay = 2000;

		// Wait for activity: 'course.labs.fragmentslab.MainActivity'
		solo.waitForActivity(course.labs.fragmentslab.MainActivity.class, delay);

		// Wait for view: 'android.R.id.text1'
		assertTrue("text1 not found", solo.waitForView(android.R.id.text1));

		// Assert that: feed_view is shown
		assertTrue("'feed_view' was not found!", solo.waitForView(solo
				.getView(course.labs.fragmentslab.R.id.feed_view)));

		// Click on ladygaga
		solo.clickOnView(solo.getView(android.R.id.text1));

		// Assert that: feed_view is shown
		assertTrue("'Select a feed to view!' is not shown!",
				solo.waitForView(solo
						.getView(course.labs.fragmentslab.R.id.feed_view)));

		// Assert that: 'the audience cheering!' is shown
		assertTrue("'the audience cheering!' is not shown!",
				solo.searchText("the audience cheering!"));

		// Click on msrebeccablack
		solo.clickOnView(solo.getView(android.R.id.text1, 1));

		// Assert that: feed_view is shown
		assertTrue("'Select a feed to view!' is not shown!",
				solo.waitForView(solo
						.getView(course.labs.fragmentslab.R.id.feed_view)));

		// Assert that: 'save me from school' is shown
		assertTrue("'save me from school' is not shown!",
				solo.searchText("save me from school"));

		// Click on taylorswift13
		solo.clickOnView(solo.getView(android.R.id.text1, 2));

		// Assert that: feed_view is shown
		assertTrue("'Select a feed to view!' is not shown!",
				solo.waitForView(solo
						.getView(course.labs.fragmentslab.R.id.feed_view)));

		// Assert that: 'I love you guys so much' is shown
		assertTrue("'I love you guys so much' is not shown!",
				solo.searchText("I love you guys so much"));

	}
}