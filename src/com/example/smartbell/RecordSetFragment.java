package com.example.smartbell;

import com.example.smartbell.SelectExerciseFragment.OnFragmentInteractionListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link RecordSetFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link RecordSetFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class RecordSetFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";

	// TODO: Rename and change types of parameters
	private String exerciseName;
	private TextView recordTextView;
	private Button endSetButton;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	private OnFragmentInteractionListener mListener;
	private SensorDataHandler sensorDataHandler;
	private boolean is_polling = false;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @return A new instance of fragment RecordSetFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RecordSetFragment newInstance(String param1) {
		RecordSetFragment fragment = new RecordSetFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public RecordSetFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			exerciseName = getArguments().getString(ARG_PARAM1);
		}
		sensorDataHandler = new SensorDataHandler();
   		//Start the sensor updating
        Message start_again_message = new Message();
    	start_again_message.what = 287;
    	sensorDataHandler.sendMessage(start_again_message);
        is_polling = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (LinearLayout) inflater.inflate(R.layout.record_set, container, false);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
		recordTextView = (TextView)this.getView().findViewById(R.id.record_exercise_text);
		recordTextView.setText(exerciseName);
		endSetButton = (Button)this.getView().findViewById(R.id.end_set_button);

		setClickListeners();
	}
	
	private void setClickListeners(){
		endSetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(is_polling)
		    	{
			    	Message stop_message = new Message();
			    	stop_message.what = -1;
			    	sensorDataHandler.sendMessage(stop_message);
			    	is_polling = false;
		    	}
				
				fragmentManager = getFragmentManager();
				SetQuestionFragment setQuestionFragment = SetQuestionFragment.newInstance(exerciseName);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, setQuestionFragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
//	public interface OnFragmentInteractionListener {
//		// TODO: Update argument type and name
//		public void onFragmentInteraction(Uri uri);
//	}

}
