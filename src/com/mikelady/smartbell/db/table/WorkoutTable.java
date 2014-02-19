package com.mikelady.smartbell.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class WorkoutTable {
	
	/** WORKOUT table in the database. */
	public static final String DATABASE_TABLE_WORKOUT = "workout_table";
	
	/** WORKOUT table column names and IDs for database access. */
	public static final String WORKOUT_KEY_ID = "_id";
	public static final int WORKOUT_COL_ID = 0;
	
	public static final String WORKOUT_TIMESTAMP = "timestamp";
	public static final int WORKOUT_COL_TIMESTAMP = WORKOUT_COL_ID + 1;
	
	public static final String WORKOUT_ATHLETE_ID = "athlete_id";
	public static final int SET_COL_ATHLETE_ID = WORKOUT_COL_ID + 2;
	
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_WORKOUT + " (" + 
			WORKOUT_KEY_ID + " integer primary key autoincrement, " + 
			WORKOUT_TIMESTAMP	+ " text not null, " +
			WORKOUT_ATHLETE_ID 	+ "integer foreign key REFERENCES "+AthleteTable.DATABASE_TABLE_ATHLETE+"("+AthleteTable.ATHLETE_COL_ID+")"+");";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_WORKOUT;
	
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
		Log.w(WorkoutTable.class.getName(), "Database is being upgraded from "+oldVersion+" to "+newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
