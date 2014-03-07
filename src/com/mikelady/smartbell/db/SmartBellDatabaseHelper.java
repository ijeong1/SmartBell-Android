package com.mikelady.smartbell.db;
import com.mikelady.smartbell.db.table.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Class that hooks up to the MomentContentProvider for initialization and
 * maintenance. Uses MomentTable for assistance.
 */
public class SmartBellDatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

	/** The name of the database. */
	public static final String DATABASE_NAME = "smartbell_database.db";
	
	/** The starting database version. */
	public static final int DATABASE_VERSION = 1;
	
	/**
	 * Create a helper object to create, open, and/or manage a database.
	 * 
	 * @param context
	 * 					The application context.
	 * @param name
	 * 					The name of the database.
	 * @param factory
	 * 					Factory used to create a cursor. Set to null for default behavior.
	 * @param version
	 * 					The starting database version.
	 */
	public SmartBellDatabaseHelper(Context context, String name,
		CursorFactory factory, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		AthleteTable.onCreate(db);
		WorkoutTable.onCreate(db);
		LiftingSetTable.onCreate(db);
		MomentTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		AthleteTable.onUpgrade(db, oldVersion, newVersion);
		WorkoutTable.onUpgrade(db, oldVersion, newVersion);
		LiftingSetTable.onUpgrade(db, oldVersion, newVersion);
		MomentTable.onUpgrade(db, oldVersion, newVersion);
	}
}
