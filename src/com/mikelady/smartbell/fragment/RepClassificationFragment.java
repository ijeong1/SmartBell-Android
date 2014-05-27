package com.mikelady.smartbell.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.string;
import com.mikelady.smartbell.db.SmartBellDatabaseHelper;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.AthleteTable;
import com.mikelady.smartbell.db.table.LiftingSetTable;
import com.mikelady.smartbell.db.table.MomentTable;
import com.mikelady.smartbell.db.table.RepTable;
import com.mikelady.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;
import com.mikelady.smartbell.primitives.Athlete;
import com.mikelady.smartbell.primitives.LiftingSet;
import com.mikelady.smartbell.primitives.Moment;
import com.parse.ParseFile;
import com.parse.ParseObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
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
import org.apache.commons.io.FileUtils.*;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SetQuestionFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link RepClassificationFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class RepClassificationFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_EXERCISE_NAME = "exerciseName";
	private static final String ARG_SET_ID = "setId";
	private static final String ARG_WEIGHT = "weight";
	private static final String ARG_ACTUAL_REPS = "actualReps";
	private static final String ARG_CURRENT_REP = "currentRep";
	
	private static Activity activity;
	// TODO: Rename and change types of parameters
	private int actualReps;
	private static int m_setId;
	private int currentRep;
	private int workoutId;
	private String exerciseName;
	private static ArrayList<Moment> m_moments;
	private static ArrayList<Long> m_repTimestamps;
	private ArrayList<Integer> m_repIds = new ArrayList<Integer>();
//	private TextView setQuestionTextView;
//	private EditText setQuestionEditText;

	
	//the following is terrible
	
	//overhead press checkboxes
	private TextView repCategoryText;
	private CheckBox pressCorrectCheckBox;
	private CheckBox chestDownCheckBox;
