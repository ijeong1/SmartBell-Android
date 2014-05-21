package com.mikelady.smartbell.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class MomentTable {
	
	/** Moment table in the database. */
	public static final String DATABASE_TABLE_MOMENT = "moment_table";
	
	/** Moment table column names and IDs for database access. */
	public static final String MOMENT_KEY_ID = "_id";
	public static final int MOMENT_COL_ID = 0;
	
	public static final String MOMENT_TIMESTAMP = "timestamp";
	public static final int MOMENT_COL_TIMESTAMP = MOMENT_COL_ID + 1;
	
	public static final String MOMENT_REP_ID = "rep_id";
	public static final int MOMENT_COL_REP_ID = MOMENT_COL_ID + 2;

	public static final String MOMENT_QUAT_W = "quat_W";
	public static final int MOMENT_COL_QUAT_W = MOMENT_COL_ID + 3;
	
	public static final String MOMENT_QUAT_X = "quat_X";
	public static final int MOMENT_COL_QUAT_X = MOMENT_COL_ID + 4;
	
	public static final String MOMENT_QUAT_Y = "quat_Y";
	public static final int MOMENT_COL_QUAT_Y = MOMENT_COL_ID + 5;
	
	public static final String MOMENT_QUAT_Z = "quat_Z";
	public static final int MOMENT_COL_QUAT_Z = MOMENT_COL_ID + 6;
	
	public static final String MOMENT_LINACC_X = "lin_acc_X";
	public static final int MOMENT_COL_LINACC_X = MOMENT_COL_ID + 7;
	
	public static final String MOMENT_LINACC_Y = "lin_acc_Y";
	public static final int MOMENT_COL_LINACC_Y = MOMENT_COL_ID + 8;
	
	public static final String MOMENT_LINACC_Z = "lin_acc_Z";
	public static final int MOMENT_COL_LINACC_Z = MOMENT_COL_ID + 9;
	
	public static final String MOMENT_COMPASS_X = "compass_X";
	public static final int MOMENT_COL_COMPASS_X = MOMENT_COL_ID + 10;
	
	public static final String MOMENT_COMPASS_Y = "compass_Y";
	public static final int MOMENT_COL_COMPASS_Y = MOMENT_COL_ID + 11;
	
	public static final String MOMENT_COMPASS_Z = "compass_Z";
	public static final int MOMENT_COL_COMPASS_Z = MOMENT_COL_ID + 12;
	
	
	public static final String[] MOMENT_COL_NAMES = {MOMENT_KEY_ID, MOMENT_TIMESTAMP,
		MOMENT_REP_ID, MOMENT_QUAT_W, MOMENT_QUAT_X, MOMENT_QUAT_Y, MOMENT_QUAT_Z, MOMENT_LINACC_X,
		MOMENT_LINACC_Y, MOMENT_LINACC_Z, MOMENT_COMPASS_X, MOMENT_COMPASS_Y, MOMENT_COMPASS_Z};
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_MOMENT + " (" + 
			MOMENT_KEY_ID + " integer primary key autoincrement, " + 
			MOMENT_TIMESTAMP + " text not null, " + 
			MOMENT_REP_ID 	+ " integer REFERENCES "+RepTable.DATABASE_TABLE_REP+"("+RepTable.REP_KEY_ID+")," +
			MOMENT_QUAT_W	+ " float not null, " + 
			MOMENT_QUAT_X	+ " float not null, " + 
			MOMENT_QUAT_Y	+ " float not null, " +
			MOMENT_QUAT_Z	+ " float not null, " + 
			MOMENT_LINACC_X	+ " float not null, " + 
			MOMENT_LINACC_Y	+ " float not null, " + 
			MOMENT_LINACC_Z	+ " float not null, "+
			MOMENT_COMPASS_X	+ " float not null, " + 
			MOMENT_COMPASS_Y	+ " float not null, " + 
			MOMENT_COMPASS_Z	+ " float not null "+
			");";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_MOMENT;
	
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
		Log.w(MomentTable.class.getName(), "Database is being upgraded from "+oldVersion+" to "+newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
