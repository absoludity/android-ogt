package net.liveandletlearn.opengoaltracker;

import net.liveandletlearn.opengoaltracker.OGTContract.OGTDbHelper;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewGoalFragment extends Fragment {
	
	private OGTDbHelper mDbHelper;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			                 Bundle savedInstanceState) {
		final View fragmentView = inflater.inflate(R.layout.fragment_new_goal, container, false);
		// Would it be better to pass this in from the view? We'd have the context then...
		mDbHelper = new OGTDbHelper(fragmentView.getContext());
		
		// See http://stackoverflow.com/questions/6091194/how-to-handle-button-clicks-using-the-xml-onclick-within-fragments
		Button addGoalButton = (Button) fragmentView.findViewById(R.id.add_goal_button);
		addGoalButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				addGoal(fragmentView);
			}
		});
		return fragmentView;
	}
	
    public void addGoal(View view) {
    	
    	SQLiteDatabase db = mDbHelper.getWritableDatabase();
    	ContentValues values = new ContentValues();

    	EditText newGoal = (EditText) view.findViewById(R.id.new_goal);
    	
    	String title = newGoal.getText().toString();
    	if (title.isEmpty()) {
    		return;
    	}
    	values.put(OGTContract.UserGoals.COLUMN_NAME_TITLE, title);
        	
        db.insert(OGTContract.UserGoals.TABLE_NAME, null, values);    		
    }
}
