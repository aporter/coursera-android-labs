package course.labs.intentslab.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import course.labs.intentslab.ActivityLoaderActivity;

public class ImplicitTest extends
		ActivityInstrumentationTestCase2<ActivityLoaderActivity> {
	private Solo solo;

	public ImplicitTest() {
		super(ActivityLoaderActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(),getActivity());
		
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// Executes the ImplicitTest
	public void testRun() {

		int delay = 2000;
		
		// =================== Section One =====================

		// Wait for activity: 'course.labs.intentslab.ActivityLoaderActivity'
		assertTrue(
				"ImplicitTest:" +
				"Section One:" +
				"ActivityLoaderActivity did not load correctly",
				solo.waitForActivity(course.labs.intentslab.ActivityLoaderActivity.class,delay));


		solo.sleep(delay);
		
		// Click on Implicit Activation Button
		solo.clickOnView(solo
				.getView(course.labs.intentslab.R.id.implicit_activation_button));


		// Wait for activity: 'com.android.internal.app.ChooserActivity'
		assertTrue(
				"ImplicitTest:" +
				"Section One:" +
				"ChooserActivity was not launched correctly",
				solo.waitForActivity("ChooserActivity",delay));
		
		solo.sleep(delay);
		
		assertTrue(
				"ImplicitTest:" +
				"Section One:" +
				"MyBrowser was not found",
				solo.searchText("MyBrowser", true));
		
		

	}
}
