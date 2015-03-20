package course.labs.graphicslab.tests;

import course.labs.graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

public class BubbleActivityFloatOffScreen extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityFloatOffScreen() {
		super(BubbleActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		getInstrumentation().runOnMainSync(new Runnable() {
			@Override
			public void run() {
				getActivity().getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			}
		});
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testRun() {

		int shortDelay = 250, delay = 2000;

		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(course.labs.graphicslab.BubbleActivity.class,
				delay);

		// Click on action bar item
		solo.clickOnActionBarItem(course.labs.graphicslab.R.id.menu_single_speed);

		solo.sleep(delay);

		// Click to create a bubble
		solo.clickOnScreen(250.0f, 250.0f);

		// Check whether bubble appears
		boolean bubbleAppeared = solo.getCurrentViews(
				course.labs.graphicslab.BubbleActivity.BubbleView.class).size() > 0;
		for (int i = 0; i < 8 && !bubbleAppeared; i++) {
			solo.sleep(shortDelay);
			bubbleAppeared = solo.getCurrentViews(
					course.labs.graphicslab.BubbleActivity.BubbleView.class)
					.size() > 0;
		}

		// Assert that a bubble was displayed
		assertTrue("Bubble hasn't appeared", bubbleAppeared);

		solo.sleep(delay);

		// Assert that the bubble has left the screen
		assertEquals(
				"Bubble hasn't left the screen",
				0,
				solo.getCurrentViews(
						course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());

	}
}
