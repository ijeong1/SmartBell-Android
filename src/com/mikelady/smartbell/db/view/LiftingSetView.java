package com.mikelady.smartbell.db.view;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.primitives.LiftingSet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LiftingSetView extends LinearLayout {

	private Button m_liftingSetButton;
	
	/** The data version of this View, containing the liftingSet's information. */
	private LiftingSet m_liftingSet;

	private OnLiftingSetChangeListener m_onLiftingSetChangeListener;
	

	/**
	 * Basic Constructor that takes only an application Context.
	 * 
	 * @param context
	 *            The application Context in which this view is being added. 
	 *            
	 * @param liftingSet
	 * 			  The LiftingSet this view is responsible for displaying.
	 */
	public LiftingSetView(Context context, LiftingSet liftingSet) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_lifting_set, this, true);
	
		this.m_liftingSetButton = (Button)findViewById(R.id.lifting_set_button);
		this.m_liftingSetButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				notifyOnLiftingSetChangeListener();
			}
		});

		this.setLiftingSet(liftingSet);
	}

	/**
	 * Mutator method for changing the LiftingSet object this View displays. This View
	 * will be updated to display the correct contents of the new LiftingSet.
	 * 
	 * @param liftingSet
	 *            The LiftingSet object which this View will display.
	 */
	public void setLiftingSet(LiftingSet liftingSet) {
		this.m_liftingSet = liftingSet;
		this.m_liftingSetButton.setText("Set "+LiftingSet.ExerciseId.values()[liftingSet.getExercise()]+" "+liftingSet.getId());
	}

	
	public LiftingSet getLiftingSet(){
		return m_liftingSet;
	}
	

	/**
	 * Mutator method for changing the OnLiftingSetChangeListener object this LiftingSetView
	 * notifies when the state its underlying LiftingSet object changes.
	 * 
	 * It is possible and acceptable for m_onLiftingSetChangeListener to be null, you
	 * should allow for this.
	 * 
	 * @param listener
	 *            The OnLiftingSetChangeListener object that should be notified when
	 *            the underlying LiftingSet changes state.
	 */
	public void setOnLiftingSetChangeListener(OnLiftingSetChangeListener listener) {
		m_onLiftingSetChangeListener = listener;
	}

	/**
	 * This method should always be called after the state of m_liftingSet is changed.
	 * 
	 * It is possible and acceptable for m_onLiftingSetChangeListener to be null, you
	 * should test for this.
	 * 
	 * This method should not be called if setLiftingSet(...) is called, since the
	 * internal state of the LiftingSet object that m_liftingSet references is not be
	 * changed. Rather, m_liftingSet reference is being changed to reference a
	 * different LiftingSet object.
	 */
	protected void notifyOnLiftingSetChangeListener() {
		Log.d("mlady", "m_onLiftingSetChangeListener: "+m_onLiftingSetChangeListener);
		if(m_onLiftingSetChangeListener != null)
			m_onLiftingSetChangeListener.onLiftingSetChanged(this, m_liftingSet);
	}
	
	/**
	 * Interface definition for a callback to be invoked when the underlying
	 * LiftingSet is changed in this LiftingSetView object.
	 */
	public static interface OnLiftingSetChangeListener {

		/**
		 * Called when the underlying LiftingSet in an LiftingSetView object changes state.
		 * 
		 * @param view
		 *            The LiftingSetView in which the LiftingSet was changed.
		 * @param liftingSet
		 *            The LiftingSet that was changed.
		 */
		public void onLiftingSetChanged(LiftingSetView view, LiftingSet liftingSet);
	}

}
