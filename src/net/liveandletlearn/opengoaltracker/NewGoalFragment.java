package net.liveandletlearn.opengoaltracker;

import net.liveandletlearn.opengoaltracker.OGTContract.OGTDbHelper;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NewGoalFragment extends Fragment {
	
	private OGTDbHelper mDbHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			                 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_new_goal, container, false);
		// Would it be better to pass this in from the view? We'd have the context then...
		// Check how java does instance properties :/
		mDbHelper = new OGTDbHelper(view.getContext());
		return view;
	}
	
    public void addGoal(View view) {
    	SQLiteDatabase db = mDbHelper.getWritableDatabase();
    	ContentValues values = new ContentValues();

    	EditText newGoal = (EditText) view.findViewById(R.id.new_goal);
    	String title = newGoal.getText().toString();

    	values.put(OGTContract.UserGoals.COLUMN_NAME_TITLE, title);
    	
    	db.insert(OGTContract.UserGoals.TABLE_NAME, null, values);
    }
}
