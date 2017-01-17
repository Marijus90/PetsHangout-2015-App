package eu.luckyj.petshangout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * FindUsers.java
 * Version Rev 1
 * 13/09/2015
 * @author Marijus Jalinskas, x14125421
 */
public class FindUsers extends AppCompatActivity {
	EditText searchField;
	String searchInput;
	Button search;
	ListView lv;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_users);
		getWindow().setBackgroundDrawableResource(R.drawable.bgapp);

		search = (Button) findViewById(R.id.search_btn);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Read the input
				searchField = (EditText) findViewById(R.id.et_search_field);
				searchInput = searchField.getText().toString();
				// Search and output the results
				findUsers(searchInput);
			}
		});
	}

	// Looks for a user or city matching the query
	public void findUsers(String input) {
		input = searchInput;
//		ParseQuery<ParseUser> query = ParseUser.getQuery();
//		query.whereEqualTo("username", input);
//		query.findInBackground(new FindCallback<ParseUser>() {
//			public void done(List<ParseUser> objects, ParseException e) {
//				if (e == null) {
//					// The query was successful.
//					String after = "The query was successful.";
//					Toast.makeText(context, after, Toast.LENGTH_LONG).show();
//				} else {
//					// Something went wrong.
//				}
//			}
//		});

		final ArrayList<String> resultList = new ArrayList<String>();
		lv = (ListView) findViewById(R.id.results_view);
		ParseQuery<ParseObject> query = ParseQuery.getQuery(input);
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> userList, ParseException e) {
				if (e == null) {
					for (int i = 0; i < userList.size(); i++) {
						resultList.add(userList.get(i).getString("username") + ", " + userList.get(i).getString("city"));
						Toast.makeText(context, "Query successful", Toast.LENGTH_LONG).show();
					}
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}

		});

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resultList);
		lv.setAdapter(arrayAdapter);
		// Refresh the output
		lv.invalidateViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_find_pets, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here.
		int id = item.getItemId();
		if (id == R.id.action_logout) {
			// Logout current user
			ParseUser.logOut();
			finish();
			Intent intent = new Intent(this,
					LoginSignupActivity.class);
			startActivity(intent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}