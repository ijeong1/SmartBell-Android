package com.mikelady.smartbell.db.table;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.  
 */
public class AthleteTable {
	
	/** ATHLETE table in the database. */
	public static final String DATABASE_TABLE_ATHLETE = "athlete_table";
	
	/** ATHLETE table column names and IDs for database access. */
	public static final String ATHLETE_KEY_ID = "_id";
	public static final int ATHLETE_COL_ID = 0;

	public static final String ATHLETE_IS_MALE = "is_male";
	public static final int ATHLETE_COL_IS_MALE = ATHLETE_COL_ID + 1;
	
	public static final String ATHLETE_HEIGHT = "height";
	public static final int ATHLETE_COL_HEIGHT = ATHLETE_COL_ID + 2;
	
	public static final String ATHLETE_WEIGHT = "weight";
	public static final int ATHLETE_COL_WEIGHT = ATHLETE_COL_ID + 3;
	
	public static final String ATHLETE_FOREARM = "forearm";
	public static final int ATHLETE_COL_FOREARM = ATHLETE_COL_ID + 4;
	
	public static final String ATHLETE_UPPER_ARM = "upper_arm";
	public static final int ATHLETE_COL_UPPER_ARM = ATHLETE_COL_ID + 5;
	
	public static final String ATHLETE_TORSO = "torso";
	public static final int ATHLETE_COL_TORSO = ATHLETE_COL_ID + 6;
	
	public static final String ATHLETE_THIGH = "thigh";
	public static final int ATHLETE_COL_THIGH = ATHLETE_COL_ID + 7;
	
	public static final String ATHLETE_SHIN = "shin";
	public static final int ATHLETE_COL_SHIN = ATHLETE_COL_ID + 8;
	
	public static final String[] ATHLETE_COL_NAMES = {ATHLETE_KEY_ID, ATHLETE_IS_MALE, ATHLETE_HEIGHT, ATHLETE_WEIGHT,
		ATHLETE_FOREARM, ATHLETE_UPPER_ARM, ATHLETE_TORSO, ATHLETE_THIGH, ATHLETE_SHIN};
	
	/** SQLite database creation statement. Auto-increments IDs of inserted jokes.
	 * Joke IDs are set after insertion into the database. */
	public static final String DATABASE_CREATE = "create table " + DATABASE_TABLE_ATHLETE + " (" + 
			ATHLETE_KEY_ID + " integer primary key autoincrement, " + 
			ATHLETE_IS_MALE	+ " text not null, " + 
			ATHLETE_HEIGHT	+ " integer not null, " + 
			ATHLETE_WEIGHT	+ " integer not null, " + 
			ATHLETE_FOREARM	+ " integer not null, " + 
			ATHLETE_UPPER_ARM	+ " integer not null, " + 
			ATHLETE_TORSO	+ " integer not null, " + 
			ATHLETE_THIGH	+ " integer not null, " + 
			ATHLETE_SHIN	+ " integer not null " + ");";
	
	/** SQLite database table removal statement. Only used if upgrading database. */
	public static final String DATABASE_DROP = "drop table if exists " + DATABASE_TABLE_ATHLETE;
	
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
		Log.w(AthleteTable.class.getName(), "Database is being upgraded from "+oldVersion+" to "+newVersion);
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
}
