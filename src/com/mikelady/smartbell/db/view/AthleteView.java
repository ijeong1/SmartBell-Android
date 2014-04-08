package com.mikelady.smartbell.db.view;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.primitives.Athlete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AthleteView extends LinearLayout {

	private Button m_athleteButton;
	
	/** The data version of this View, containing the athlete's information. */
	private Athlete m_athlete;

	private OnAthleteChangeListener m_onAthleteChangeListener;
	

	/**
	 * Basic Constructor that takes only an application Context.
	 * 
	 * @param context
	 *            The application Context in which this view is being added. 
	 *            
	 * @param athlete
	 * 			  The Athlete this view is responsible for displaying.
	 */
	public AthleteView(Context context, final Athlete athlete) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_athlete, this, true);
	
		this.m_athleteButton = (Button)findViewById(R.id.athlete_button);
		this.m_athleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("AthleteView", "AthleteView "+athlete+" clicked");
				notifyOnAthleteChangeListener();
			}
		});

		this.setAthlete(athlete);
	}

	/**
	 * Mutator method for changing the Athlete object this View displays. This View
	 * will be updated to display the correct contents of the new Athlete.
	 * 
	 * @param athlete
	 *            The Athlete object which this View will display.
	 */
	public void setAthlete(Athlete athlete) {
		this.m_athlete = athlete;
		this.m_athleteButton.setText("Athlete "+athlete.getId());
	}

	
	public Athlete getAthlete(){
		return m_athlete;
	}
	

	/**
	 * Mutator method for changing the OnAthleteChangeListener object this AthleteView
	 * notifies when the state its underlying Athlete object changes.
	 * 
	 * It is possible and acceptable for m_onAthleteChangeListener to be null, you
	 * should allow for this.
	 * 
	 * @param listener
	 *            The OnAthleteChangeListener object that should be notified when
	 *            the underlying Athlete changes state.
	 */
	public void setOnAthleteChangeListener(OnAthleteChangeListener listener) {
		m_onAthleteChangeListener = listener;
	}

	/**
	 * This method should always be called after the state of m_athlete is changed.
	 * 
	 * It is possible and acceptable for m_onAthleteChangeListener to be null, you
	 * should test for this.
	 * 
	 * This method should not be called if setAthlete(...) is called, since the
	 * internal state of the Athlete object that m_athlete references is not be
	 * changed. Rather, m_athlete reference is being changed to reference a
	 * different Athlete object.
	 */
	protected void notifyOnAthleteChangeListener() {
		Log.d("AthleteView", "notifyOnAthleteChangeListener m_athlete: "+m_athlete+" m_onAthleteChangeListener: "+m_onAthleteChangeListener);
		if(m_onAthleteChangeListener != null)
			m_onAthleteChangeListener.onAthleteChanged(this, m_athlete);
	}
	
	/**
	 * Interface definition for a callback to be invoked when the underlying
	 * Athlete is changed in this AthleteView object.
	 */
	public static interface OnAthleteChangeListener {

		/**
		 * Called when the underlying Athlete in an AthleteView object changes state.
		 * 
		 * @param view
		 *            The AthleteView in which the Athlete was changed.
		 * @param athlete
		 *            The Athlete that was changed.
		 */
		public void onAthleteChanged(AthleteView view, Athlete athlete);
	}

}
