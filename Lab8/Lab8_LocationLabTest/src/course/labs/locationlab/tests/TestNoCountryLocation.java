package course.labs.locationlab.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import course.labs.locationlab.PlaceViewActivity;

public class TestNoCountryLocation extends
		ActivityInstrumentationTestCase2<PlaceViewActivity> {
	private Solo solo;

	public TestNoCountryLocation() {
		super(PlaceViewActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		PlaceViewActivity.sHasNetwork = false;
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int delay = 2000;
		int longDelay = 5000;

		// Wait for activity: 'course.labs.locationlab.PlaceViewActivity'
		solo.waitForActivity(course.labs.locationlab.PlaceViewActivity.class,
				delay);

		// Click on action bar item
		solo.clickOnActionBarItem(course.labs.locationlab.R.id.place_no_country);

		solo.sleep(delay);

		// Click on Get New Place
		assertTrue("Footer view isn't visible",
				solo.waitForView(course.labs.locationlab.R.id.footer, 0, delay));

		solo.clickOnView(solo.getView(course.labs.locationlab.R.id.footer));

		String noCountryString = solo
				.getString(course.labs.locationlab.R.string.no_country_string);

		// Assert that no country toast is shown
		assertTrue(noCountryString + " is not shown!",
				solo.waitForText(noCountryString, 1, longDelay));

	}
}
