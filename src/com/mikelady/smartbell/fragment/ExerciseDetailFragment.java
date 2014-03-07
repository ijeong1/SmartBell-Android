package com.mikelady.smartbell.fragment;

import com.mikelady.smartbell.R;
import com.mikelady.smartbell.R.id;
import com.mikelady.smartbell.R.layout;
import com.mikelady.smartbell.R.string;
import com.mikelady.smartbell.activity.BarPathActivity;
import com.mikelady.smartbell.activity.SelectWorkoutActivity;
import com.mikelady.smartbell.db.adapter.LiftingSetCursorAdapter;
import com.mikelady.smartbell.db.adapter.LiftingSetCursorAdapter;
import com.mikelady.smartbell.db.provider.SmartBellContentProvider;
import com.mikelady.smartbell.db.table.LiftingSetTable;
import com.mikelady.smartbell.db.view.LiftingSetView.OnLiftingSetChangeListener;
import com.mikelady.smartbell.db.view.LiftingSetView;
import com.mikelady.smartbell.db.view.LiftingSetView.OnLiftingSetChangeListener;
import com.mikelady.smartbell.fragment.SelectExerciseFragment.OnFragmentInteractionListener;
import com.mikelady.smartbell.primitives.LiftingSet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link ExerciseDetailFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link ExerciseDetailFragment#newInstance}
 * factory method to create an instance of this fragment.
 * 
 */
public class ExerciseDetailFragment extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, OnLiftingSetChangeListener {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "exerciseName"; 
	private static final int LOADER_ID = 1;
	// TODO: Rename and change types of parameters
	private String exerciseName;
	private OnFragmentInteractionListener mListener;
	
	private TextView exerciseDetailTitle;
	private Button instructionsButton;
	private Button startSetButton;
	private Button endExerciseButton;
	private Button showBarpathButton;
	private android.support.v4.app.FragmentManager fragmentManager;
	
	protected ListView liftingSetListViewGroup;
	protected Cursor liftingSetCursor;
	protected LiftingSetCursorAdapter liftingSetCursorAdapter;
	
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
		return (LinearLayout) inflater.inflate(R.layout.fragment_exercise_detail, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		liftingSetCursorAdapter = new LiftingSetCursorAdapter(getActivity().getBaseContext(), null, 0);
		getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
		initLayout();
	}
	
	private void initLayout(){
		
		exerciseDetailTitle = (TextView)this.getView().findViewById(R.id.exercise_detail_title);
		exerciseDetailTitle.setText(exerciseName);
		
//		instructionsButton = (Button)this.getView().findViewById(R.id.instructions_button);
		startSetButton = (Button)this.getView().findViewById(R.id.start_set_button);
		endExerciseButton = (Button)this.getView().findViewById(R.id.end_exercise_button);
//		showBarpathButton = (Button)this.getView().findViewById(R.id.bar_path_button);
		
		liftingSetListViewGroup = (ListView)this.getView().findViewById(R.id.lifting_set_list_view_group);
		liftingSetCursorAdapter.setOnLiftingSetChangeListener(this);
		liftingSetListViewGroup.setAdapter(liftingSetCursorAdapter);
		setClickListeners();
	}
	
	private void setClickListeners(){
//		instructionsButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				fragmentManager = getFragmentManager();
//				ExerciseInstructionFragment exerciseInstructionFragment = ExerciseInstructionFragment.newInstance(exerciseName);
//				
//				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//				fragmentTransaction.replace(R.id.start_workout_activity_layout, exerciseInstructionFragment);
//				String exerciseDetailTag = getResources().getString(R.string.exercise_detail_tag);
//				fragmentTransaction.addToBackStack(exerciseDetailTag);
//				fragmentTransaction.commit();
//				Log.d("exerciseDetailFragment", "after instruction fragment commit");
//			}
//		});
		
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
		
//		showBarpathButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				fragmentManager = getFragmentManager();
////				BarPathFragment barPathFragment = BarPathFragment.newInstance();
////				
////				android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////				fragmentTransaction.replace(R.id.start_workout_activity_layout, barPathFragment);
////				String barPathTag = getResources().getString(R.string.bar_path_tag);
////				fragmentTransaction.addToBackStack(barPathTag);
////				fragmentTransaction.commit();
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
	
	protected void addLiftingSet(LiftingSet liftingSet){
		Uri addRow = Uri.parse(SmartBellContentProvider.SET_CONTENT_URI+"/liftingSet/"+liftingSet.getId());
		ContentValues cv = new ContentValues();
//		cv.put(LiftingSetTable.SET_KEY_ID, liftingSet.getId());
		cv.put(LiftingSetTable.SET_TIMESTAMP, liftingSet.getTimestamp());
		cv.put(LiftingSetTable.SET_EXERCISE_ID, 0);
		cv.put(LiftingSetTable.SET_WEIGHT_LIFTED, 0);
		cv.put(LiftingSetTable.SET_REPS, 0);
		cv.put(LiftingSetTable.SET_WORKOUT_ID, 0);
		

		liftingSet.setId(Integer.valueOf(getActivity().getContentResolver().insert(addRow, cv).getLastPathSegment()));
		Log.d("mlady", "liftingSet: "+liftingSet);
		liftingSetCursorAdapter.setOnLiftingSetChangeListener(null);
		fillData();
	}
	
	protected void fillData(){
		getActivity().getSupportLoaderManager().restartLoader(0, null, this);
		this.liftingSetListViewGroup.setAdapter(liftingSetCursorAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] proj = new String[LiftingSetTable.SET_COL_NAMES.length];
		proj[LiftingSetTable.SET_COL_ID] = LiftingSetTable.SET_KEY_ID;
		proj[LiftingSetTable.SET_COL_EXERCISE_ID] = LiftingSetTable.SET_EXERCISE_ID;
		proj[LiftingSetTable.SET_COL_REPS] = LiftingSetTable.SET_REPS;
		proj[LiftingSetTable.SET_COL_TIMESTAMP] = LiftingSetTable.SET_TIMESTAMP;
		proj[LiftingSetTable.SET_COL_WEIGHT_LIFTED] = LiftingSetTable.SET_WEIGHT_LIFTED;
		proj[LiftingSetTable.SET_COL_WORKOUT_ID] = LiftingSetTable.SET_WORKOUT_ID;

		Uri m_uri = Uri.parse(SmartBellContentProvider.SET_CONTENT_URI + "/lifting_set/");
		
		CursorLoader cl = new CursorLoader(getActivity().getBaseContext(), m_uri, proj, null, null, null);
		
		return cl;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		liftingSetCursorAdapter.swapCursor(arg1);
		Log.d("ExerciseDetailFragment","liftingSetListViewGroup "+liftingSetListViewGroup);
		Log.d("ExerciseDetailFragment","liftingSetCursorAdapter "+liftingSetCursorAdapter);
		
		this.liftingSetListViewGroup.setAdapter(liftingSetCursorAdapter);
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		liftingSetCursorAdapter.swapCursor(null);
	}

	@Override
	public void onLiftingSetChanged(LiftingSetView view, LiftingSet liftingSet) {
		
        Intent intent = new Intent(getActivity(), BarPathActivity.class);
        intent.putExtra(getString(R.string.lifting_set_id), liftingSet.getId());
        startActivity(intent);
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
