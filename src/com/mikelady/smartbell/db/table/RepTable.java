package com.mikelady.smartbell.db.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class RepTable {
	
	/** REP table in the database. */
	public static final String DATABASE_TABLE_REP = "rep_table";
	
	/** REP table column names and IDs for database access. */
	public static final String REP_KEY_ID = "_id";
	public static final int REP_COL_ID = 0;
	
	public static final String REP_TIMESTAMP = "timestamp";
	public static final int REP_COL_TIMESTAMP = REP_COL_ID + 1;
	
	public static final String REP_SET_ID = "set_id";
	public static final int REP_COL_SET_ID = REP_COL_ID + 2;
	
	public static final String REP_SEQ_ID = "seq_id";
	public static final int REP_COL_SEQ_ID = REP_COL_ID + 3;
	
	public static final String REP_ORIENT_CATEGORY = "orient_category";
	public static final int REP_COL_ORIENT_CATEGORY = REP_COL_ID + 4;
	
	public static final String REP_PATH_CATEGORY = "path_category";
	public static final int REP_COL_PATH_CATEGORY = REP_COL_ID + 5;
	
	public static final String[] REP_COL_NAMES = {REP_KEY_ID, REP_TIMESTAMP, 
		REP_SET_ID, REP_SEQ_ID, REP_ORIENT_CATEGORY, REP_PATH_CATEGORY};
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are rep after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_REP + " (" + 
			REP_KEY_ID + " integer primary key autoincrement, " + 
			REP_TIMESTAMP	+ " text not null, " + 
			REP_SET_ID 	+ " integer REFERENCES "+LiftingSetTable.DATABASE_TABLE_SET+"("+LiftingSetTable.SET_KEY_ID+"), " +
			REP_SEQ_ID	+ " integer not null, "+
			REP_ORIENT_CATEGORY	+ " text not null, "+
			REP_PATH_CATEGORY	+ " text not null, "+
			");";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_REP;
	
	/**
	 * Initializes the database.
	 * 
	 * @param database
	 * 				The database to initialize.	
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * Upgrades the database to a new version.
	 * 
	 * @param database
	 * 					The database to upgrade.
	 * @param oldVersion
	 * 					The old version of the database.
	 * @param newVersion
	 * 					The new version of the database.
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(RepTable.class.getName(), "Database is being upgraded from "+oldVersion+" to "+newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
