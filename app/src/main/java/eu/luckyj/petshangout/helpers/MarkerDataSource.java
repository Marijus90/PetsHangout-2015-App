package eu.luckyj.petshangout.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * MarkerDataSource.java
 * Version Rev 1
 * 07/09/2015
 * @reference https://www.youtube.com/user/505mesngr/videos
 */

public class MarkerDataSource {

	MySQLHelper dbHelper;
	SQLiteDatabase db;
	String[] columns = {MySQLHelper.TITLE, MySQLHelper.SNIPPET, MySQLHelper.POSITION};

	public MarkerDataSource(Context c) {
		dbHelper = new MySQLHelper(c);
	}

	// Adding marker to the DB
	public void addMarker(MyMarkerObject object) {
		ContentValues values = new ContentValues();
		// A list of values to insert into database
		values.put(MySQLHelper.TITLE, object.getTitle());
		values.put(MySQLHelper.SNIPPET, object.getSnippet());
		values.put(MySQLHelper.POSITION, object.getPosition());

		db.insert(MySQLHelper.TABLE_NAME, null, values);
	}

	// Deleting marker from the DB
	public void deleteMarker(MyMarkerObject object) {
		db.delete(MySQLHelper.TABLE_NAME, MySQLHelper.POSITION + " = '" + object.getPosition() + "'", null);
	}

	public List<MyMarkerObject> getMarkers() {
		List<MyMarkerObject> markers = new ArrayList<MyMarkerObject>();
		// Iterating cursor to get markers out
		Cursor cursor = db.query(MySQLHelper.TABLE_NAME, columns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MyMarkerObject marker = cursorToMarker(cursor);
			markers.add(marker);
			cursor.moveToNext();
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return markers;
	}

	// Getting information from cursor and making it into a marker
	private MyMarkerObject cursorToMarker(Cursor cursor) {
		MyMarkerObject marker = new MyMarkerObject();
		marker.setTitle(cursor.getString(0));
		marker.setSnippet(cursor.getString(1));
		marker.setPosition(cursor.getString(2));
		return marker;
	}

	// Create and/or open a database that will be used for reading and writing
	public void open() {
		db = dbHelper.getWritableDatabase();
	}
}