package com.mikelady.smartbell.activity;
import java.util.ArrayList;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.db.adapter.AthleteCursorAdapter;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.AthleteTable;
import com.mikelady.smartbell.db.view.AthleteView;
import com.mikelady.smartbell.db.view.AthleteView.OnAthleteChangeListener;
import com.mikelady.smartbell.primitives.Athlete;
import com.mikelady.smartbell.primitives.Moment;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SelectAthleteActivity extends FragmentActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, OnAthleteChangeListener {
	
	private static final int LOADER_ID = 1;
	
	protected Button newAthleteButton;
	protected ListView athleteListViewGroup;
	protected Cursor athleteCursor;
	protected AthleteCursorAdapter athleteCursorAdapter;
//	protected AthleteListAdapter athleteListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "6YKFkBFeURXqEB3cK9YvPLzRDiMHYvqGsHZy5YMt", "YukuM4BgD5YT1jsV9npeU7iLnUjolmvmNz1bNONX");
		
		athleteCursorAdapter = new AthleteCursorAdapter(this, null, 0);
		getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		
		initLayout();
		
		Log.d("SelectAthleteActivity", "after oncreate");
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
		
		newAthleteButton = (Button)this.findViewById(R.id.new_athlete_button);
		athleteListViewGroup = (ListView)this.findViewById(R.id.athlete_list_view_group);
//		athleteCursorAdapter = new AthleteCursorAdapter(this, null, 0);
		athleteCursorAdapter.setOnAthleteChangeListener(this);
		athleteListViewGroup.setAdapter(athleteCursorAdapter);

		newAthleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addAthlete(new Athlete());
			}
		});
		
		
	}
	
	/**
	 * Method used for encapsulating the logic necessary to properly add a new
	 * Athlete to database, and display it on screen.
	 * 
	 * @param athlete
	 *            The Athlete to add to list of Athletes.
	 */
	protected void addAthlete(Athlete athlete) {
		Uri addRow = Uri.parse(SmartBellContentProvider.ATHLETE_CONTENT_URI+"/athlete/"+athlete.getId());
		ContentValues cv = new ContentValues();
//		cv.put(AthleteTable.ATHLETE_KEY_ID, athlete.getId());
		cv.put(AthleteTable.ATHLETE_IS_MALE, athlete.isMale());
		cv.put(AthleteTable.ATHLETE_HEIGHT, 0);
		cv.put(AthleteTable.ATHLETE_WEIGHT, 0);
		cv.put(AthleteTable.ATHLETE_FOREARM, 0);
		cv.put(AthleteTable.ATHLETE_UPPER_ARM, 0);
		cv.put(AthleteTable.ATHLETE_TORSO, 0);
		cv.put(AthleteTable.ATHLETE_THIGH, 0);
		cv.put(AthleteTable.ATHLETE_SHIN, 0);

		athlete.setId(Integer.valueOf(getContentResolver().insert(addRow, cv).getLastPathSegment()));
		Log.d("SelectAthleteActivity", "addAthlete: "+athlete);
		athleteCursorAdapter.setOnAthleteChangeListener(null);
		fillData();
	}
	
	protected void fillData(){
		getSupportLoaderManager().restartLoader(0, null, this);
		this.athleteListViewGroup.setAdapter(athleteCursorAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] proj = new String[AthleteTable.ATHLETE_COL_NAMES.length];
		proj[AthleteTable.ATHLETE_COL_ID] = AthleteTable.ATHLETE_KEY_ID;
		proj[AthleteTable.ATHLETE_COL_IS_MALE] = AthleteTable.ATHLETE_IS_MALE;
		proj[AthleteTable.ATHLETE_COL_HEIGHT] = AthleteTable.ATHLETE_HEIGHT;
		proj[AthleteTable.ATHLETE_COL_WEIGHT] = AthleteTable.ATHLETE_WEIGHT;
		proj[AthleteTable.ATHLETE_COL_FOREARM] = AthleteTable.ATHLETE_FOREARM;
		proj[AthleteTable.ATHLETE_COL_UPPER_ARM] = AthleteTable.ATHLETE_UPPER_ARM;
		proj[AthleteTable.ATHLETE_COL_TORSO] = AthleteTable.ATHLETE_TORSO;
		proj[AthleteTable.ATHLETE_COL_THIGH] = AthleteTable.ATHLETE_THIGH;
		proj[AthleteTable.ATHLETE_COL_SHIN] = AthleteTable.ATHLETE_SHIN;
		Uri m_uri = Uri.parse(SmartBellContentProvider.ATHLETE_CONTENT_URI + "/athlete/");
		
		CursorLoader cl = new CursorLoader(this, m_uri, proj, null, null, null);
		
		return cl;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		Log.d("SelectAthleteActivity", "onLoadFinished arg0:" + arg0 + " arg1: "+arg1);
		athleteCursorAdapter.swapCursor(arg1);
		this.athleteListViewGroup.setAdapter(athleteCursorAdapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		Log.d("SelectAthleteActivity", "onLoaderReset");
		athleteCursorAdapter.swapCursor(null);
	}

	@Override
	public void onAthleteChanged(AthleteView view, Athlete athlete) {
		// TODO Auto-generated method stub

        Log.d("SelectAthleteActivity", "onAthleteChanged, starting Activity");
        Intent intent = new Intent(SelectAthleteActivity.this, SelectWorkoutActivity.class);
        intent.putExtra(getString(R.string.athlete_id), athlete.getId());
        startActivity(intent);
	}

}
