package com.mikelady.smartbell.activity;
import com.mikelady.smartbell.R;
import com.mikelady.smartbell.db.adapter.AthleteCursorAdapter;
import com.mikelady.smartbell.db.adapter.WorkoutCursorAdapter;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.WorkoutTable;
import com.mikelady.smartbell.db.table.WorkoutTable;
import com.mikelady.smartbell.db.table.WorkoutTable;
import com.mikelady.smartbell.db.view.WorkoutView;
import com.mikelady.smartbell.db.view.WorkoutView.OnWorkoutChangeListener;

import com.mikelady.smartbell.primitives.Workout;
import com.mikelady.smartbell.primitives.Workout;
import com.parse.Parse;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectWorkoutActivity extends FragmentActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, OnWorkoutChangeListener{
	public static Context ctx;
	private static final int LOADER_ID = 2;
	
	protected Button newWorkoutButton;
	protected ListView workoutListViewGroup;
	protected Button userDetailsButton;
	int athleteId;
	
	protected Cursor workoutCursor;
	protected WorkoutCursorAdapter workoutCursorAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		athleteId = getIntent().getIntExtra(getString(R.string.athlete_id), 0);

		ctx = this;
		workoutCursorAdapter = new WorkoutCursorAdapter(this, null, 0);
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		initLayout();
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void initLayout(){
		setContentView(R.layout.activity_select_workout);
		
		newWorkoutButton = (Button)this.findViewById(R.id.new_workout_button);
		workoutCursorAdapter.setOnWorkoutChangeListener(this);
		
		workoutListViewGroup = (ListView)this.findViewById(R.id.view_previous_workouts_view_group);
		userDetailsButton = (Button)this.findViewById(R.id.user_details_button);
		workoutListViewGroup.setAdapter(workoutCursorAdapter);
		
		newWorkoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addWorkout(new Workout());
			}
		});
		
		userDetailsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectWorkoutActivity.this, SetAthleteDetailsActivity.class);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * Method used for encapsulating the logic necessary to properly add a new
	 * Workout to database, and display it on screen.
	 * 
	 * @param workout
	 *            The Workout to add to list of Workouts.
	 */
	protected void addWorkout(Workout workout) {
		Uri addRow = Uri.parse(SmartBellContentProvider.WORKOUT_CONTENT_URI+"/workout/"+workout.getId());
		ContentValues cv = new ContentValues();
//		cv.put(WorkoutTable.WORKOUT_KEY_ID, workout.getId());
		cv.put(WorkoutTable.WORKOUT_TIMESTAMP, workout.getTimestamp());
		cv.put(WorkoutTable.WORKOUT_ATHLETE_ID, athleteId);

		workout.setId(Integer.valueOf(getContentResolver().insert(addRow, cv).getLastPathSegment()));
		Log.d("mlady", "workout: "+workout);
		workoutCursorAdapter.setOnWorkoutChangeListener(null);
		fillData();
	}
	
	protected void fillData(){
		getSupportLoaderManager().restartLoader(0, null, this);
		this.workoutListViewGroup.setAdapter(workoutCursorAdapter);
	}


	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] proj = new String[WorkoutTable.WORKOUT_COL_NAMES.length];
		proj[WorkoutTable.WORKOUT_COL_ID] = WorkoutTable.WORKOUT_KEY_ID;
		proj[WorkoutTable.WORKOUT_COL_TIMESTAMP] = WorkoutTable.WORKOUT_TIMESTAMP;
		proj[WorkoutTable.WORKOUT_COL_ATHLETE_ID] = WorkoutTable.WORKOUT_ATHLETE_ID;

		Uri m_uri = Uri.parse(SmartBellContentProvider.WORKOUT_CONTENT_URI + "/workout/");
		
		CursorLoader cl = new CursorLoader(this, m_uri, proj, ""+WorkoutTable.WORKOUT_ATHLETE_ID+"="+athleteId, null, null);
		return cl;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		workoutCursorAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		workoutCursorAdapter.swapCursor(null);
	}

	@Override
	public void onWorkoutChanged(WorkoutView view, Workout workout) {
//		Uri updateRow = Uri.parse(SmartBellContentProvider.WORKOUT_CONTENT_URI+"/workout/"+workout.getId());
//		Log.d("mlady","updateRow: "+updateRow);
//		ContentValues cv = new ContentValues();
//		cv.put(WorkoutTable.WORKOUT_TIMESTAMP, workout.getTimeStamp());
//
//		getContentResolver().update(updateRow, cv, null, null);
//		
//		workoutCursorAdapter.setOnWorkoutChangeListener(null);
//		
//		fillData();
		
		//put click handler in here for selecting a particular workout
        Log.d("ml", "clicked workout");
        Intent intent = new Intent(SelectWorkoutActivity.this, StartWorkoutActivity.class);
        intent.putExtra(getString(R.string.workout_id), workout.getId());
        
        startActivity(intent);
	}

}