//	private CheckBox shouldersDownCheckBox;
//	private CheckBox elbowsDownCheckBox;
	private CheckBox hipsCheckBox;
	private CheckBox barAwayCheckBox;
	private CheckBox underBarCheckBox;
	private CheckBox laybackCheckBox;
	private CheckBox wristsCheckBox;
	private CheckBox jerkyCheckBox;
	private CheckBox elbowsCheckBox;
	private CheckBox incCheckBox;
	private CheckBox pressGoldenCheckBox;
	
	//squat checkboxes
	private CheckBox squatCorrectCheckBox;
	private CheckBox chinTuckCheckBox;
	private CheckBox upperBackRoundCheckBox;
	private CheckBox lowerBackRoundCheckBox;
	private CheckBox overExtensionCheckBox;
	private CheckBox chaseBackCheckBox;
	private CheckBox hipRollUnderCheckBox;
	private CheckBox kneesSpreadCheckBox;
	private CheckBox heelsCheckBox;
	private CheckBox notParallelCheckBox;
	private CheckBox standUpCheckBox;
	private CheckBox wristsSquatCheckBox;
	private CheckBox incSquatCheckBox;
	private CheckBox squatGoldenCheckBox;
	
	//bench checkboxes
	private CheckBox benchCorrectCheckBox;
	private CheckBox upperBackTightCheckBox;
	private CheckBox glutesCheckBox;
	private CheckBox lowerBackArchCheckBox;
	private CheckBox chestBounceCheckBox;
	private CheckBox RangeOfMotionCheckBox;
	private CheckBox wristsBenchCheckBox;
	private CheckBox incBenchCheckBox;
	private CheckBox benchGoldenCheckBox;
	
	private ArrayList<CheckBox> checkBoxes;
	private Button pressContinueButton;
	private Button squatContinueButton;
	private Button benchContinueButton;
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
	
	public static RepClassificationFragment newInstance(String exerciseName, int currentRep, int setId, int actualReps, ArrayList<Moment> moments, ArrayList<Long> repTimestamps) {
		RepClassificationFragment fragment = new RepClassificationFragment();
		Bundle args = new Bundle();
		args.putString(ARG_EXERCISE_NAME, exerciseName);
		args.putInt(ARG_CURRENT_REP, currentRep);
		args.putInt(ARG_SET_ID, setId);
		args.putInt(ARG_ACTUAL_REPS, actualReps);
		m_moments = moments;
		m_repTimestamps = repTimestamps;
		fragment.setArguments(args);
		Log.d("RepClassificationFragment", "newInstance exerciseName: "+exerciseName);
		Log.d("RepClassificationFragment", "newInstance currentRep: "+currentRep);
		Log.d("RepClassificationFragment", "newInstance setId: "+setId);
		Log.d("RepClassificationFragment", "newInstance actualReps: "+actualReps);
//		Log.d("RepClassificationFragment", "newInstance m_moments: "+m_moments);
		Log.d("RepClassificationFragment", "newInstance m_repTimestamps: "+m_repTimestamps);
		
		return fragment;
	}

	/**
	 * Method used for encapsulating the logic necessary to properly add LiftingSets
	 * to database.
	 * 
	 * @param 
	 *            
	 */
	
	public RepClassificationFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d("RepClassificationFragment","onStart getArguments(): "+getArguments());
		if (getArguments() != null) {
			exerciseName = getArguments().getString(ARG_EXERCISE_NAME);
			actualReps = getArguments().getInt(ARG_ACTUAL_REPS);
			currentRep = getArguments().getInt(ARG_CURRENT_REP);
			workoutId = getArguments().getInt(ARG_SET_ID);
			m_setId = getArguments().getInt(ARG_SET_ID);
			Log.d("RepClassificationFragment","onStart exerciseName: "+exerciseName);
			Log.d("RepClassificationFragment","onStart actualReps: "+actualReps);
			Log.d("RepClassificationFragment","onStart currentRep: "+currentRep);
			Log.d("RepClassificationFragment","onStart workoutId: "+workoutId);
			Log.d("RepClassificationFragment","onStart m_setId: "+m_setId);
			
		}
		if(exerciseName.contains("Squat")){
			return (LinearLayout) inflater.inflate(R.layout.fragment_rep_classification_squat, container, false);
		}
		else if(exerciseName.contains("Bench")){
			return (LinearLayout) inflater.inflate(R.layout.fragment_rep_classification_bench, container, false);
		}
		else{
			return (LinearLayout) inflater.inflate(R.layout.fragment_rep_classification_press, container, false);
		}
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
//		setQuestionTextView = (TextView)this.getView().findViewById(R.id.set_question_text);
//		setQuestionTextView.setText(exerciseName + " question");
//		setQuestionEditText = (EditText)this.getView().findViewById(R.id.set_question_edittext);
		repCategoryText = (TextView)this.getView().findViewById(R.id.rep_category_text);
		Log.d("RepClassificationFragment", "initLayout currentRep: "+currentRep);
		Log.d("RepClassificationFragment", "initLayout repCategoryText: "+repCategoryText);
		Log.d("RepClassificationFragment", "initLayout repCategoryText.getText(): "+repCategoryText.getText());
		repCategoryText.setText(repCategoryText.getText()+""+(currentRep+1)+" of "+actualReps);
		
		if(exerciseName.contains("Squat")){
			initLayoutSquat();
		}
		else if(exerciseName.contains("Bench")){
			initLayoutBench();
		}
		else{
			initLayoutPress();
		}
		
	}
	
	private void initLayoutBench(){
		benchGoldenCheckBox = (CheckBox)this.getView().findViewById(R.id.golden_bench);
		benchCorrectCheckBox = (CheckBox)this.getView().findViewById(R.id.correct_check_box_bench);
		upperBackTightCheckBox = (CheckBox)this.getView().findViewById(R.id.upper_back_tight_check_box);
		glutesCheckBox = (CheckBox)this.getView().findViewById(R.id.glutes_check_box_bench);
		lowerBackArchCheckBox = (CheckBox)this.getView().findViewById(R.id.arch_check_box_bench);
		chestBounceCheckBox = (CheckBox)this.getView().findViewById(R.id.chest_bounce_check_box_bench);
		wristsBenchCheckBox = (CheckBox)this.getView().findViewById(R.id.wrists_back_check_box_bench);
		incBenchCheckBox = (CheckBox)this.getView().findViewById(R.id.inc_check_box_bench);
		
		CheckBox[] checks = {benchGoldenCheckBox, benchCorrectCheckBox, upperBackTightCheckBox, glutesCheckBox, lowerBackArchCheckBox,
				chestBounceCheckBox, wristsBenchCheckBox, incBenchCheckBox};
		checkBoxes = new ArrayList<CheckBox>(Arrays.asList(checks));
		benchContinueButton = (Button)this.getView().findViewById(R.id.continue_button_bench);

		
		setClickListenerBench();
	}
	
	private void setClickListenerBench(){
		benchContinueButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleFragmentLogic();
			}
		});
	}
	
	private void initLayoutSquat(){
		squatGoldenCheckBox = (CheckBox)this.getView().findViewById(R.id.golden_squat);
		squatCorrectCheckBox = (CheckBox)this.getView().findViewById(R.id.correct_check_box_squat);
	
		upperBackRoundCheckBox = (CheckBox)this.getView().findViewById(R.id.upper_back_round_check_box);
//		shouldersDownCheckBox = (CheckBox)this.getView().findViewById(R.id.shoulders_down_check_box);
//		elbowsDownCheckBox = (CheckBox)this.getView().findViewById(R.id.elbows_down_check_box);
		chinTuckCheckBox = (CheckBox)this.getView().findViewById(R.id.chin_tuck);
		lowerBackRoundCheckBox = (CheckBox)this.getView().findViewById(R.id.lower_back_round_check_box);
		overExtensionCheckBox = (CheckBox)this.getView().findViewById(R.id.over_extension_check_box);
		chaseBackCheckBox = (CheckBox)this.getView().findViewById(R.id.chase_back_check_box);
		hipRollUnderCheckBox = (CheckBox)this.getView().findViewById(R.id.hips_roll_check_box);
		kneesSpreadCheckBox = (CheckBox)this.getView().findViewById(R.id.knees_out_check_box);
		notParallelCheckBox =  (CheckBox)this.getView().findViewById(R.id.not_parallel_check_box);
		standUpCheckBox =  (CheckBox)this.getView().findViewById(R.id.stand_up_check_box);
		heelsCheckBox = (CheckBox)this.getView().findViewById(R.id.heels_up_check_box);
		wristsSquatCheckBox = (CheckBox)this.getView().findViewById(R.id.wrists_roll_check_box_squat);
		incSquatCheckBox = (CheckBox)this.getView().findViewById(R.id.incomplete_check_box_squat);

		CheckBox[] checks = {squatGoldenCheckBox, squatCorrectCheckBox, chinTuckCheckBox, upperBackRoundCheckBox, lowerBackRoundCheckBox, overExtensionCheckBox,
				chaseBackCheckBox, hipRollUnderCheckBox, notParallelCheckBox, standUpCheckBox, kneesSpreadCheckBox, heelsCheckBox, wristsSquatCheckBox, incSquatCheckBox};
		checkBoxes = new ArrayList<CheckBox>(Arrays.asList(checks));
		squatContinueButton = (Button)this.getView().findViewById(R.id.continue_button_squat);

		setClickListenerSquat();
	}
	
	private void setClickListenerSquat(){
		squatContinueButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleFragmentLogic();
			
			}
		});
	}
	
	private void initLayoutPress(){

		pressGoldenCheckBox = (CheckBox)this.getView().findViewById(R.id.golden_press);
		pressCorrectCheckBox = (CheckBox)this.getView().findViewById(R.id.correct_check_box_press);
	
		chestDownCheckBox = (CheckBox)this.getView().findViewById(R.id.chest_down_check_box);
//		shouldersDownCheckBox = (CheckBox)this.getView().findViewById(R.id.shoulders_down_check_box);
//		elbowsDownCheckBox = (CheckBox)this.getView().findViewById(R.id.elbows_down_check_box);
		
		hipsCheckBox = (CheckBox)this.getView().findViewById(R.id.hips_check_box);
		barAwayCheckBox = (CheckBox)this.getView().findViewById(R.id.bar_away_check_box);
		underBarCheckBox = (CheckBox)this.getView().findViewById(R.id.under_bar_check_box);
		laybackCheckBox = (CheckBox)this.getView().findViewById(R.id.layback_check_box);
		wristsCheckBox = (CheckBox)this.getView().findViewById(R.id.wrists_back_check_box);
		jerkyCheckBox = (CheckBox)this.getView().findViewById(R.id.jerky_check_box);
		elbowsCheckBox = (CheckBox)this.getView().findViewById(R.id.elbows_out_check_box);
		incCheckBox = (CheckBox)this.getView().findViewById(R.id.inc_check_box);
		CheckBox[] checks = {pressGoldenCheckBox, pressCorrectCheckBox, chestDownCheckBox, /*shouldersDownCheckBox, elbowsDownCheckBox,*/ hipsCheckBox,
				barAwayCheckBox, underBarCheckBox, laybackCheckBox, wristsCheckBox, jerkyCheckBox, elbowsCheckBox, incCheckBox};
		checkBoxes = new ArrayList<CheckBox>(Arrays.asList(checks));
		pressContinueButton = (Button)this.getView().findViewById(R.id.continue_button);

		setClickListenerPress();
	}
	
	private void setClickListenerPress(){
		pressContinueButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				handleFragmentLogic();

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
			

		
		});
		
	}
	
	private void handleFragmentLogic(){
		fragmentManager = getFragmentManager();
		
		
		String categoryString = buildCategoryString();
		Log.d("RepClassificationFragment:handleFragmentLogic()", "addRep categoryString: "+categoryString);
		Log.d("RepClassificationFragment:handleFragmentLogic()", "addRep m_setId: "+m_setId);
		
		int repId = addRep(categoryString, m_setId, m_moments, m_repTimestamps);
		m_repIds.add(repId); 
		
		addMoments(m_repIds.get(m_repIds.size()-1), currentRep, m_moments, m_repTimestamps);
		
		String setClassificationTag = getResources().getString(R.string.set_classification_tag) + currentRep;
		currentRep++;
		Log.d("RepClassificationFragment:handleFragmentLogic()","continueButton currentRep:"+currentRep);
		
		if(currentRep < actualReps){
			Log.d("RepClassificationFragment:handleFragmentLogic()", "currentRep < actualReps");
			RepClassificationFragment repClassificationFragment = RepClassificationFragment.newInstance(exerciseName, currentRep, m_setId, actualReps, m_moments, m_repTimestamps);
			android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

			fragmentTransaction.replace(R.id.start_workout_activity_layout, repClassificationFragment); //replace
			fragmentTransaction.addToBackStack(setClassificationTag);
			fragmentTransaction.commit();
		}
		else{

			Log.d("RepClassificationFragment:handleFragmentLogic()", "getActivity(): "+getActivity());
			Log.d("RepClassificationFragment:handleFragmentLogic()", "activity: "+activity);
			Log.d("RepClassificationFragment:handleFragmentLogic()", "activity.getFilesDir(): "+activity.getFilesDir());
			Log.d("RepClassificationFragment:handleFragmentLogic()", "activity.getFilesDir().getPath(): "+activity.getFilesDir().getPath());
			Log.d("RepClassificationFragment:handleFragmentLogic()", "db path: "+activity.getFilesDir().getPath()+"/"+SmartBellDatabaseHelper.DATABASE_NAME);
			File databaseFile = activity.getDatabasePath(SmartBellDatabaseHelper.DATABASE_NAME);
			byte[] databaseBytes = null;
			try {
				databaseBytes = org.apache.commons.io.FileUtils.readFileToByteArray(databaseFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Log.d("RepClassificationFragment:handleFragmentLogic()","databaseBytes: "+databaseBytes);
			ParseFile databaseParseFile = new ParseFile("database.sqlite",databaseBytes);
			ParseObject databaseObject = new ParseObject("database");
			databaseObject.put("database", databaseParseFile);
			databaseObject.saveInBackground();
			Log.d("RepClassificationFragment:handleFragmentLogic()","saved database to parse");
			
			getActivity().finish();
			
			//go back to exercise detail fragment
//			String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
//			fragmentManager.popBackStackImmediate(exerciseDetailTag, 1);
			
			
			
			
		}
	}
	
	private int addRep(String categoryString, int setId, ArrayList<Moment> moments, ArrayList<Long> repTimestamps){
		activity = getActivity();
		Uri addRow = Uri.parse(SmartBellContentProvider.REP_CONTENT_URI+"/rep/"+0);
		ContentValues cv = new ContentValues();

		cv.put(RepTable.REP_TIMESTAMP, repTimestamps.get(currentRep));
		cv.put(RepTable.REP_SET_ID, setId);
		cv.put(RepTable.REP_SEQ_ID, currentRep);
		cv.put(RepTable.REP_CATEGORY, categoryString);
		int repId = Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment());
		Log.d("RepClassificationFragment", "added repId: "+repId);
		return repId;
	}
	
	private void addMoments(int repId, int currentRep, ArrayList<Moment> moments, ArrayList<Long> repTimestamps){
		activity = getActivity();
		for(Moment m : moments){
			//if on first n-1 reps, add moments inbetween rep timestamps
			// --DON'T NEED THIS PART I THINK otherwise, on last rep, add all moments greater than last rep's timestamp 
			/*|| (currentRep == repTimestamps.size() - 1 
			&& m.getTimestamp() >= repTimestamps.get(currentRep))*/
			
			if( (currentRep < repTimestamps.size() - 1 
					&& m.getTimestamp() >= repTimestamps.get(currentRep) 
					&& m.getTimestamp() < repTimestamps.get(currentRep+1)) ){
				
				Uri addRow = Uri.parse(SmartBellContentProvider.MOMENT_CONTENT_URI+"/moment/"+0);
				ContentValues cv = new ContentValues();
				cv.put(MomentTable.MOMENT_TIMESTAMP, m.getTimestamp());
				cv.put(MomentTable.MOMENT_REP_ID, repId);
				cv.put(MomentTable.MOMENT_QUAT_W, m.getQuat()[Moment.qW]);
				cv.put(MomentTable.MOMENT_QUAT_X, m.getQuat()[Moment.qX]);
				cv.put(MomentTable.MOMENT_QUAT_Y, m.getQuat()[Moment.qY]);
				cv.put(MomentTable.MOMENT_QUAT_Z, m.getQuat()[Moment.qZ]);
				
				cv.put(MomentTable.MOMENT_LINACC_X, m.getLinAcc()[Moment.X]);
				cv.put(MomentTable.MOMENT_LINACC_Y, m.getLinAcc()[Moment.Y]);
				cv.put(MomentTable.MOMENT_LINACC_Z, m.getLinAcc()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_CORRECTED_GYRO_X, m.getCorrectedGyro()[Moment.X]);
				cv.put(MomentTable.MOMENT_CORRECTED_GYRO_Y, m.getCorrectedGyro()[Moment.Y]);
				cv.put(MomentTable.MOMENT_CORRECTED_GYRO_Z, m.getCorrectedGyro()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_CORRECTED_ACC_X, m.getCorrectedAcc()[Moment.X]);
				cv.put(MomentTable.MOMENT_CORRECTED_ACC_Y, m.getCorrectedAcc()[Moment.Y]);
				cv.put(MomentTable.MOMENT_CORRECTED_ACC_Z, m.getCorrectedAcc()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_CORRECTED_COMPASS_X, m.getCorrectedCompass()[Moment.X]);
				cv.put(MomentTable.MOMENT_CORRECTED_COMPASS_Y, m.getCorrectedCompass()[Moment.Y]);
				cv.put(MomentTable.MOMENT_CORRECTED_COMPASS_Z, m.getCorrectedCompass()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_RAW_GYRO_X, m.getRawGyro()[Moment.X]);
				cv.put(MomentTable.MOMENT_RAW_GYRO_Y, m.getRawGyro()[Moment.Y]);
				cv.put(MomentTable.MOMENT_RAW_GYRO_Z, m.getRawGyro()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_RAW_ACC_X, m.getRawAcc()[Moment.X]);
				cv.put(MomentTable.MOMENT_RAW_ACC_Y, m.getRawAcc()[Moment.Y]);
				cv.put(MomentTable.MOMENT_RAW_ACC_Z, m.getRawAcc()[Moment.Z]);
				
				cv.put(MomentTable.MOMENT_RAW_COMPASS_X, m.getRawCompass()[Moment.X]);
				cv.put(MomentTable.MOMENT_RAW_COMPASS_Y, m.getRawCompass()[Moment.Y]);
				cv.put(MomentTable.MOMENT_RAW_COMPASS_Z, m.getRawCompass()[Moment.Z]);
				
				m_repIds.add(Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment()));
				Log.d("RepClassificationFragment", "addMoment1: "+m_repIds.get(m_repIds.size()-1));
			}
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
