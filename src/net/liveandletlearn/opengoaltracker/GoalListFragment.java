package net.liveandletlearn.opengoaltracker;

import net.liveandletlearn.opengoaltracker.OGTDatabase.UserGoals;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class GoalListFragment extends ListFragment {

	private OGTDatabase mDb;
	private String[] mUserGoalColumns;
	public SimpleCursorAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Context context = this.getActivity();
		mDb = new OGTDatabase(context);
		mUserGoalColumns = new String[] {UserGoals.COLUMN_NAME_TITLE, UserGoals._ID};
		Cursor cursor = mDb.UserGoals(mUserGoalColumns);
		int[] to = new int[] {android.R.id.text1};
		mAdapter = new SimpleCursorAdapter(
			context, android.R.layout.simple_list_item_1,
			cursor, mUserGoalColumns, to, 0);
		setListAdapter(mAdapter);
	}
		
	public void refreshCursor() {
		mAdapter.swapCursor(mDb.UserGoals(mUserGoalColumns));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), GoalDetailActivity.class);
        startActivity(intent);	
	}
}