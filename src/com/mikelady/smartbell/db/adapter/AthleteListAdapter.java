package com.mikelady.smartbell.db.adapter;

import java.util.List;

import com.mikelady.smartbell.db.view.AthleteView;
import com.mikelady.smartbell.primitives.Athlete;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This class binds the visual AthleteViews and the data behind them (Athletes).
 */
public class AthleteListAdapter extends BaseAdapter {

	/** The application Context in which this AthleteListAdapter is being used. */
	private Context m_context;

	/** The data set to which this AthleteListAdapter is bound. */
	private List<Athlete> m_athleteList;

	/**
	 * Parameterized constructor that takes in the application Context in which
	 * it is being used and the Collection of Athlete objects to which it is bound.
	 * m_nSelectedPosition will be initialized to Adapter.NO_SELECTION.
	 * 
	 * @param context
	 *            The application Context in which this AthleteListAdapter is being
	 *            used.
	 * 
	 * @param athleteList
	 *            The Collection of Athlete objects to which this AthleteListAdapter
	 *            is bound.
	 */
	public AthleteListAdapter(Context context, List<Athlete> athleteList) {
		m_context = context;
		m_athleteList = athleteList;
	}

	@Override
	public int getCount() {
		return m_athleteList.size();
	}

	@Override
	public Object getItem(int position) {
		return m_athleteList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AthleteView av;
		Log.d("ml","AthleteView position: "+position+" Athlete:"+m_athleteList.get(position));
		if(convertView == null){
			Log.d("ml", "convertView == null");
			return new AthleteView(m_context, m_athleteList.get(position));
		}
		else{
			Log.d("ml", "convertView != null");
			av = (AthleteView) convertView;
			Athlete a = m_athleteList.get(position).clone();
			av.setAthlete(a);
			return av;
		}
	
	}
}
