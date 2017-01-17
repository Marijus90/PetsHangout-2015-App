package eu.luckyj.petshangout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import eu.luckyj.petshangout.notes.DBOpenHelper;
import eu.luckyj.petshangout.notes.NotesProvider;

/**
 * EditorActivity.java
 * Version Rev 1
 * 20/07/2015
 * @reference http://git.io/jxTM
 * @author Marijus Jalinskas, x14125421
 */
public class EditorActivity extends AppCompatActivity {
	private String noteFilter;
	private String oldText;
	private String action;
	private EditText editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		getWindow().setBackgroundDrawableResource(R.drawable.bgapp);
		editor = (EditText) findViewById(R.id.editText);
		// Checking if intent is to create new or update an existing note
		Intent intent = getIntent();
		Uri uri = intent.getParcelableExtra(NotesProvider.CONTENT_ITEM_TYPE);
		if (uri != null) {
			action = Intent.ACTION_EDIT;
			noteFilter = DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
			Cursor cursor = getContentResolver().query(uri, DBOpenHelper.ALL_COLUMNS, noteFilter, null, null);
			// Retrieving data showing which note was selected to edit by user
			cursor.moveToFirst();
			oldText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
			editor.setText(oldText);
			editor.requestFocus();
		} else {
			action = Intent.ACTION_INSERT;
			setTitle(getString(R.string.n_note));
		}
	}

	// Save the typed note when used presses back button on phone
	private void save() {
		String newText = editor.getText().toString().trim();
		switch (action) {
			case Intent.ACTION_INSERT:
				if (newText.length() == 0) {
					setResult(RESULT_CANCELED);
				} else {
					insertNote(newText);
				}
				break;
			case Intent.ACTION_EDIT:
				if (newText.length() == 0) {
					deleteNote();
				} else if (oldText.equals(newText)) {
					setResult(RESULT_CANCELED);
				} else {
					updateNote(newText);
				}
		}
		finish();
	}

	private void deleteNote() {
		getContentResolver().delete(NotesProvider.CONTENT_URI, noteFilter, null);
		Toast.makeText(this, getString(R.string.deleted), Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
		finish();
	}

	private void updateNote(String newText) {
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.NOTE_TEXT, newText);
		getContentResolver().update(NotesProvider.CONTENT_URI, values, noteFilter, null);
		Toast.makeText(this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
		setResult(RESULT_OK);
	}

	// Inserting note to the DB
	private void insertNote(String noteText) {
		ContentValues values = new ContentValues();
		values.put(DBOpenHelper.NOTE_TEXT, noteText);
		getContentResolver().insert(NotesProvider.CONTENT_URI, values);
		setResult(RESULT_OK);
	}

	@Override
	public void onBackPressed() {
		save();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (action.equals(Intent.ACTION_EDIT)) {
			getMenuInflater().inflate(R.menu.menu_editor, menu);
		}
		return true;
	}

	/**
	 * @author Marijus Jalinskas, x14125421
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handling action bar item clicks here
		switch (item.getItemId()) {
			// Back button ID:
			case android.R.id.home:
				save();
				break;
			// When user touches Trash icon - delete note
			case R.id.action_delete:
				deleteNote();
				break;
		}
		return true;
	}
}