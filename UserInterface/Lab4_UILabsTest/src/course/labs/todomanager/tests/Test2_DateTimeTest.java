package course.labs.todomanager.tests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import course.labs.todomanager.ToDoManagerActivity;

public class Test2_DateTimeTest extends ActivityInstrumentationTestCase2<ToDoManagerActivity> {
	
	private Solo solo;
	
	public Test2_DateTimeTest() {
		super(ToDoManagerActivity.class);
	}

	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation());
		getActivity();
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// Executes DateTimeTest
	public void testRun() {
		
		int delay = 2000;
		
		// ============== Section One ================

		// Wait for activity: 'course.labs.todomanager.ToDoManagerActivity'
		assertTrue("DateTimeTest failed:" +
				"Section One:" +
				"ToDoManagerActivity did not correctly load.",
				solo.waitForActivity(
						course.labs.todomanager.ToDoManagerActivity.class, 2000));

		// Click on action bar item to delete all items
		solo.clickOnActionBarItem(0x1);

		solo.sleep(delay);
		
		// Click on Add New ToDo Item
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.footerView));

		// Wait for activity: 'course.labs.todomanager.AddToDoActivity'
		assertTrue("DateTimeTest failed:" +
				"Section One:" +
				"AddToDoActivity did not correctly load.",
				solo.waitForActivity(course.labs.todomanager.AddToDoActivity.class));

		// Hide the soft keyboard
		solo.hideSoftKeyboard();

		// Enter the text: 't1'
		solo.clearEditText((android.widget.EditText) solo
				.getView(course.labs.todomanager.R.id.title));
		
		solo.enterText((android.widget.EditText) solo
				.getView(course.labs.todomanager.R.id.title), "t1");

		// Hide the soft keyboard
		solo.hideSoftKeyboard();
		
		// Click on Done:
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.statusDone));
		
		// Click on Low
		solo.clickOnView(solo.getView(course.labs.todomanager.R.id.lowPriority));
		
		// Click on Choose Date
		solo.clickOnView(solo
				.getView(course.labs.todomanager.R.id.date_picker_button));

		// Wait for dialog
		solo.waitForDialogToOpen(10000);
		
		// Enter the text: 'Feb'
		solo.clearEditText((android.widget.EditText) solo
				.getView("numberpicker_input"));
		
		solo.enterText(
				(android.widget.EditText) solo.getView("numberpicker_input"),
				"Feb");
		
		// Enter the text: '28'
		solo.clearEditText((android.widget.EditText) solo.getView(
				"numberpicker_input", 1));
		
		solo.enterText(
				(android.widget.EditText) solo.getView("numberpicker_input", 1),
				"28");
		
		// Enter the text: '2014'
		solo.clearEditText((android.widget.EditText) solo.getView(
				"numberpicker_input", 2));
		
		solo.enterText(
				(android.widget.EditText) solo.getView("numberpicker_input", 2),
				"2014");

		// Really set the date
		solo.setDatePicker(0, 2014, 1, 28);

		// Click on Done
		solo.clickOnView(solo.getView(android.R.id.button1));

		solo.sleep(delay);
		
		// Click on Choose Time
		solo.clickOnView(solo
				.getView(course.labs.todomanager.R.id.time_picker_button));
		
		// Wait for dialog
		solo.waitForDialogToOpen(10000);
		
		// Enter the text: '9'
		solo.clearEditText((android.widget.EditText) solo
				.getView("numberpicker_input"));
		
		solo.enterText(
				(android.widget.EditText) solo.getView("numberpicker_input"),
				"9");
		
		// Enter the text: '19'
		solo.clearEditText((android.widget.EditText) solo.getView(
				"numberpicker_input", 1));
		
		solo.enterText(
				(android.widget.EditText) solo.getView("numberpicker_input", 1),
				"19");

		// Really set the time
		solo.setTimePicker(0, 9, 19);

		// Click on Done
		solo.clickOnView(solo.getView(android.R.id.button1));

		solo.sleep(delay);
		
		// Click on Submit
		solo.clickOnView(solo
				.getView(course.labs.todomanager.R.id.submitButton));

		// Wait for activity: 'course.labs.todomanager.ToDoManagerActivity'
		assertTrue("DateTimeTest failed:" +
				"Section One:" +
				"ToDoManagerActivity did not load correctly",
				solo.waitForActivity(
						course.labs.todomanager.ToDoManagerActivity.class, delay));
				
		// ============== Section Two =============

		// Makes sure the title was changed correctly
		assertTrue("DateTimeTest failed:" +
				"Section Two:" +
				"Did not modify title correctly",solo.searchText("t1"));
		
		// Checks to see if the status was changed correctly
		assertTrue("DateTimeTest failed:" +
				"Section Two:" +
				"Did not change status correctly",solo.isCheckBoxChecked(0));
		
		// Checks to make sure the priority was correctly set
		assertTrue("DateTimeTest failed:" +
				"Section Two:" +
				"Did not correctly set priority",solo.searchText("[lL][oO][wW]"));
		
		// Checks to make sure the Date was correctly set
		assertTrue("DateTimeTest failed:" +
				"Section Two:" +
				"Did not correctly set the date",solo.searchText("2014-02-28"));
		
		// Checks to make sure the Time was correctly set
		assertTrue("DateTimeTest failed:" +
				"Section Two:" +
				"Did not correctly set the time",solo.searchText("09:19:00"));
		
	}
	
}