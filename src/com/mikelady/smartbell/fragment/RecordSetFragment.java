package com.mikelady.smartbell.fragment;

import java.util.ArrayList;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.activity.BarPathActivity;
import com.mikelady.smartbell.activity.SelectWorkoutActivity;
import com.mikelady.smartbell.activity.StartWorkoutActivity;
import com.mikelady.smartbell.barpath.BarPathTracker;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.LiftingSetTable;
import com.mikelady.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;
import com.mikelady.smartbell.primitives.LiftingSet;
import com.mikelady.smartbell.primitives.Moment;
import com.mikelady.smartbell.sensor.SensorDataHandler;
import com.mikelady.smartbell.sensor.TSSBTSensor;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
	private static final String ARG_EXERCISE_NAME = "param1";
	private static final String ARG_WORKOUT_ID = "workoutId";
	private static final String ARG_WEIGHT = "weight";
	private static final String ARG_REPS = "targetedReps";

	// TODO: Rename and change types of parameters
	private String exerciseName;
	private int workoutId;
	private int weight;
	private int targetedReps;
	private int m_setId;
	
	private TextView recordTextView;
	private TextView repsTextView;
	private Button startSetButton;
	private Button endSetButton;
	private Button nextRepButton;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	private boolean startClicked = false;
	
	private OnFragmentInteractionListener mListener;
	private SensorDataHandler sensorDataHandler;
	private ArrayList<Long> repTimestamps;
	private boolean is_polling = false;
	
	public static ArrayList<Double[]> positions;
	
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @param param1
	 *            Parameter 1.
	 * @return A new instance of fragment RecordSetFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RecordSetFragment newInstance(String param1, int workoutId, int weight, int reps) {
		RecordSetFragment fragment = new RecordSetFragment();
		Bundle args = new Bundle();
		args.putString(ARG_EXERCISE_NAME, param1);
		args.putInt(ARG_WORKOUT_ID, workoutId);
		args.putInt(ARG_WEIGHT, weight);
		args.putInt(ARG_REPS, reps);
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
			exerciseName = getArguments().getString(ARG_EXERCISE_NAME);
			workoutId = getArguments().getInt(ARG_WORKOUT_ID);
			weight = getArguments().getInt(ARG_WEIGHT);
			targetedReps = getArguments().getInt(ARG_REPS);
			
			Log.d("RecordSetFragment", "onCreate exerciseName: "+exerciseName);
			Log.d("RecordSetFragment", "onCreate workoutId: "+workoutId);
			Log.d("RecordSetFragment", "onCreate weight: "+weight);
			Log.d("RecordSetFragment", "onCreate targetedReps: "+targetedReps);
		}
		repTimestamps = new ArrayList<Long>();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (LinearLayout) inflater.inflate(R.layout.fragment_record_set, container, false);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		initLayout();
	}
	
	private void initLayout(){
		recordTextView = (TextView)this.getView().findViewById(R.id.record_exercise_text);
		recordTextView.setText(exerciseName);
		repsTextView = (TextView)this.getView().findViewById(R.id.num_reps_text);
		startSetButton = (Button)this.getView().findViewById(R.id.start_set_button);
		endSetButton = (Button)this.getView().findViewById(R.id.end_set_button);
		nextRepButton = (Button)this.getView().findViewById(R.id.next_rep_button);
		setClickListeners();
	}
	
	private void setClickListeners(){
		
		startSetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!Moment.TEST && !startClicked){
					//1 is default
					sensorDataHandler = new SensorDataHandler(1, getActivity()); // make new thread?
					
			   		//Start the sensor updating
			        Message start_again_message = new Message();
			    	start_again_message.what = 287;
			    	sensorDataHandler.sendMessage(start_again_message);
			        is_polling = true;
			        startClicked = true;
			        startSetButton.setVisibility(View.GONE);
			        repsTextView.setVisibility(View.VISIBLE);
			        endSetButton.setVisibility(View.VISIBLE);
			        nextRepButton.setVisibility(View.VISIBLE);
			        
			        
		        }
			}
		});
		
		endSetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!Moment.TEST){
					if(is_polling)
			    	{
				    	Message stop_message = new Message();
				    	stop_message.what = -1;
				    	sensorDataHandler.sendMessage(stop_message);
				    	is_polling = false;
			    	}
					ArrayList<Moment> moments = sensorDataHandler.getMoments();
					Log.d("RecordSetFragment", "num moments: "+moments.size());
					
					// remove any noise from startup
					if(moments.size() > 3){
						moments.remove(0);
						moments.remove(0);
						moments.remove(0);
					}

					Log.d("RecordSetFragment", "addLiftingSet exerciseName: "+exerciseName);
					Log.d("RecordSetFragment", "addLiftingSet workoutId: "+workoutId);
					Log.d("RecordSetFragment", "addLiftingSet weight: "+weight);
					Log.d("RecordSetFragment", "addLiftingSet targetedReps: "+targetedReps);
					Log.d("RecordSetFragment", "addLiftingSet moments: "+moments);
					for(Moment m : moments){
						Log.d("RecordSetFragment", "addLiftingSet moment: "+m);
					}
					m_setId = addLiftingSet(exerciseName, workoutId, weight, targetedReps, moments, repTimestamps);
					
					repTimestamps.add(0, moments.get(0).getTimestamp());
					
					Log.d("RecordSetFragment","repTimestamps: "+repTimestamps);
					Log.d("RecordSetFragment","repTimestamps.size(): "+repTimestamps.size());
					for(Long timestamp: repTimestamps){
						Log.d("RecordSetFragment", "repTimestamp: "+timestamp);
					}
					
//					BarPathTracker barPathTracker = new BarPathTracker(moments);
//					positions = barPathTracker.determinePosition();
					
					fragmentManager = getFragmentManager();
					RepClassificationFragment repClassificationFragment = RepClassificationFragment.newInstance(0, m_setId, repTimestamps.size()-1, moments, repTimestamps);
					
	//				BarPathFragment barPathFragment = BarPathFragment.newInstance(moments);
	//				
					android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.start_workout_activity_layout, repClassificationFragment);
	//				fragmentTransaction.replace(R.id.start_workout_activity_layout, barPathFragment);
