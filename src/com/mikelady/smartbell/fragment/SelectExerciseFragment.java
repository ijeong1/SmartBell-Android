package com.mikelady.smartbell.fragment;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.string;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SelectExerciseFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link SelectExerciseFragment#newInstance}
 * factory method to create an instance of this fragment.
 * 
 */
public class SelectExerciseFragment extends Fragment{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_WORKOUT_ID = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
//	private String workoutId;
//	private String mParam2;

	private Button startBenchButton;
	private Button startSquatButton;
	private Button startPressButton;
	private Button endWorkoutButton;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	private OnFragmentInteractionListener mListener;
	private int workoutId;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param workoutId
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SelectExerciseFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SelectExerciseFragment newInstance(int workoutId) {
		SelectExerciseFragment fragment = new SelectExerciseFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_WORKOUT_ID, workoutId);
		fragment.setArguments(args);
		return fragment;
	}

	public SelectExerciseFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			workoutId = getArguments().getInt(ARG_WORKOUT_ID);
//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 return (LinearLayout) inflater.inflate(R.layout.fragment_exercise_selection, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
		
		startBenchButton = (Button)this.getView().findViewById(R.id.bench_button);
		startSquatButton = (Button)this.getView().findViewById(R.id.squat_button);
		startPressButton = (Button)this.getView().findViewById(R.id.press_button);
		endWorkoutButton = (Button)this.getView().findViewById(R.id.end_workout);
		setClickListeners();
	}
	
	private void setClickListeners(){
		startBenchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getActivity().getSupportFragmentManager();
				ExerciseDetailFragment exerciseDetailFragment = ExerciseDetailFragment.newInstance(getResources().getString(R.string.bench), workoutId);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, exerciseDetailFragment);
				String selectExerciseTag = getResources().getString(R.string.select_exercise_tag);
				fragmentTransaction.addToBackStack(selectExerciseTag);
				fragmentTransaction.commit();
				
			}
		});
		
		startSquatButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getActivity().getSupportFragmentManager();
				ExerciseDetailFragment exerciseDetailFragment = ExerciseDetailFragment.newInstance(getResources().getString(R.string.squat), workoutId);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, exerciseDetailFragment);
				String selectExerciseTag = getResources().getString(R.string.select_exercise_tag);
				fragmentTransaction.addToBackStack(selectExerciseTag);
				fragmentTransaction.commit();
				
			}
		});
		
		startPressButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getActivity().getSupportFragmentManager();
				ExerciseDetailFragment exerciseDetailFragment = ExerciseDetailFragment.newInstance(getResources().getString(R.string.press), workoutId);
				
				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.start_workout_activity_layout, exerciseDetailFragment);
				String selectExerciseTag = getResources().getString(R.string.select_exercise_tag);
				fragmentTransaction.addToBackStack(selectExerciseTag);
				fragmentTransaction.commit();
				
			}
		});
		
		endWorkoutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				
				//finalize workout data
				String startWorkoutActivityTag = getResources().getString(R.string.start_workout_activity_tag);
				fragmentManager.popBackStackImmediate(startWorkoutActivityTag, 1);
				
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
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
