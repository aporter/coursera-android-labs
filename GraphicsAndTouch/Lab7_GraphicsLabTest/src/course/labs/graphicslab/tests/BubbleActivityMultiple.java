package course.labs.graphicslab.tests;

import course.labs.graphicslab.BubbleActivity;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;
import android.view.WindowManager;

public class BubbleActivityMultiple extends
		ActivityInstrumentationTestCase2<BubbleActivity> {
	private Solo solo;

	public BubbleActivityMultiple() {
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

		int delay = 2000;

		// Wait for activity: 'course.labs.TouchLab.BubbleActivity'
		solo.waitForActivity(course.labs.graphicslab.BubbleActivity.class, delay);

		// Set Still Mode
		solo.clickOnActionBarItem(course.labs.graphicslab.R.id.menu_still_mode);

		solo.sleep(delay);

		// Click to create a bubble
		solo.clickOnScreen(100, 100);

		solo.sleep(delay);
		
		// Assert that a bubble was displayed 
		assertEquals("Bubble hasn't appeared", 1, solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class).size());

		// Click to create second bubble
		solo.clickOnScreen(500, 500);

		solo.sleep(delay);

		// Assert that a bubble was displayed 
		assertEquals("Second bubble hasn't appeared", 2, solo.getCurrentViews(course.labs.graphicslab.BubbleActivity.BubbleView.class).size());

		solo.sleep(delay);

		// Give misbehaving bubbles a chance to move off screen
		// Assert that there are two bubbles on the screen
		assertEquals(
				"There should be two bubbles on the screen",
				2,
				solo.getCurrentViews(
						course.labs.graphicslab.BubbleActivity.BubbleView.class)
						.size());
	}
}
