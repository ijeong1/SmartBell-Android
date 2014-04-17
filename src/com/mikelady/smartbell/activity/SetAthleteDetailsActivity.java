package com.mikelady.smartbell.activity;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.menu;
import com.parse.Parse;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SetAthleteDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_athlete_details);
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_user_details, menu);
		return true;
	}

}
