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
		// http://stackoverflow.com/questions/2547508/android-unit-testing-using-a-different-database-file
		OGTContract.OGTDbHelper.mTesting = true;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		mActivity = getActivity();
		mEditText = (EditText) mActivity.findViewById(R.id.new_goal);
		mAddButton = (Button) mActivity.findViewById(R.id.add_goal_button);
		OGTDbHelper dbHelper = new OGTDbHelper(mEditText.getContext());
		dbHelper.truncateTestDatabase();
		mDb = dbHelper.getReadableDatabase();
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
	
	public void testAddEmptyGoalDoesNothing() {
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
		
		assertEquals(0, DatabaseUtils.queryNumEntries(mDb, OGTContract.UserGoals.TABLE_NAME));
	}
	
	public void testAddGoalClearsInput() {
		this.sendKeys("A B C D E");
		this.focusAddGoalButton();
		
		this.sendKeys("DPAD_CENTER");
		
		assertEquals(1, DatabaseUtils.queryNumEntries(mDb, OGTContract.UserGoals.TABLE_NAME));		
		assertEquals("", mEditText.getText().toString());				
	}
}