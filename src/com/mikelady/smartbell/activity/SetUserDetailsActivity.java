package com.mikelady.smartbell.activity;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SetUserDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_user_details);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_user_details, menu);
		return true;
	}

}
