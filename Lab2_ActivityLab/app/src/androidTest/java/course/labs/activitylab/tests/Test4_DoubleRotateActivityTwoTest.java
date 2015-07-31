package course.labs.activitylab.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import course.labs.activitylab.ActivityOne;

public class Test4_DoubleRotateActivityTwoTest extends ActivityInstrumentationTestCase2<ActivityOne> {

	private Solo solo;
	private int timeout = 20000;
	private int sleep = 1000;

	public Test4_DoubleRotateActivityTwoTest() {
		super(ActivityOne.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// Exectues the DoubleRotateActivityTwoTest
	public void testRun() {
		// ==================== Section One =====================
		// Wait for activity: 'course.labs.activitylab.ActivityOne'
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section One:" +
				   "ActivityOne did not load correctly",
				   solo.waitForActivity(course.labs.activitylab.ActivityOne.class, timeout));

				
		// ==================== Section Two =====================
		// Click on Start Activity Two
		solo.waitForView(course.labs.activitylab.R.id.bLaunchActivityTwo);
		solo.clickOnView(solo
				.getView(course.labs.activitylab.R.id.bLaunchActivityTwo));
		
		// Wait for activity: 'course.labs.activitylab.ActivityTwo'
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Two:" +
				   "ActivityTwo did not load correctly",
				   solo.waitForActivity(course.labs.activitylab.ActivityTwo.class, timeout));
		
		
		solo.waitForView(course.labs.activitylab.R.id.bClose);

		// ==================== Section Three =====================
		// Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);

		// Wait for activity: 'course.labs.activitylab.ActivityTwo'
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Three:" +
				   "ActivityTwo did not correctly load after first LANDSCAPE rotation.",
				solo.waitForActivity(course.labs.activitylab.ActivityTwo.class, timeout));
		
		solo.waitForView(course.labs.activitylab.R.id.bClose);
		
		// ==================== Section Four =====================
		// Rotate the screen
		solo.setActivityOrientation(Solo.PORTRAIT);

		// Wait for activity: 'course.labs.activitylab.ActivityTwo'
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Four:" +
				   "ActivityTwo did not correctly load after second PORTRAIT rotation.",
				solo.waitForActivity(course.labs.activitylab.ActivityTwo.class, timeout));
		
		solo.waitForView(course.labs.activitylab.R.id.bClose);

		// Check for proper counts
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Four:" +
				   "onCreate() count was off for ActivityTwo after second PORTRAIT rotation.",
				   solo.waitForText("onCreate\\(\\) calls: 3"));
		
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Four:" +
				   "onStart() count was off for ActivityTwo after second PORTRAIT rotation.",
				   solo.waitForText("onStart\\(\\) calls: 3"));
		
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Four:" +
				   "onResume() count was off for ActivityTwo after second PORTRAIT rotation.",
				   solo.waitForText("onResume\\(\\) calls: 3"));
		
		assertTrue("DoubleRotateActivityTwoTest failed:" +
				   "Section Four:" +
				   "onRestart() count was off for ActivityTwo after second PORTRAIT rotation.",
				   solo.waitForText("onRestart\\(\\) calls: 0"));
	}

}
