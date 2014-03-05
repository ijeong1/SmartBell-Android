package com.mikelady.smartbell.activity;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.menu;
import com.mikelady.smartbell.barpath.BarPathView;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BarPathActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new BarPathView(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.bar_path, menu);
		return true;
	}

}
