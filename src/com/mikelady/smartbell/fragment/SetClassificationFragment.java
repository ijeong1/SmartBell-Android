package com.mikelady.smartbell.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.string;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.AthleteTable;
import com.mikelady.smartbell.db.table.LiftingSetTable;
import com.mikelady.smartbell.db.table.MomentTable;
import com.mikelady.smartbell.db.table.RepTable;
import com.mikelady.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;
import com.mikelady.smartbell.primitives.Athlete;
import com.mikelady.smartbell.primitives.LiftingSet;
import com.mikelady.smartbell.primitives.Moment;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SetQuestionFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link SetClassificationFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class SetClassificationFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_EXERCISE_NAME = "exerciseName";
	private static final String ARG_WORKOUT_ID = "workoutId";
	private static final String ARG_SET_ID = "setId";
	private static final String ARG_WEIGHT = "weight";
	private static final String ARG_REPS = "reps";
	private static final String ARG_CURRENT_REP = "currentRep";
	
	private static Activity activity;
	// TODO: Rename and change types of parameters
	private String exerciseName;
	private int reps;
	private static int m_setId;
	private int currentRep;
	private int workoutId;
	private static ArrayList<Moment> m_moments;
	private static ArrayList<Long> m_repTimestamps;
	private ArrayList<Integer> m_repId = new ArrayList<Integer>();
//	private TextView setQuestionTextView;
//	private EditText setQuestionEditText;

	private TextView repCategoryText;
	private CheckBox chestDownCheckBox;
	private CheckBox shouldersDownCheckBox;
	private CheckBox elbowsDownCheckBox;
	private CheckBox hipsCheckBox;
	private CheckBox barAwayCheckBox;
	private CheckBox underBarCheckBox;
	private CheckBox laybackCheckBox;
	private CheckBox wristsCheckBox;
	private CheckBox incCheckBox;
	private ArrayList<CheckBox> checkBoxes;
	private Button setClassificationContinueButton;
	private android.support.v4.app.FragmentManager fragmentManager;

	private OnFragmentInteractionListener mListener;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param exerciseName
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment SetQuestionFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SetClassificationFragment newInstance(String exerciseName, int workoutId, int weight, int reps, ArrayList<Moment> moments, ArrayList<Long> repTimestamps) {
		SetClassificationFragment fragment = new SetClassificationFragment();
		Bundle args = new Bundle();
		args.putString(ARG_EXERCISE_NAME, exerciseName);
//		args.putInt(ARG_WORKOUT_ID, workoutId);
//		args.putInt(ARG_WEIGHT, weight);
		args.putInt(ARG_REPS, reps);
		fragment.setArguments(args);
		addLiftingSet(exerciseName, workoutId, weight, reps, moments, repTimestamps);
		m_moments = moments;
		m_repTimestamps = repTimestamps;
		return fragment;
	}
	
	public static SetClassificationFragment newInstance(int currentRep, int setId, int reps, ArrayList<Moment> moments, ArrayList<Long> repTimestamps) {
		SetClassificationFragment fragment = new SetClassificationFragment();
		Bundle args = new Bundle();
		
		args.putInt(ARG_CURRENT_REP, currentRep);
		args.putInt(ARG_SET_ID, setId);
		args.putInt(ARG_REPS, reps);
		
		m_moments = moments;
		m_repTimestamps = repTimestamps;
		return fragment;
	}

	/**
	 * Method used for encapsulating the logic necessary to properly add LiftingSets
	 * to database.
	 * 
	 * @param 
	 *            
	 */
	//refactor to RecordSetFragment later?
	protected static void addLiftingSet(String exerciseName, int workoutId, int weight, int reps, ArrayList<Moment> moments, ArrayList<Long> repTimestamps) {
		Uri addRow = Uri.parse(SmartBellContentProvider.SET_CONTENT_URI+"/set/"+0);
		ContentValues cv = new ContentValues();
		int exerciseId = LiftingSet.ExerciseId.getId(exerciseName);
		Log.d("SetClassificationFragment", "exerciseId: "+exerciseId);
		cv.put(LiftingSetTable.SET_TIMESTAMP, moments.get(0).getTimestamp());
		cv.put(LiftingSetTable.SET_EXERCISE_ID, 0);
		cv.put(LiftingSetTable.SET_WORKOUT_ID, workoutId);
		cv.put(LiftingSetTable.SET_WEIGHT_LIFTED, weight);
		cv.put(LiftingSetTable.SET_REPS, reps);
		
		m_setId = Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment());
		Log.d("SetClassificationFragment", "addSet: "+m_setId);
