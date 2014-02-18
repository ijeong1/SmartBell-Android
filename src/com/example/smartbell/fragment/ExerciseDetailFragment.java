package com.example.smartbell.fragment;

import com.example.smartbell.R;
import com.example.smartbell.R.id;
import com.example.smartbell.R.layout;
import com.example.smartbell.R.string;
import com.example.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
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
 * {@link ExerciseDetailFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link ExerciseDetailFragment#newInstance}
 * factory method to create an instance of this fragment.
 * 
 */
public class ExerciseDetailFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "exerciseName"; 

	// TODO: Rename and change types of parameters
	private String exerciseName;
	private OnFragmentInteractionListener mListener;
	
	private TextView exerciseDetailTitle;
	private Button instructionsButton;
	private Button startSetButton;
	private Button endExerciseButton;
	private Button showBarpathButton;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ExerciseDetailFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ExerciseDetailFragment newInstance(String param1) {
		ExerciseDetailFragment fragment = new ExerciseDetailFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public ExerciseDetailFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			exerciseName = getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (LinearLayout) inflater.inflate(R.layout.exercise_detail, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
		
		exerciseDetailTitle = (TextView)this.getView().findViewById(R.id.exercise_detail_title);
		exerciseDetailTitle.setText(exerciseName);
		
		instructionsButton = (Button)this.getView().findViewById(R.id.instructions_button);
		startSetButton = (Button)this.getView().findViewById(R.id.start_set_button);
		endExerciseButton = (Button)this.getView().findViewById(R.id.end_exercise_button);
		showBarpathButton = (Button)this.getView().findViewById(R.id.bar_path_button);
		
		setClickListeners();
	}
	
	private void setClickListeners(){
		instructionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				ExerciseInstructionFragment exerciseInstructionFragment = ExerciseInstructionFragment.newInstance(exerciseName);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, exerciseInstructionFragment);
				String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
				fragmentTransaction.addToBackStack(exerciseDetailTag);
				fragmentTransaction.commit();
				Log.d("exerciseDetailFragment", "after instruction fragment commit");
			}
		});
		
		startSetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				RecordSetFragment recordSetFragment = RecordSetFragment.newInstance(exerciseName);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, recordSetFragment);
				String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
				fragmentTransaction.addToBackStack(exerciseDetailTag);
				fragmentTransaction.commit();
			}
		});
		
		endExerciseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				
				//save set data to database
				String selectExerciseTag = getResources().getString(R.string.select_exercise_tag);
				fragmentManager.popBackStackImmediate(selectExerciseTag, 1);
				
			}
		});
		
		showBarpathButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				BarPathFragment barPathFragment = BarPathFragment.newInstance();
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, barPathFragment);
				String barPathTag = getResources().getString(R.string.bar_path_tag);
				fragmentTransaction.addToBackStack(barPathTag);
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
