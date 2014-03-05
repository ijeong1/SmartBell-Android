package com.mikelady.smartbell.db.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.mikelady.smartbell.db.SmartBellDatabaseHelper;
import com.mikelady.smartbell.db.table.AthleteTable;
import com.mikelady.smartbell.db.table.MomentTable;
import com.mikelady.smartbell.db.table.SetTable;
import com.mikelady.smartbell.db.table.WorkoutTable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**
 * Class that provides content from a SQLite database to the application.
 * Provides athlete information to a ListView through a CursorAdapter. The database stores
 * athletes in a two-dimensional table, where each row is a athlete and each column is a property
 * of a athlete (ID, athlete text, athlete rating, athlete author).
 * 
 * Note that CursorLoaders require a ContentProvider, which is why this application wraps a
 * SQLite database into a content provider instead of managing the database<-->application
 * transactions manually.
 */
public class SmartBellContentProvider extends ContentProvider {

	/** The athlete database. */
	private SmartBellDatabaseHelper database;

	/** Values for the URIMatcher. */ 
	private static final int ATHLETE_ID = 1;
	private static final int ATHLETE_ALL = 2;
	private static final int WORKOUT_ID = 3;
	private static final int WORKOUT_ALL = 4;
	private static final int SET_ID = 5;
	private static final int SET_ALL = 6;
	private static final int MOMENT_ID = 7;
	private static final int MOMENT_ALL = 8;

	/** The authority for this content provider. */
	private static final String AUTHORITY = "com.mikelady.smarbell.smartbellcontentprovider";

	/** The database table to read from and write to, and also the root path for use in the URI matcher.
	 * This is essentially a label to a two-dimensional array in the database filled with rows of athletes
	 * whose columns contain athlete data. */
	private static final String ATHLETE_BASE_PATH = "athlete_table";
	private static final String WORKOUT_BASE_PATH = "workout_table";
	private static final String SET_BASE_PATH = "set_table";
	private static final String MOMENT_BASE_PATH = "moment_table";

