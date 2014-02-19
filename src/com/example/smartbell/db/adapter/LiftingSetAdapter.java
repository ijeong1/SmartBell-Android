package com.example.smartbell.db.adapter;

import java.util.List;

import com.example.smartbell.db.LiftingSetView;
import com.example.smartbell.primitives.LiftingSet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This class binds the visual SetViews and the data behind them (Sets).
 */
public class LiftingSetAdapter extends BaseAdapter {

	/** The application Context in which this LiftingSetAdapter is being used. */
	private Context m_context;

	/** The data set to which this LiftingSetAdapter is bound. */
	private List<LiftingSet> m_liftingSet;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Set objects to which it is bound.
	 * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
	 * 
	 * @param context
	 *            The application Context in which this LiftingSetAdapter is being
	 *            used.
	 * 
	 * @param liftingSet
	 *            The Collection of Set objects to which this LiftingSetAdapter
	 *            is bound.
	 */
	public LiftingSetAdapter(Context context, List<LiftingSet> liftingSet) {
		this.m_context = context;
		this.m_liftingSet = liftingSet;
	}

	public int getCount() {
		return this.m_liftingSet.size();
	}

	public Object getItem(int position) {
		return this.m_liftingSet.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LiftingSetView liftingSetView = null;
		
		if (convertView == null) {
			liftingSetView = new LiftingSetView(m_context, this.m_liftingSet.get(position));
		}
		else {
			liftingSetView = (LiftingSetView)convertView;
		}
		liftingSetView.setLiftingSet(this.m_liftingSet.get(position));
		return liftingSetView;
	}
}
