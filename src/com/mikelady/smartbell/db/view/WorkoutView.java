package com.mikelady.smartbell.db.view;

import java.util.Date;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.activity.SelectAthleteActivity;
import com.mikelady.smartbell.activity.SelectWorkoutActivity;
import com.mikelady.smartbell.primitives.Workout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class WorkoutView extends LinearLayout {

	private Button m_workoutButton;
	
	/** The data version of this View, containing the workout's information. */
	private Workout m_workout;

	private OnWorkoutChangeListener m_onWorkoutChangeListener;
	

	/**
	 * Basic Constructor that takes only an application Context.
	 * 
	 * @param context
	 *            The application Context in which this view is being added. 
	 *            
	 * @param workout
	 * 			  The Workout this view is responsible for displaying.
	 */
	public WorkoutView(Context context, Workout workout) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_workout, this, true);
	
		this.m_workoutButton = (Button)findViewById(R.id.workout_button);
		this.m_workoutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				notifyOnWorkoutChangeListener();
			}
		});

		this.setWorkout(workout);
	}

	/**
	 * Mutator method for changing the Workout object this View displays. This View
	 * will be updated to display the correct contents of the new Workout.
	 * 
	 * @param workout
	 *            The Workout object which this View will display.
	 */
	public void setWorkout(Workout workout) {
		this.m_workout = workout;
		this.m_workoutButton.setText("Workout "+(new Date(workout.getTimestamp())).toString());
	}

	
	public Workout getWorkout(){
		return m_workout;
	}
	

	/**
	 * Mutator method for changing the OnWorkoutChangeListener object this WorkoutView
	 * notifies when the state its underlying Workout object changes.
	 * 
	 * It is possible and acceptable for m_onWorkoutChangeListener to be null, you
	 * should allow for this.
	 * 
	 * @param listener
	 *            The OnWorkoutChangeListener object that should be notified when
	 *            the underlying Workout changes state.
	 */
	public void setOnWorkoutChangeListener(OnWorkoutChangeListener listener) {
		m_onWorkoutChangeListener = listener;
	}

	/**
	 * This method should always be called after the state of m_workout is changed.
	 * 
	 * It is possible and acceptable for m_onWorkoutChangeListener to be null, you
	 * should test for this.
	 * 
	 * This method should not be called if setWorkout(...) is called, since the
	 * internal state of the Workout object that m_workout references is not be
	 * changed. Rather, m_workout reference is being changed to reference a
	 * different Workout object.
	 */
	protected void notifyOnWorkoutChangeListener() {
		Log.d("mlady", "m_onWorkoutChangeListener: "+m_onWorkoutChangeListener);
		if(m_onWorkoutChangeListener != null)
			m_onWorkoutChangeListener.onWorkoutChanged(this, m_workout);
		else{
			setOnWorkoutChangeListener((SelectWorkoutActivity)getContext());
			m_onWorkoutChangeListener.onWorkoutChanged(this, m_workout);
		}
	}
	
	/**
	 * Interface definition for a callback to be invoked when the underlying
	 * Workout is changed in this WorkoutView object.
	 */
	public static interface OnWorkoutChangeListener {

		/**
		 * Called when the underlying Workout in an WorkoutView object changes state.
		 * 
		 * @param view
		 *            The WorkoutView in which the Workout was changed.
		 * @param workout
		 *            The Workout that was changed.
		 */
		public void onWorkoutChanged(WorkoutView view, Workout workout);
	}

}