//					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
					
	//				String selectExerciseTag = getResources().getString(R.string.exercise_detail_tag);
	//				fragmentManager.popBackStackImmediate(selectExerciseTag, 1);
//					Intent intent = new Intent(getActivity(), BarPathActivity.class);
					
//					startActivity(intent);
				}
				else{
					Log.d("RecordSetFragment", "In test mode");
					/*ArrayList<Moment> moments = new ArrayList<Moment>();
					Float[] test = {(float) 1.0, (float) 2.0, (float) 3.0};
					Float[] test1 = {(float) 4.0, (float) 5.0, (float) 6.0};
					moments.add(new Moment((long) 10, test, test1));
					fragmentManager = getFragmentManager();
					BarPathFragment barPathFragment = BarPathFragment.newInstance(moments);
					android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
					fragmentTransaction.replace(R.id.start_workout_activity_layout, barPathFragment); //setQuestionFragment
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
					
					
					String selectExerciseTag = getResources().getString(R.string.exercise_detail_tag);*/
//					fragmentManager.popBackStackImmediate(selectExerciseTag, 1);
//					fragmentManager.popBackStackImmediate();
				}
			}
			
			protected int addLiftingSet(String exerciseName, int workoutId, int weight, int reps, ArrayList<Moment> moments, ArrayList<Long> repTimestamps) {
				Activity activity = getActivity();
				Uri addRow = Uri.parse(SmartBellContentProvider.SET_CONTENT_URI+"/lifting_set/"+0);
				ContentValues cv = new ContentValues();
				int exerciseId = LiftingSet.ExerciseId.getId(exerciseName);
				Log.d("RepClassificationFragment", "exerciseId: "+exerciseId);
				cv.put(LiftingSetTable.SET_TIMESTAMP, moments.get(0).getTimestamp());
				cv.put(LiftingSetTable.SET_EXERCISE_ID, exerciseId);
				cv.put(LiftingSetTable.SET_WORKOUT_ID, workoutId);
				cv.put(LiftingSetTable.SET_WEIGHT_LIFTED, weight);
				cv.put(LiftingSetTable.SET_TARGETED_REPS, reps);
				cv.put(LiftingSetTable.SET_ACTUAL_REPS, repTimestamps.size());
				
				Log.d("RepClassificationFragment", "activity: "+activity);
				Log.d("RepClassificationFragment", "activity.getContentResolver(): "+activity.getContentResolver());
				Log.d("RepClassificationFragment", "addRow: "+addRow);
				Log.d("RepClassificationFragment", "cv: "+cv);

				int setId = Integer.valueOf(activity.getContentResolver().insert(addRow, cv).getLastPathSegment());
				Log.d("RepClassificationFragment", "addLiftingSet: "+setId);

				return setId;
			}
			
		});
		
		nextRepButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				repTimestamps.add(System.currentTimeMillis());
			
				repsTextView.setText(""+repTimestamps.size()+" reps");
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
