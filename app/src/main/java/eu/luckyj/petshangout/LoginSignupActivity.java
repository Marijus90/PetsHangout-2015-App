package eu.luckyj.petshangout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * LoginSignupActivity.java
 * Version Rev 1
 * 12/09/2015
 * @reference http://www.androidbegin.com/tutorial/android-parse-com-simple-login-and-signup-tutorial/
 */

public class LoginSignupActivity extends Activity {
	Button loginbutton;
	Button signup;
	String usernametxt;
	String passwordtxt;
	String citytxt;
	EditText password;
	EditText username;
	EditText city;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_signup);
		getWindow().setBackgroundDrawableResource(R.drawable.bglogin);
		username = (EditText) findViewById(R.id.pUsername);
		password = (EditText) findViewById(R.id.pPassword);
		city = (EditText) findViewById(R.id.pCity);


		loginbutton = (Button) findViewById(R.id.pLogin);
		signup = (Button) findViewById(R.id.pSignup);

		loginbutton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();

				// Send data to Parse.com for verification
				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// If user exist and authenticated, greet the user
									Intent intent = new Intent(
											LoginSignupActivity.this,
											MainActivity.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in",
											Toast.LENGTH_LONG).show();
									finish();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"No such user exist, please signup",
											Toast.LENGTH_LONG).show();
								}
							}
						});
			}
		});

		signup.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				usernametxt = username.getText().toString();
				passwordtxt = password.getText().toString();
				citytxt = city.getText().toString();

				// Force user to fill up the form
				if (usernametxt.equals("") || passwordtxt.equals("") || citytxt.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please complete the sign up form",
							Toast.LENGTH_LONG).show();
				} else {
					// Save new user data into Parse.com Data Storage
					ParseUser user = new ParseUser();
					user.setUsername(usernametxt);
					user.setPassword(passwordtxt);
					user.put("city", citytxt);
					user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Show a simple Toast message upon successful registration
								Toast.makeText(getApplicationContext(),
										"Successfully Signed up, please log in.",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(getApplicationContext(),
										"Sign up Error", Toast.LENGTH_LONG)
										.show();
							}
						}
					});
				}

			}
		});

	}

}