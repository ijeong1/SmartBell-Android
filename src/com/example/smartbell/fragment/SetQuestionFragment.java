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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SetQuestionFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link SetQuestionFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class SetQuestionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";

	// TODO: Rename and change types of parameters
	private String exerciseName;
	private TextView setQuestionTextView;
	private EditText setQuestionEditText;
	private Button setQuestionNextButton;
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
	 * @return A new instance of fragment SetQuestionFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SetQuestionFragment newInstance(String param1) {
		SetQuestionFragment fragment = new SetQuestionFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	public SetQuestionFragment() {
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
		return (LinearLayout) inflater.inflate(R.layout.set_question, container, false);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
		setQuestionTextView = (TextView)this.getView().findViewById(R.id.set_question_text);
		setQuestionTextView.setText(exerciseName + " question");
		setQuestionEditText = (EditText)this.getView().findViewById(R.id.set_question_edittext);
		
		setQuestionNextButton = (Button)this.getView().findViewById(R.id.set_question_next_button);

		setClickListeners();
	}
	
	private void setClickListeners(){
		setQuestionNextButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				
				//logic for figuring out what question is next, or to close out set
				//if last question, close out set and go back to exercise detail
				String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
				fragmentManager.popBackStackImmediate(exerciseDetailTag, 1);
				
//				SetQuestionFragment setQuestionFragment = SetQuestionFragment.newInstance(exerciseName);
//				
//				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.replace(R.id.start_workout_activity_layout, setQuestionFragment);
//				fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
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
