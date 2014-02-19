package com.example.smartbell.fragment;

import com.example.smartbell.R;
import com.example.smartbell.R.id;
import com.example.smartbell.R.layout;
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
 * {@link ExerciseInstructionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the
 * {@link ExerciseInstructionFragment#newInstance} factory method to create an
 * instance of this fragment.
 * 
 */
public class ExerciseInstructionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "exerciseName";

	// TODO: Rename and change types of parameters
	private String exerciseName;
	private TextView exerciseInstructionTitle;
	private TextView exerciseInstructionBody;
//	private Button exerciseInstructionBack;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ExerciseInstructionFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ExerciseInstructionFragment newInstance(String param1) {
		ExerciseInstructionFragment fragment = new ExerciseInstructionFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public ExerciseInstructionFragment() {
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
		return (LinearLayout) inflater.inflate(R.layout.fragment_exercise_instructions, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		Log.d("instruction","before initLayout");
		initLayout();
	}
	
	private void initLayout(){
		Log.d("instruction", "this.getView(): "+this.getView());
		exerciseInstructionTitle = (TextView)this.getView().findViewById(R.id.instruction_title);
		Log.d("instruction", "exerciseInstructionTitle = "+exerciseInstructionTitle);
		exerciseInstructionTitle.setText(exerciseName);
		exerciseInstructionBody = (TextView)this.getView().findViewById(R.id.instruction_body);
//		exerciseInstructionBack = (Button)this.getView().findViewById(R.id.instruction_back);
		
		setClickListeners();
	}
	
	private void setClickListeners(){
//		exerciseInstructionBack.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				fragmentManager = getFragmentManager();
//				ExerciseInstructionFragment exerciseInstructionFragment = ExerciseInstructionFragment.newInstance(exerciseName);
//				
//				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.replace(R.id.select_exercise_fragment, exerciseInstructionFragment);
//				fragmentTransaction.commit();
//				
//			}
//		});
		
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
