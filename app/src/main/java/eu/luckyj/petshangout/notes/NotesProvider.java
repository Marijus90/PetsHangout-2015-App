package eu.luckyj.petshangout.notes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * NotesProvider.java
 * Version Rev 1
 * 20/07/2015
 * @reference http://git.io/jxzz
 */
public class NotesProvider extends ContentProvider {

	public static final String CONTENT_ITEM_TYPE = "Empty";
	private static final String AUTHORITY = "eu.luckyj.petshangout.notesprovider";
	private static final String BASE_PATH = "notes"; // Name of the table
	public static final Uri CONTENT_URI =
			Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

	// Constant to identify the requested operation
	private static final int NOTES = 1;
	private static final int NOTES_ID = 2;

	// Uri matcher parses Uri and tells which operation was requested:
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static { //static initializer
		uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
		// Search for ID
		uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTES_ID);
	}

	private SQLiteDatabase database;

	@Override
	public boolean onCreate() {
		// Instance of OpenHelper class
		DBOpenHelper helper = new DBOpenHelper(getContext());
		// Reference to the DB that helper class is managing
		database = helper.getWritableDatabase();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Uri matcher object
		if (uriMatcher.match(uri) == NOTES_ID) {
			selection = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
		}
		return database.query(DBOpenHelper.TABLE_NOTES, DBOpenHelper.ALL_COLUMNS, selection, null, null, null, DBOpenHelper.NOTE_CREATED + " DESC");
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = database.insert(DBOpenHelper.TABLE_NOTES, null, values);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return database.delete(DBOpenHelper.TABLE_NOTES, selection, selectionArgs);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return database.update(DBOpenHelper.TABLE_NOTES, values, selection, selectionArgs);
	}
}
