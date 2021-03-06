package net.liveandletlearn.opengoaltracker;

import net.liveandletlearn.opengoaltracker.OGTDatabase.UserGoals;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoalDetailActivity extends FragmentActivity implements ActionBar.TabListener {
    public static final String GOAL_ID = "goal_id";
	GoalDetailsPagerAdapter mGoalDetailsPagerAdapter;

    private long mGoalId;
    /**
     * The {@link android.support.v4.view.ViewPager} that will display the object collection.
     */
    private ViewPager mViewPager;
    private OGTDatabase mDb; 

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details);

        Intent intent = getIntent();
        mGoalId = intent.getLongExtra(GoalDetailActivity.GOAL_ID, -1);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mDb = new OGTDatabase(this);
        mGoalDetailsPagerAdapter = new GoalDetailsPagerAdapter(getSupportFragmentManager(), mGoalId);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mGoalDetailsPagerAdapter);
        //mViewPager.
   		
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mGoalDetailsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mGoalDetailsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

    }
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
            case android.R.id.home:
            	// XXX Why can't we just do the same action as the "Back" button,
            	// which looks much nicer (closes current activity transitioning to background,
            	// rather than creating new parent activity and transitioning that to forground).
            	// and would seem more logical than creating a new
            	// intent etc.
            	
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MyGoalsActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public class GoalDetailsPagerAdapter extends FragmentPagerAdapter {
    	// Convert to resource strings.
        public final int[] mLabels = {
        	R.string.goal_details,
        	R.string.todo,
        	R.string.inprogress,
        	R.string.done,
        };
        private long mGoalId;

        public GoalDetailsPagerAdapter(FragmentManager fm, long goalId) {
            super(fm);
            mGoalId = goalId;
        }

        @Override
        public Fragment getItem(int i) {
        	if (i == 0) {
        		Fragment fragment = new GoalOverviewFragment();
        		Bundle args = new Bundle();
        		args.putLong(GoalOverviewFragment.ARG_GOAL_ID, mGoalId);
	            fragment.setArguments(args);
        		return fragment;
        	} else {
	            Fragment fragment = new DemoObjectFragment();
	            Bundle args = new Bundle();
	            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
	            fragment.setArguments(args);
	            return fragment;
        	}
        }

        @Override
        public int getCount() {
            return 4;
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
        	return getString(mLabels[position]);
        }
    }

    public static class DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_goal_overview, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }
    
    public static class GoalOverviewFragment extends Fragment {
    	public static final String ARG_GOAL_ID = "goal_id";
    	
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			                 Bundle savedInstanceState) {
    		View rootView = inflater.inflate(R.layout.fragment_goal_overview, container, false);
    		Bundle args = getArguments();
    		OGTDatabase db = new OGTDatabase(this.getActivity());
    		String[] columns = new String[] {UserGoals._ID, UserGoals.TITLE};
    		Cursor cursor = db.GetGoal(args.getLong(ARG_GOAL_ID), columns);
    		int index = cursor.getColumnIndexOrThrow(UserGoals.TITLE);
    		String title = cursor.getString(index); 
            ((TextView) rootView.findViewById(android.R.id.text1)).setText(title);
            return rootView;

    	}
    }

}