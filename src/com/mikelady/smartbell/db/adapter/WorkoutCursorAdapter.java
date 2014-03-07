package com.mikelady.smartbell.db.adapter;

import com.mikelady.smartbell.db.table.WorkoutTable;
import com.mikelady.smartbell.db.view.WorkoutView;
import com.mikelady.smartbell.db.view.WorkoutView.OnWorkoutChangeListener;
import com.mikelady.smartbell.primitives.Workout;

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
public class WorkoutCursorAdapter extends android.support.v4.widget.CursorAdapter {

	/** The OnLiftingSetChangeListener that should be connected to each of the
	 * WorkoutViews created/managed by this Adapter. */
	private OnWorkoutChangeListener m_listener;

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
	public WorkoutCursorAdapter(Context context, Cursor athleteCursor, int flags) {
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
	public void setOnWorkoutChangeListener(OnWorkoutChangeListener mListener) {
		this.m_listener = mListener;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		int workoutID = cursor.getInt(WorkoutTable.WORKOUT_COL_ID);
		long workoutTime = cursor.getLong(WorkoutTable.WORKOUT_COL_TIMESTAMP);

		Workout retrievedWorkout = new Workout();
		retrievedWorkout.setId(workoutID);
		retrievedWorkout.setTimestamp(workoutTime);
		
		((WorkoutView)view).setOnWorkoutChangeListener(null);
		((WorkoutView)view).setWorkout(retrievedWorkout);
		((WorkoutView)view).setOnWorkoutChangeListener(m_listener);
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup vg) {
		
		int workoutID = cursor.getInt(WorkoutTable.WORKOUT_COL_ID);
		long workoutTime = cursor.getLong(WorkoutTable.WORKOUT_COL_TIMESTAMP);
		Workout retrievedWorkout = new Workout();
		retrievedWorkout.setId(workoutID);
		retrievedWorkout.setTimestamp(workoutTime);
		
		WorkoutView view = new WorkoutView(context, retrievedWorkout);
		((WorkoutView)view).setOnWorkoutChangeListener(m_listener);
		
		return view;
	}
}