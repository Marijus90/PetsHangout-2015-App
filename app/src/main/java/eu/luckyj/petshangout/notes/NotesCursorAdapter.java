package eu.luckyj.petshangout.notes;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import eu.luckyj.petshangout.R;

/**
 * NotesCursorAdapter.java
 * Version Rev 1
 * 12/07/2015
 * @reference PlainOlNotes2/app/src/main/java/com/example/plainolnotes/DBOpenHelper.java
 */
public class NotesCursorAdapter extends CursorAdapter {

	public NotesCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	// Returns a view created based on a layout that defines the ListItem display
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
	}

	@Override
	// Binding the view
	public void bindView(View view, Context context, Cursor cursor) {
		// Receiving a cursor object pointing to the row of data in DB that's suppose to be displayed
		String noteText = cursor.getString(cursor.getColumnIndex(DBOpenHelper.NOTE_TEXT));
		// Detecting Newline char position and if found modifying the noteText
		int position = noteText.indexOf(10);
		if (position != -1) {
			noteText = noteText.substring(0, position) + " ...";
		}
		// Displaying string in TextView
		TextView tv = (TextView) view.findViewById(R.id.tvNote);
		tv.setText(noteText);
	}
}