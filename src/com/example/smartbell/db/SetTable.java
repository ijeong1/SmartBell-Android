package com.example.smartbell.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class SetTable {
	
	/** SET table in the database. */
	public static final String DATABASE_TABLE_SET = "set_table";
	
	/** SET table column names and IDs for database access. */
	public static final String SET_KEY_ID = "_id";
	public static final int SET_COL_ID = 0;
	
	public static final String SET_TIMESTAMP = "timestamp";
	public static final int SET_COL_TIMESTAMP = SET_COL_ID + 1;
	
	public static final String SET_WORKOUT_ID = "workout_id";
	public static final int SET_COL_WORKOUT_ID = SET_COL_ID + 2;
	
	public static final String SET_WEIGHT_LIFTED = "weight_lifted";
	public static final int SET_COL_WEIGHT_LIFTED = SET_COL_ID + 3;
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_SET + " (" + 
			SET_KEY_ID + " integer primary key autoincrement, " + 
			SET_TIMESTAMP	+ " text not null, " + 
			SET_WORKOUT_ID 	+ "integer foreign key REFERENCES "+WorkoutTable.DATABASE_TABLE_WORKOUT+"("+WorkoutTable.WORKOUT_COL_ID+"), " +
			SET_WEIGHT_LIFTED + "integer not null" + ");";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_SET;
	
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
		Log.w(SetTable.class.getName(), "Database is being upgraded from "+oldVersion+" to "+newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
