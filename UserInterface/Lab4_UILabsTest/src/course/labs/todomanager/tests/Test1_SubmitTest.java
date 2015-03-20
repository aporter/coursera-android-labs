package course.labs.todomanager.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import course.labs.todomanager.ToDoManagerActivity;

public class Test1_SubmitTest extends ActivityInstrumentationTestCase2<ToDoManagerActivity> {
	
	private Solo solo;
	
	public Test1_SubmitTest() {
		super(ToDoManagerActivity.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(),getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// Execute the SubmitTest
	public void testRun() {
		
		int delay = 2000;
		
		// ============= Section One ===============
		// Wait for activity: 'course.labs.todomanager.ToDoManagerActivity'
		assertTrue("SubmitTest failed:" +
				"Section One:" +
				"ToDoManagerActivity did not load correctly.",
				solo.waitForActivity(
						course.labs.todomanager.ToDoManagerActivity.class, 2000));

		// Click on action bar item to delete all items
		solo.clickOnActionBarItem(0x1);
		
		solo.sleep(delay);
		
		// Click on Add New ToDo Item
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.footerView));
		
		// Wait for activity: 'course.labs.todomanager.AddToDoActivity'
		assertTrue("Submit Test failed:" +
				"Section One:" +
				"AddToDoActivity did not correctly load.",
				solo.waitForActivity(course.labs.todomanager.AddToDoActivity.class));

		// Hide the soft keyboard
		solo.hideSoftKeyboard();
		
		// Enter the text: 't4'
		solo.clearEditText((android.widget.EditText) solo
				.getView(course.labs.todomanager.R.id.title));
		
		solo.enterText((android.widget.EditText) solo
				.getView(course.labs.todomanager.R.id.title), "t4");

		// Hide the soft keyboard
		solo.hideSoftKeyboard();
		
		// Click on Done:
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.statusDone));
		
		// Click on Low
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.lowPriority));

		// Click on Submit
		solo.clickOnView(solo
				.getView(course.labs.todomanager.R.id.submitButton));
		
		// ================= Section Two ================
		// Wait for activity: 'course.labs.todomanager.ToDoManagerActivity'
		assertTrue("SubmitTest failed:" +
				"Section Two:" +
				"ToDoManagerActivity did not load correctly after pressing submit.",
				solo.waitForActivity(course.labs.todomanager.ToDoManagerActivity.class));
		
		assertTrue("SubmitTest failed:" +
				"Section Two:" +
				"Title was not correctly entered in the ToDoManager",
				solo.searchText("t4"));
		
		assertTrue("SubmitTest failed:" +
				"Section Two:" +
				"Priority was not correctly entered in the ToDoManager",
				solo.searchText("[lL][oO][wW]"));
		
		assertTrue("SubmitTest failed:" +
				"Section Two:" +
				"Did not correctly set completion status.",
				solo.isCheckBoxChecked(0));
		
	}

}
