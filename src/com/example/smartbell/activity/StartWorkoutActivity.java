package com.example.smartbell.activity;

import com.example.smartbell.R;
import com.example.smartbell.R.id;
import com.example.smartbell.R.layout;
import com.example.smartbell.R.menu;
import com.example.smartbell.R.string;
import com.example.smartbell.fragment.SelectExerciseFragment;
import com.example.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;

public class StartWorkoutActivity extends FragmentActivity 
		implements OnFragmentInteractionListener{
	
	private android.support.v4.app.FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_workout, menu);
		return true;
	}
	
	private void initLayout(){
		setContentView(R.layout.activity_start_workout);
		fragmentManager = getSupportFragmentManager();
		SelectExerciseFragment selectExerciseFragment = new SelectExerciseFragment();
		
		
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.start_workout_activity_layout, selectExerciseFragment);
		String startWorkoutActivityTag = getResources().getString(R.string.start_workout_activity_tag);
		fragmentTransaction.addToBackStack(startWorkoutActivityTag);
		fragmentTransaction.commit();
		
		Log.d("startWorkoutActivity","backstack count: "+fragmentManager.getBackStackEntryCount());

	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

}
