package com.mikelady.smartbell.activity;
import com.mikelady.smartbell.R;
import com.mikelady.smartbell.db.adapter.AthleteCursorAdapter;
import com.mikelady.smartbell.db.provider.AthleteContentProvider;
import com.mikelady.smartbell.db.table.AthleteTable;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SelectAthleteActivity extends FragmentActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
	
	private static final int LOADER_ID = 1;
	
	protected Button newAthleteButton;
	protected ListView athleteListViewGroup;
	protected AthleteCursorAdapter athleteCursorAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
		
		athleteCursorAdapter = new AthleteCursorAdapter(this, null, 0);
		
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		
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
		athleteListViewGroup = (ListView)this.findViewById(R.id.athlete_list_view_group);
		athleteListViewGroup.setAdapter(athleteCursorAdapter);
		
		newAthleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SelectAthleteActivity.this, StartWorkoutActivity.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] proj = new String[9];
		proj[AthleteTable.ATHLETE_COL_ID] = AthleteTable.ATHLETE_KEY_ID;
		proj[AthleteTable.ATHLETE_COL_IS_MALE] = AthleteTable.ATHLETE_IS_MALE;
		proj[AthleteTable.ATHLETE_COL_HEIGHT] = AthleteTable.ATHLETE_HEIGHT;
		proj[AthleteTable.ATHLETE_COL_WEIGHT] = AthleteTable.ATHLETE_WEIGHT;
		proj[AthleteTable.ATHLETE_COL_FOREARM] = AthleteTable.ATHLETE_FOREARM;
		proj[AthleteTable.ATHLETE_COL_UPPER_ARM] = AthleteTable.ATHLETE_UPPER_ARM;
		proj[AthleteTable.ATHLETE_COL_TORSO] = AthleteTable.ATHLETE_TORSO;
		proj[AthleteTable.ATHLETE_COL_THIGH] = AthleteTable.ATHLETE_THIGH;
		proj[AthleteTable.ATHLETE_COL_SHIN] = AthleteTable.ATHLETE_SHIN;
		Uri m_uri = Uri.parse(AthleteContentProvider.CONTENT_URI + "/athlete/");
		
		CursorLoader cl = new CursorLoader(this, m_uri, proj, null, null, null);
		
		return cl;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		athleteCursorAdapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		athleteCursorAdapter.swapCursor(null);
	}

}
