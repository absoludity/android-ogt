package net.liveandletlearn.opengoaltracker;

import net.liveandletlearn.opengoaltracker.OGTContract.OGTDbHelper;
import net.liveandletlearn.opengoaltracker.OGTContract.UserGoals;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class GoalListFragment extends ListFragment {

	private SQLiteDatabase mDb;
	private String[] mUserGoalColumns;
	public SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Context context = this.getActivity();
		OGTDbHelper dbHelper = new OGTDbHelper(context);
		mDb = dbHelper.getReadableDatabase();
		mUserGoalColumns = new String[] {UserGoals.COLUMN_NAME_TITLE, UserGoals._ID};
		Cursor cursor = getCursor();
		int[] to = new int[] {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(
			context, android.R.layout.simple_list_item_1,
			cursor, mUserGoalColumns, to, 0);
		
		setListAdapter(mAdapter);
	}
	
	private Cursor getCursor() {
		return mDb.query(UserGoals.TABLE_NAME, mUserGoalColumns, null, null, null, null, null);
	}
	
	public void refreshCursor() {
		mAdapter.swapCursor(getCursor());
	}
}
