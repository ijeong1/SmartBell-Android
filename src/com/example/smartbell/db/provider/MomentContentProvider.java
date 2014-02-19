package com.example.smartbell.db.provider;

import java.util.Arrays;
import java.util.HashSet;

import com.example.smartbell.db.SmartBellDatabaseHelper;
import com.example.smartbell.db.table.MomentTable;

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
 * Provides moment information to a ListView through a CursorAdapter. The database stores
 * moments in a two-dimensional table, where each row is a moment and each column is a property
 * of a moment (ID, moment text, moment rating, moment author).
 * 
 * Note that CursorLoaders require a ContentProvider, which is why this application wraps a
 * SQLite database into a content provider instead of managing the database<-->application
 * transactions manually.
 */
public class MomentContentProvider extends ContentProvider {

	/** The moment database. */
	private SmartBellDatabaseHelper database;

	/** Values for the URIMatcher. */
	private static final int MOMENT_ID = 1;
	private static final int JOKE_FILTER = 2;

	/** The authority for this content provider. */
	private static final String AUTHORITY = "com.example.smarbell.momentcontentprovider";

	/** The database table to read from and write to, and also the root path for use in the URI matcher.
	 * This is essentially a label to a two-dimensional array in the database filled with rows of moments
	 * whose columns contain moment data. */
	private static final String BASE_PATH = "moment_table";

	/** This provider's content location. Used by accessing applications to
	 * interact with this provider. */
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	/** Matches content URIs requested by accessing applications with possible
	 * expected content URI formats to take specific actions in this provider. */
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/moments/#", MOMENT_ID);
	}

	@Override
	public boolean onCreate() {
		database = new SmartBellDatabaseHelper(this.getContext(), SmartBellDatabaseHelper.DATABASE_NAME, null, SmartBellDatabaseHelper.DATABASE_VERSION);
		return true;
	}

	/**
	 * Fetches rows from the moment table. Given a specified URI that contains a
	 * filter, returns a list of moments from the moment table matching that filter in the
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

		/** Set up helper to query our moments table. */
		queryBuilder.setTables(MomentTable.DATABASE_TABLE_MOMENT);

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
	 * Inserts a moment into the moment table. Given a specific URI that contains a
	 * moment and the values of that moment, writes a new row in the table filled
	 * with that moment's information and gives the moment a new ID, then returns a URI
	 * containing the ID of the inserted moment.<br><br>
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

		/** Will contain the ID of the inserted moment. */
		long id = 0;

		/** Match the passed-in URI to an expected URI format. */
		int uriType = sURIMatcher.match(uri);

		switch(uriType)	{

		/** Expects a moment ID, but we will do nothing with the passed-in ID since
		 * the database will automatically handle ID assignment and incrementation.
		 * IMPORTANT: moment ID cannot be set to -1 in passed-in URI; -1 is not interpreted
		 * as a numerical value by the URIMatcher. */
		case MOMENT_ID:

			/** Perform the database insert, placing the moment at the bottom of the table. */
			id = sqlDB.insert(MomentTable.DATABASE_TABLE_MOMENT, null, values);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		/** Alert any watchers of an underlying data change for content/view refreshing. */
		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(BASE_PATH + "/" + id);
	}

	/**
	 * Removes a row from the moment table. Given a specific URI containing a moment ID,
	 * removes rows in the table that match the ID and returns the number of rows removed.
	 * Since IDs are automatically incremented on insertion, this will only ever remove
	 * a single row from the moment table.<br><br>
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

		switch(uriType)	{
		case MOMENT_ID:
			String id = uri.getLastPathSegment();
			rowsDeleted = sqlDB.delete(MomentTable.DATABASE_TABLE_MOMENT, MomentTable.MOMENT_KEY_ID+"="+id, null);
		}
		
		if(rowsDeleted > 0)
			getContext().getContentResolver().notifyChange(uri, null);
		
		return rowsDeleted;
	}

	/**
	 * Updates a row in the moment table. Given a specific URI containing a moment ID and the
	 * new moment values, updates the values in the row with the matching ID in the table. 
	 * Since IDs are automatically incremented on insertion, this will only ever update
	 * a single row in the moment table.<br><br>
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

		switch(uriType)	{
		case MOMENT_ID:
			String id = uri.getLastPathSegment();
			Log.d("mlady","id: "+id);
			Log.d("mlady","values: "+values);
			rowsUpdated = sqlDB.update(MomentTable.DATABASE_TABLE_MOMENT, values, MomentTable.MOMENT_KEY_ID+"="+id, null);
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
		String[] available = { MomentTable.MOMENT_KEY_ID, MomentTable.MOMENT_TIMESTAMP, MomentTable.MOMENT_EULER_X, 
				MomentTable.MOMENT_EULER_Y, MomentTable.MOMENT_EULER_Z, MomentTable.MOMENT_LINACC_X, MomentTable.MOMENT_LINACC_Y,
				MomentTable.MOMENT_LINACC_Z };

		if(projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

			if(!availableColumns.containsAll(requestedColumns))	{
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}
}
