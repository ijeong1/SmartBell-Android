package com.mikelady.smartbell.activity;
import com.mikelady.smartbell.R;
import com.mikelady.smartbell.db.adapter.AthleteCursorAdapter;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SelectAthleteActivity extends Activity {
	protected Button newAthleteButton;
	protected LinearLayout athleteList;
	protected AthleteCursorAdapter athleteCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
		
		initLayout();
//		ParseObject testObject = new ParseObject("TestObject");
//		testObject.put("foo", "bar");
//		testObject.saveInBackground();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	private void initLayout(){
		setContentView(R.layout.activity_select_athlete);
		
		newAthleteButton = (Button)this.findViewById(R.id.start_workout_button);
		athleteList = (LinearLayout)this.findViewById(R.id.athlete_list);
		
		newAthleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectAthleteActivity.this, StartWorkoutActivity.class);
				startActivity(intent);
			}
		});
		
	}

}
