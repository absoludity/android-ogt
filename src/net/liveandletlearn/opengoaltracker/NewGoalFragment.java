package net.liveandletlearn.opengoaltracker;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class NewGoalFragment extends Fragment {
	
	private OGTDatabase mDb;
	private OnAddGoalListener mAddGoalListener;
	
	public interface OnAddGoalListener {
		public void onAddGoal(long id);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			                 Bundle savedInstanceState) {
		final View fragmentView = inflater.inflate(R.layout.fragment_new_goal, container, false);
		mDb = new OGTDatabase(fragmentView.getContext());
		
		// See http://stackoverflow.com/a/7969020/523729
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
    	EditText newGoal = (EditText) view.findViewById(R.id.new_goal);
    	String title = newGoal.getText().toString();
    	if (title.isEmpty()) {
    		return;
    	}
    	
    	long id = mDb.insertGoal(title);        
        newGoal.setText("");
        mAddGoalListener.onAddGoal(id);
    }
    
    @Override
    public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	
    	try {
    		mAddGoalListener = (OnAddGoalListener) activity;
    	} catch (ClassCastException e) {
    		throw new ClassCastException(
    			activity.toString() + " must implement OnAddGoalListener.");
    	}
    }
}