	/** This provider's content location. Used by accessing applications to
	 * interact with this provider. */
	public static final Uri ATHLETE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ATHLETE_BASE_PATH);
	public static final Uri WORKOUT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + WORKOUT_BASE_PATH);
	public static final Uri SET_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + SET_BASE_PATH);
	public static final Uri MOMENT_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MOMENT_BASE_PATH);

	/** Matches content URIs requested by accessing applications with possible
	 * expected content URI formats to take specific actions in this provider. */
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, ATHLETE_BASE_PATH + "/athlete", ATHLETE_ALL);
		sURIMatcher.addURI(AUTHORITY, ATHLETE_BASE_PATH + "/athlete/#", ATHLETE_ID);
		sURIMatcher.addURI(AUTHORITY, WORKOUT_BASE_PATH + "/workout", WORKOUT_ALL);
		sURIMatcher.addURI(AUTHORITY, WORKOUT_BASE_PATH + "/workout/#", WORKOUT_ID);
		sURIMatcher.addURI(AUTHORITY, SET_BASE_PATH + "/set", SET_ALL);
		sURIMatcher.addURI(AUTHORITY, SET_BASE_PATH + "/set/#", SET_ID);
		sURIMatcher.addURI(AUTHORITY, MOMENT_BASE_PATH + "/moment", MOMENT_ALL);
		sURIMatcher.addURI(AUTHORITY, MOMENT_BASE_PATH + "/moment/#", MOMENT_ID);
	}

	@Override
	public boolean onCreate() {
		database = new SmartBellDatabaseHelper(this.getContext(), SmartBellDatabaseHelper.DATABASE_NAME, null, SmartBellDatabaseHelper.DATABASE_VERSION);
		return true;
	}

	/**
	 * Fetches rows from the athlete table. Given a specified URI that contains a
	 * filter, returns a list of athletes from the athlete table matching that filter in the
	 * form of a Cursor.<br><br>
	 * 
	 * Overrides the built-in version of <b>query(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>query(...)</b> in the Overrides details.
	 * */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		/** Use a helper class to perform a query for us. */
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		/** Make sure the projection is proper before querying. */
		checkColumns(projection);

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURIMatcher.match(uri);
		String id = uri.getLastPathSegment();
		
		switch(uriType) {
		case ATHLETE_ALL:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(AthleteTable.DATABASE_TABLE_ATHLETE);
			break;
			
		case ATHLETE_ID:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(AthleteTable.DATABASE_TABLE_ATHLETE);
			queryBuilder.appendWhere(AthleteTable.ATHLETE_KEY_ID + "=" + id);
			break;

		case WORKOUT_ALL:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(WorkoutTable.DATABASE_TABLE_WORKOUT);
			break;
			
		case WORKOUT_ID:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(WorkoutTable.DATABASE_TABLE_WORKOUT);
			queryBuilder.appendWhere(WorkoutTable.WORKOUT_KEY_ID + "=" + id);
			break;
			
		case SET_ALL:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(SetTable.DATABASE_TABLE_SET);
			break;
			
		case SET_ID:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(SetTable.DATABASE_TABLE_SET);
			queryBuilder.appendWhere(SetTable.SET_KEY_ID + "=" + id);
			break;
			
		case MOMENT_ALL:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(MomentTable.DATABASE_TABLE_MOMENT);
			break;
			
		case MOMENT_ID:
			/** Set up helper to query our athletes table. */
			queryBuilder.setTables(SetTable.DATABASE_TABLE_SET);
			queryBuilder.appendWhere(SetTable.SET_KEY_ID + "=" + id);
			break;
			

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		
		/** Perform the database query. */
		SQLiteDatabase db = this.database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, null, null, null, null);

		/** Set the cursor to automatically alert listeners for content/view refreshing. */
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	/** We don't really care about this method for this application. */
	@Override
	public String getType(Uri uri) {
		return null;
	}

	/**
	 * Inserts a athlete into the athlete table. Given a specific URI that contains a
	 * athlete and the values of that athlete, writes a new row in the table filled
	 * with that athlete's information and gives the athlete a new ID, then returns a URI
	 * containing the ID of the inserted athlete.<br><br>
	 * 
	 * Overrides the built-in version of <b>insert(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>insert(...)</b> in the Overrides details.
	 * */
	@Override
	public Uri insert(Uri uri, ContentValues values) {

		/** Open the database for writing. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();

		/** Will contain the ID of the inserted athlete. */
		long id = 0;

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURIMatcher.match(uri);

		String basePath = "";
		
		switch(uriType)	{

		/** Expects a athlete ID, but we will do nothing with the passed-in ID since
		 * the database will automatically handle ID assignment and incrementation.
		 * IMPORTANT: athlete ID cannot be set to -1 in passed-in URI; -1 is not interpreted
		 * as a numerical value by the URIMatcher. */
		case ATHLETE_ID:

			/** Perform the database insert, placing the athlete at the bottom of the table. */
			id = sqlDB.insert(AthleteTable.DATABASE_TABLE_ATHLETE, null, values);
			basePath = ATHLETE_BASE_PATH;
			break;

		case WORKOUT_ID:
			/** Perform the database insert, placing the workout at the bottom of the table. */
			id = sqlDB.insert(WorkoutTable.DATABASE_TABLE_WORKOUT, null, values);
			basePath = WORKOUT_BASE_PATH;
			break;

		case SET_ID:
			/** Perform the database insert, placing the set at the bottom of the table. */
			id = sqlDB.insert(SetTable.DATABASE_TABLE_SET, null, values);
			basePath = SET_BASE_PATH;
			break;
			
		case MOMENT_ID:
			/** Perform the database insert, placing the moment at the bottom of the table. */
			id = sqlDB.insert(MomentTable.DATABASE_TABLE_MOMENT, null, values);
			basePath = MOMENT_BASE_PATH;
			break;
			
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		/** Alert any watchers of an underlying data change for content/view refreshing. */
		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(basePath + "/" + id);
	}

	/**
	 * Removes a row from the athlete table. Given a specific URI containing a athlete ID,
	 * removes rows in the table that match the ID and returns the number of rows removed.
	 * Since IDs are automatically incremented on insertion, this will only ever remove
	 * a single row from the athlete table.<br><br>
	 * 
	 * Overrides the built-in version of <b>delete(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>delete(...)</b> in the Overrides details.
	 * */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		/** Open the database for writing. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		
		int rowsDeleted = 0;
		
		int uriType = sURIMatcher.match(uri);
		String id = uri.getLastPathSegment();

		switch(uriType)	{
		case ATHLETE_ID:
			rowsDeleted = sqlDB.delete(AthleteTable.DATABASE_TABLE_ATHLETE, AthleteTable.ATHLETE_KEY_ID+"="+id, null);
			break;
			
		case WORKOUT_ID:
			rowsDeleted = sqlDB.delete(WorkoutTable.DATABASE_TABLE_WORKOUT, WorkoutTable.WORKOUT_KEY_ID+"="+id, null);
			break;
			
		case SET_ID:
			rowsDeleted = sqlDB.delete(SetTable.DATABASE_TABLE_SET, SetTable.SET_KEY_ID+"="+id, null);
			break;
			
		case MOMENT_ID:
			rowsDeleted = sqlDB.delete(MomentTable.DATABASE_TABLE_MOMENT, MomentTable.MOMENT_KEY_ID+"="+id, null);
			break;	
		}

		if(rowsDeleted > 0)
			getContext().getContentResolver().notifyChange(uri, null);
		
		return rowsDeleted;
	}

	/**
	 * Updates a row in the athlete table. Given a specific URI containing a athlete ID and the
	 * new athlete values, updates the values in the row with the matching ID in the table. 
	 * Since IDs are automatically incremented on insertion, this will only ever update
	 * a single row in the athlete table.<br><br>
	 * 
	 * Overrides the built-in version of <b>update(...)</b> provided by ContentProvider.<br><br>
	 * 
	 * For more information, read the documentation for the built-in version of this
	 * method by hovering over the method name in the method signature below this
	 * comment block in Eclipse and clicking <b>update(...)</b> in the Overrides details.
	 * */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		/** Open the database for writing. */
		SQLiteDatabase sqlDB = this.database.getWritableDatabase();
		
		int rowsUpdated = 0;
		
		int uriType = sURIMatcher.match(uri);
		String id = uri.getLastPathSegment();
		
		switch(uriType)	{
		case ATHLETE_ID:

//			Log.d("mlady","id: "+id);
//			Log.d("mlady","values: "+values);
			rowsUpdated = sqlDB.update(AthleteTable.DATABASE_TABLE_ATHLETE, values, AthleteTable.ATHLETE_KEY_ID+"="+id, null);
			break;
			
		case WORKOUT_ID:
//			Log.d("mlady","id: "+id);
//			Log.d("mlady","values: "+values);
			rowsUpdated = sqlDB.update(WorkoutTable.DATABASE_TABLE_WORKOUT, values, WorkoutTable.WORKOUT_KEY_ID+"="+id, null);
			break;
			
		case SET_ID:
//			Log.d("mlady","id: "+id);
//			Log.d("mlady","values: "+values);
			rowsUpdated = sqlDB.update(SetTable.DATABASE_TABLE_SET, values, SetTable.SET_KEY_ID+"="+id, null);
			break;
			
		case MOMENT_ID:
//			Log.d("mlady","id: "+id);
//			Log.d("mlady","values: "+values);
			rowsUpdated = sqlDB.update(MomentTable.DATABASE_TABLE_MOMENT, values, MomentTable.MOMENT_KEY_ID+"="+id, null);
			break;
			
		}
		
		if(rowsUpdated > 0)
			getContext().getContentResolver().notifyChange(uri, null);
		
		return rowsUpdated;
	}

	/**
	 * Verifies the correct set of columns to return data from when performing a query.
	 * 
	 * @param projection
	 * 						The set of columns about to be queried.
	 */
	private void checkColumns(String[] projection)
	{
		ArrayList<String> available = new ArrayList<String>();
		available.addAll(Arrays.asList(AthleteTable.ATHLETE_COL_NAMES));
		available.addAll(Arrays.asList(WorkoutTable.WORKOUT_COL_NAMES));
		available.addAll(Arrays.asList(SetTable.SET_COL_NAMES));
		available.addAll(Arrays.asList(MomentTable.MOMENT_COL_NAMES));

		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(available);

			if(!availableColumns.containsAll(requestedColumns))	{
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}
