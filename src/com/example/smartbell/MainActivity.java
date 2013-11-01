package com.example.smartbell;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	protected Button startWorkoutButton;
	protected Button viewPrevWorkoutsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void initLayout(){
		setContentView(R.layout.activity_main);
		
		startWorkoutButton = (Button)this.findViewById(R.id.start_workout_button);
		viewPrevWorkoutsButton = (Button)this.findViewById(R.id.view_previous_workouts_button);
		
		startWorkoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, StartWorkoutActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

}
