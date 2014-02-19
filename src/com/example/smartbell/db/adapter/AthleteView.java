package com.example.smartbell.db.adapter;

import com.example.smartbell.R;
import com.example.smartbell.primitives.Athlete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class AthleteView extends LinearLayout {

	private Button m_athleteButton;
	
	/** The data version of this View, containing the joke's information. */
	private Athlete m_athlete;
	

	/**
	 * Basic Constructor that takes only an application Context.
	 * 
	 * @param context
	 *            The application Context in which this view is being added. 
	 *            
	 * @param athlete
	 * 			  The Joke this view is responsible for displaying.
	 */
	public AthleteView(Context context, Athlete athlete) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_athlete, this, true);
	
		this.m_athleteButton = (Button)findViewById(R.id.athlete_button);

		this.setAthlete(athlete);
	}

	/**
	 * Mutator method for changing the Joke object this View displays. This View
	 * will be updated to display the correct contents of the new Joke.
	 * 
	 * @param athlete
	 *            The Joke object which this View will display.
	 */
	public void setAthlete(Athlete athlete) {
		this.m_athlete = athlete;
		this.m_athleteButton.setText(m_athleteButton.getText()+""+athlete.getId());
	}

	
	public Athlete getAthlete(){
		return m_athlete;
	}

}