//		athleteCursorAdapter.setOnAthleteChangeListener(null);
//		fillData();
	}
	
	public SetClassificationFragment() {
		// Required empty public constructor
		activity = getActivity();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			exerciseName = getArguments().getString(ARG_EXERCISE_NAME);
			reps = getArguments().getInt(ARG_REPS);
			currentRep = getArguments().getInt(ARG_CURRENT_REP, 0);
			workoutId = getArguments().getInt(ARG_WORKOUT_ID);
			m_setId = getArguments().getInt(ARG_SET_ID, 0);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (LinearLayout) inflater.inflate(R.layout.view_set_question, container, false);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
//		setQuestionTextView = (TextView)this.getView().findViewById(R.id.set_question_text);
//		setQuestionTextView.setText(exerciseName + " question");
//		setQuestionEditText = (EditText)this.getView().findViewById(R.id.set_question_edittext);
		repCategoryText = (TextView)this.getView().findViewById(R.id.rep_category_text);
		repCategoryText.setText(repCategoryText.getText()+""+currentRep);
		
		chestDownCheckBox = (CheckBox)this.getView().findViewById(R.id.chest_down_check_box);
		shouldersDownCheckBox = (CheckBox)this.getView().findViewById(R.id.shoulders_down_check_box);
		elbowsDownCheckBox = (CheckBox)this.getView().findViewById(R.id.elbows_down_check_box);
		hipsCheckBox = (CheckBox)this.getView().findViewById(R.id.hips_check_box);
		barAwayCheckBox = (CheckBox)this.getView().findViewById(R.id.bar_away_check_box);
		underBarCheckBox = (CheckBox)this.getView().findViewById(R.id.under_bar_check_box);
		laybackCheckBox = (CheckBox)this.getView().findViewById(R.id.layback_check_box);
		wristsCheckBox = (CheckBox)this.getView().findViewById(R.id.wrists_back_check_box);
		
		CheckBox[] checks = {chestDownCheckBox, shouldersDownCheckBox, elbowsDownCheckBox, hipsCheckBox,
				barAwayCheckBox, underBarCheckBox, laybackCheckBox, wristsCheckBox};
		checkBoxes = new ArrayList<CheckBox>(Arrays.asList(checks));
		setClassificationContinueButton = (Button)this.getView().findViewById(R.id.continue_button);

		setClickListeners();
	}
	
	private void setClickListeners(){
		setClassificationContinueButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentManager = getFragmentManager();
				
				if(currentRep < reps){
					
					String categoryString = buildCategoryString();
					addRep(categoryString, m_setId, m_moments, m_repTimestamps); 
					addMoments(m_repId.get(m_repId.size()-1), m_moments, m_repTimestamps);
					
					SetClassificationFragment setClassificationFragment = SetClassificationFragment.newInstance(currentRep, m_setId, reps, m_moments, m_repTimestamps);
					android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.start_workout_activity_layout, setClassificationFragment);
				}
				else{
					//go back to exercise detail fragment
				}
				
				
				//logic for figuring out what question is next, or to close out set
				//if last question, close out set and go back to exercise detail
//				String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
//				fragmentManager.popBackStackImmediate(exerciseDetailTag, 1);
				
//				SetQuestionFragment setQuestionFragment = SetQuestionFragment.newInstance(exerciseName);
//				
//				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.replace(R.id.start_workout_activity_layout, setQuestionFragment);
//				fragmentTransaction.addToBackStack(null);
//				fragmentTransaction.commit();
			}
			
			private void addRep(String categoryString, int setId, ArrayList<Moment> moments, ArrayList<Long> repTimestamps){
				Uri addRow = Uri.parse(SmartBellContentProvider.REP_CONTENT_URI+"/rep/"+0);
				ContentValues cv = new ContentValues();

				cv.put(RepTable.REP_TIMESTAMP, moments.get(0).getTimestamp());
				cv.put(RepTable.REP_SET_ID, setId);
				cv.put(RepTable.REP_SEQ_ID, currentRep);
				cv.put(RepTable.REP_CATEGORY, categoryString);
				
				m_repId.add(Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment()));
				Log.d("SetClassificationFragment", "addRep: "+m_repId.get(m_repId.size()-1));
			
			}
			
			private void addMoments(int repId, ArrayList<Moment> moments, ArrayList<Long> repTimestamps){
				
				for(Moment m : moments){
					
					Uri addRow = Uri.parse(SmartBellContentProvider.MOMENT_CONTENT_URI+"/moment/"+0);
					ContentValues cv = new ContentValues();
					cv.put(MomentTable.MOMENT_TIMESTAMP, moments.get(0).getTimestamp());
					cv.put(MomentTable.MOMENT_REP_ID, repId);
					cv.put(MomentTable.REP_SEQ_ID, currentRep);
					cv.put(MomentTable.REP_CATEGORY, categoryString);
					
					m_repId.add(Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment()));
					Log.d("SetClassificationFragment", "addRep: "+m_repId.get(m_repId.size()-1));
				}
				
			}
			
			private String buildCategoryString(){
				String category = "";
				
				for(CheckBox cb : checkBoxes){
					if(cb.isChecked())
					{
						category = category + cb.getText()+"-";
					}
				}
				
				return category;
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
