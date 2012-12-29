package net.liveandletlearn.opengoaltracker.test;

import net.liveandletlearn.opengoaltracker.MyGoalsActivity;
import net.liveandletlearn.opengoaltracker.R;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class MyGoalsActivityTest extends
		ActivityInstrumentationTestCase2<MyGoalsActivity> {

	private Activity mActivity;
	private EditText mEditText;
	private Button mAddButton;
	@SuppressWarnings("deprecation")
	public MyGoalsActivityTest() {
		super("net.liveandletlearn.opengoaltracker", MyGoalsActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mEditText = (EditText) mActivity.findViewById(R.id.new_goal);
		mAddButton = (Button) mActivity.findViewById(R.id.add_goal_button);
	}
	
	public void testPreConditions() {
		assertTrue(mEditText.isFocused());
	}

	public void testEditTextHasFocus() {
		/*mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
					mEditText.requestFocus();
				}
			});*/
		this.sendKeys("A B C D E");
			
		assertEquals("abcde", mEditText.getText().toString());				
	}
	
	public void focusAddGoalButton() {
		mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
					mAddButton.requestFocus();
				}
			});
	}
	
	public void testAddEmptyGoal() {
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
	}
}