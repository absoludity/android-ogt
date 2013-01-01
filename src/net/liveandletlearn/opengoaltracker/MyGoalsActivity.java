package net.liveandletlearn.opengoaltracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.BaseAdapter;

public class MyGoalsActivity extends Activity implements NewGoalFragment.OnAddGoalListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_goals);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_goal, menu);
		return true;
	}
	
	public void onAddGoal(long id) {
		GoalListFragment listFragment = (GoalListFragment)
			getFragmentManager().findFragmentById(R.id.list);
		if (listFragment != null) {
			listFragment.refreshCursor();
		}
	}

}
