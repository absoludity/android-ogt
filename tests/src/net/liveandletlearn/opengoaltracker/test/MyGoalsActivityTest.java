package net.liveandletlearn.opengoaltracker.test;

import net.liveandletlearn.opengoaltracker.GoalListFragment;
import net.liveandletlearn.opengoaltracker.MyGoalsActivity;
import net.liveandletlearn.opengoaltracker.OGTDatabase;
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
	private OGTDatabase mOgtDb;
	
	@SuppressWarnings("deprecation")
	public MyGoalsActivityTest() {
		super("net.liveandletlearn.opengoaltracker", MyGoalsActivity.class);
		// http://stackoverflow.com/questions/2547508/android-unit-testing-using-a-different-database-file
		OGTDatabase.DatabaseOpenHelper.mTesting = true;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mEditText = (EditText) mActivity.findViewById(R.id.new_goal);
		mAddButton = (Button) mActivity.findViewById(R.id.add_goal_button);
		mOgtDb = new OGTDatabase(mEditText.getContext());
		mOgtDb.truncateTestDatabase();
		this.focusAddGoalText();
	}
	
	public void testPreConditions() {
		assertTrue(mEditText.isFocused());

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
	
	public void focusAddGoalText() {
		mActivity.runOnUiThread(
			new Runnable() {
				public void run() {
					mEditText.requestFocus();
				}
			});
	}
	
	public void testAddEmptyGoalDoesNothing() {
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
		
		assertEquals(0, mOgtDb.countGoals());
	}
	
	private int getListFragmentLength() {
		GoalListFragment listFragment = (GoalListFragment) mActivity.getFragmentManager()
				.findFragmentById(R.id.list);
		return listFragment.getListAdapter().getCount();
	}
	
	public void testAddGoalClearsInput() {
		this.sendKeys("A B C D E");
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
		
		// The entry is now in the database, displayed in the list view and the new goal
		// entry box is cleared.
		assertEquals(1, mOgtDb.countGoals());
		assertEquals(1, getListFragmentLength());
		assertEquals("", mEditText.getText().toString());
	}
}