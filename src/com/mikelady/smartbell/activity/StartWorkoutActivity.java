package com.mikelady.smartbell.activity;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.menu;
import com.mikelady.smartbell.R.string;
import com.mikelady.smartbell.fragment.SelectExerciseFragment;
import com.mikelady.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;
import com.mikelady.smartbell.sensor.TSSBTSensor;
import com.parse.Parse;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class StartWorkoutActivity extends FragmentActivity 
		implements OnFragmentInteractionListener{
	
	private android.support.v4.app.FragmentManager fragmentManager;
	public static Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		initLayout();
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_workout, menu);
		return true;
	}
	
	private void initLayout(){
		Intent intent = getIntent();
		Log.d("StartWorkoutActivity","intent: "+intent);
		Log.d("StartWorkoutActivity","intent.getIntExtra(): "+intent.getIntExtra(getString(R.string.workout_id), 0));
		int workout = intent.getIntExtra(getString(R.string.workout_id), 0);
		
		setContentView(R.layout.activity_start_workout);
		fragmentManager = getSupportFragmentManager();
		FragmentManager.enableDebugLogging(true);
		SelectExerciseFragment selectExerciseFragment = SelectExerciseFragment.newInstance(workout);
		
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		fragmentTransaction.replace(R.id.start_workout_activity_layout, selectExerciseFragment);
//		String startWorkoutActivityTag = getResources().getString(R.string.start_workout_activity_tag);
//		fragmentTransaction.addToBackStack(startWorkoutActivityTag);
		fragmentTransaction.commit();
		
//		Log.d("startWorkoutActivity","backstack count: "+fragmentManager.getBackStackEntryCount());

	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
