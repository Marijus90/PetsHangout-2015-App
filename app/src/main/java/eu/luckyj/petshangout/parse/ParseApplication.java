package eu.luckyj.petshangout.parse;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * ParseApplication.java
 * Version Rev 1
 * 11/09/2015
 * @author Marijus Jalinskas, x14125421
 */
public class ParseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Initialization code
		Parse.initialize(this, "SruKilB8W5O9mlI8cxjesc2T2T2DSeGo41lCbZem", "JeXCg8Kfhsb1IRS4GzqaSkpj5JfL5YISMPA4Zpv3");
		// Enables automatic creation of anonymous users. After calling this method, ParseUser.getCurrentUser() will always have a value.
		ParseUser.enableAutomaticUser();
		// The ParseACL that is applied to newly-created ParseObjects will provide read and write access to the ParseUser.getCurrentUser() at the time of creation.
		ParseACL defaultACL = new ParseACL();
		ParseACL.setDefaultACL(defaultACL, true);
	}

}