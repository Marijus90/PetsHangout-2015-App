package eu.luckyj.petshangout;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import eu.luckyj.petshangout.notes.DBOpenHelper;
import eu.luckyj.petshangout.notes.NotesProvider;

/**
 * NotesActivity.java
 * Version Rev 2
 * 20/07/2015
 * @reference https://github.com/dgassner/PlainOlNotes2/blob/master/app/src/main/java/com/example/plainolnotes/MainActivity.java
 * @author Marijus Jalinskas, x14125421
 */
public class NotesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final int EDITOR_CODE = 701;
	private CursorAdapter cursorAdapter;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		getWindow().setBackgroundDrawableResource(R.drawable.bgapp);

		//Arrays showing where data is coming from
		String[] from = {DBOpenHelper.NOTE_TEXT};
		int[] to = {R.id.tvNote};

		cursorAdapter = new eu.luckyj.petshangout.notes.NotesCursorAdapter(this, null, 0);

		list = (ListView) findViewById(android.R.id.list);
		list.setAdapter(cursorAdapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(NotesActivity.this, EditorActivity.class);
				Uri uri = Uri.parse(NotesProvider.CONTENT_URI + "/" + id);
				intent.putExtra(NotesProvider.CONTENT_ITEM_TYPE, uri);
				startActivityForResult(intent, EDITOR_CODE);
			}
		});

		// Initializing loader and using 'this' class to manage it
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * @reference https://github.com/dgassner/PlainOlNotes2/blob/master/app/src/main/java/com/example/plainolnotes/MainActivity.java
	 */
	private void clearNotes() {
		DialogInterface.OnClickListener dialogClickListener =
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int button) {
						if (button == DialogInterface.BUTTON_POSITIVE) {
							//Inserted Data management into Gist's code here
							getContentResolver().delete(NotesProvider.CONTENT_URI,
									null, null);
							restartLoader();

							Toast.makeText(NotesActivity.this,
									getString(R.string.all_deleted),
									Toast.LENGTH_SHORT).show();
						}
					}
				};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.are_you_sure))
				.setPositiveButton(getString(android.R.string.yes), dialogClickListener)
				.setNegativeButton(getString(android.R.string.no), dialogClickListener)
				.show();
	}

	private void restartLoader() {
		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// Designate where data is coming from
		return new CursorLoader(this, NotesProvider.CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Taking data represented by Cursor object and passing it to cursor adapter
		cursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// Called whenever the data needs to be wiped out
		cursorAdapter.swapCursor(null);
	}

	public void newNote(View view) {
		Intent intent = new Intent(this, EditorActivity.class);
		startActivityForResult(intent, EDITOR_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// If note was successfully added/edited Loader updates the view
		if (requestCode == EDITOR_CODE && resultCode == RESULT_OK) {
			restartLoader();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handling action bar item clicks here
		int id = item.getItemId();
		if (id == R.id.action_clear) {
			clearNotes();
		}
		return super.onOptionsItemSelected(item);
	}
}