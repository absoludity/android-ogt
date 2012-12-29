package net.liveandletlearn.opengoaltracker.test;

import net.liveandletlearn.opengoaltracker.MyGoalsActivity;
import net.liveandletlearn.opengoaltracker.OGTContract;
import net.liveandletlearn.opengoaltracker.OGTContract.OGTDbHelper;
import net.liveandletlearn.opengoaltracker.R;
import android.app.Activity;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

public class MyGoalsActivityTest extends
		ActivityInstrumentationTestCase2<MyGoalsActivity> {

	private Activity mActivity;
	private EditText mEditText;
	private Button mAddButton;
	private SQLiteDatabase mDb;
	
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
		OGTDbHelper dbHelper = new OGTDbHelper(mEditText.getContext());
		mDb = dbHelper.getReadableDatabase();
	}
	
	public void testPreConditions() {
		assertTrue(mEditText.isFocused());
	}

	public void testEditTextHasFocus() {
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
		// Can tests use a separate test database so I can truncate tables in setUp?
		// http://stackoverflow.com/questions/2547508/android-unit-testing-using-a-different-database-file
		long numBefore = DatabaseUtils.queryNumEntries(mDb, OGTContract.UserGoals.TABLE_NAME);
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
		
		long numAfter = DatabaseUtils.queryNumEntries(mDb, OGTContract.UserGoals.TABLE_NAME);
		assertEquals(numBefore, numAfter);
	}
}