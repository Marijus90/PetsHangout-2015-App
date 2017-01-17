package eu.luckyj.petshangout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

/**
 * MainActivity.java
 * Version Rev 2
 * 22/07/2015
 *
 * @author Marijus Jalinskas, x14125421
 * @reference http://www.androidbegin.com/tutorial/android-parse-com-simple-login-and-signup-tutorial/
 * @reference https://parse.com/docs/android/guide
 */
public class MainActivity extends AppCompatActivity {

    /**
     * @author Marijus Jalinskas, x14125421
     */
    public static String getUserName() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        // Convert currentUser into String
        return currentUser.getUsername().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * @reference
         * http://www.androidbegin.com/tutorial/android-parse-com-simple-login-and-signup-tutorial/
         */
        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(MainActivity.this,
                    LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is not anonymous user, get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser == null) {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(MainActivity.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }

        /**
         * Displays users name in main menu after logging in
         * @author Marijus Jalinskas, x14125421
         */
        setContentView(R.layout.activity_main);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.getUsername() != null) {
            String struser = getUserName();
            TextView txtuser = (TextView) findViewById(R.id.session_user);
            txtuser.setText("You are logged in as " + struser);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            finish();
            Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @author Marijus Jalinskas, x14125421
     */
    public void openMaps(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.imageview_clicked));
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void openDialogs(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.imageview_clicked));
        Intent intent = new Intent(this, ChatActivity.class);
        String struser = getUserName();
        intent.putExtra("name", struser);
        startActivity(intent);
    }

    public void openNotes(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.imageview_clicked));
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.imageview_clicked));
        Intent intent = new Intent(this, FindUsers.class);
        startActivity(intent);
    }
}
