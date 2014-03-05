package com.mikelady.smartbell.activity;
import com.mikelady.smartbell.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SelectWorkoutActivity extends FragmentActivity {
	public static Context ctx;
	protected Button startWorkoutButton;
	protected ListView viewPrevWorkoutsViewGroup;
	protected Button userDetailsButton;
	int athleteId;
	private static final int LOADER_ID = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		athleteId = getIntent().getIntExtra(getString(R.string.athlete_id), 0);
		initLayout();
		ctx = this;
		
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void initLayout(){
		setContentView(R.layout.activity_select_workout);
		
		startWorkoutButton = (Button)this.findViewById(R.id.start_workout_button);
		viewPrevWorkoutsViewGroup = (ListView)this.findViewById(R.id.view_previous_workouts_view_group);
		userDetailsButton = (Button)this.findViewById(R.id.user_details_button);
		
		
		startWorkoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectWorkoutActivity.this, StartWorkoutActivity.class);
				startActivity(intent);
				
			}
		});
		
		userDetailsButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectWorkoutActivity.this, SetUserDetailsActivity.class);
				startActivity(intent);
			}
		});
		
	}

}
