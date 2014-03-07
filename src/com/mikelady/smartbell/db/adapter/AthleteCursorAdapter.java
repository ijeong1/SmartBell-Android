package com.mikelady.smartbell.db.adapter;

import com.mikelady.smartbell.db.table.AthleteTable;
import com.mikelady.smartbell.db.view.AthleteView;
import com.mikelady.smartbell.db.view.AthleteView.OnAthleteChangeListener;
import com.mikelady.smartbell.primitives.Athlete;
import com.mikelady.smartbell.primitives.LiftingSet;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class that functions similarly to LiftingSetListAdapter, but instead uses a Cursor.
 * A Cursor is a list of rows from a database that acts as a medium between the
 * database and a ViewGroup (in this case, a SQLite database table containing rows
 * of jokes and a ListView containing LiftingSetViews).
 */
public class AthleteCursorAdapter extends android.support.v4.widget.CursorAdapter {

	/** The OnLiftingSetChangeListener that should be connected to each of the
	 * AthleteViews created/managed by this Adapter. */
	private OnAthleteChangeListener m_listener;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of LiftingSet objects to which it is bound.
	 * 
	 * @param context
	 *            The application Context in which this LiftingSetListAdapter is being
	 *            used.
	 * 
	 * @param athleteCursor
	 *            A Database Cursor containing a result set of LiftingSets which
	 *            should be bound to LiftingSetViews.
	 *            
	 * @param flags
	 * 			  A list of flags that decide this adapter's behavior.
	 */
	public AthleteCursorAdapter(Context context, Cursor athleteCursor, int flags) {
		super(context, athleteCursor, flags);
	}

	/**
	 * Mutator method for changing the OnLiftingSetChangeListener.
	 * 
	 * @param listener
	 *            The OnLiftingSetChangeListener that will be notified when the
	 *            internal state of any LiftingSet contained in one of this Adapters
	 *            LiftingSetViews is changed.
	 */
	public void setOnAthleteChangeListener(OnAthleteChangeListener mListener) {
		this.m_listener = mListener;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		int athleteID = cursor.getInt(AthleteTable.ATHLETE_COL_ID);
//		boolean athleteIsMale = Boolean.getBoolean(
//				cursor.getString(AthleteTable.ATHLETE_COL_IS_MALE));
		Athlete retrievedAthlete = new Athlete();
		retrievedAthlete.setId(athleteID);
//		retrievedAthlete.setMale(athleteIsMale);
		((AthleteView)view).setOnAthleteChangeListener(null);
		((AthleteView)view).setAthlete(retrievedAthlete);
		((AthleteView)view).setOnAthleteChangeListener(m_listener);
//		int athleteHeight = cursor.getInt(AthleteTable.ATHLETE_COL_HEIGHT);
//		int athleteWeight = cursor.getInt(AthleteTable.ATHLETE_COL_WEIGHT);
//		int athleteForearm = cursor.getInt(AthleteTable.ATHLETE_COL_FOREARM);
//		int athleteUpperArm = cursor.getInt(AthleteTable.ATHLETE_COL_UPPER_ARM);
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup vg) {
	
		int athleteID = cursor.getInt(AthleteTable.ATHLETE_COL_ID);
//		boolean athleteIsMale = Boolean.getBoolean(
//				cursor.getString(AthleteTable.ATHLETE_COL_IS_MALE));
		Athlete retrievedAthlete = new Athlete();
		retrievedAthlete.setId(athleteID);
//		retrievedAthlete.setMale(athleteIsMale);
		
		AthleteView view = new AthleteView(context, retrievedAthlete);
		((AthleteView)view).setOnAthleteChangeListener(m_listener);
		
		return view;
	}
}