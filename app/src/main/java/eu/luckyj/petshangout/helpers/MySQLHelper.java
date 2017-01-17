package eu.luckyj.petshangout.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * MySQLHelper.java
 * Version Rev 1
 * 07/09/2015
 * @reference https://www.youtube.com/watch?v=G87jlQZeQJ0
 */

public class MySQLHelper extends SQLiteOpenHelper {

	public static final String TABLE_NAME = "locations";
	// Column names
	public static final String ID_COL = "loc_id";
	public static final String TITLE = "loc_title";
	public static final String SNIPPET = "loc_snippet";
	public static final String POSITION = "loc_position";
	// DB version
	public static final int D_VERSION = 1;
	// Filename
	public static final String DB_NAME = "marketlocations.db";
	// Create statement
	public static final String DB_CREATE = "create table " + TABLE_NAME
			+ "(" + ID_COL + " integer primary key autoincrement, "
			+ TITLE + " text, " + SNIPPET + " text, " + POSITION + " text);";

	public MySQLHelper(Context context) {
		super(context, DB_NAME, null, D_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
		onCreate(db);
	}
}
