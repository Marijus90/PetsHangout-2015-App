# Pets Hangout (2015) Project API16

The aim of the project was to build a social app for pet lovers that would allow registered users to use a global chat to share ideas and information and leave text markers on the map for everyone to see.

### Third-party libraries used
[Bolts Android libraty][lb1]  
[Circle button widget][lb2] (Deprecated)  
[Android websockets][lb3]  
And others (source links commented in code)  

### Login/Signup and Main screen
One of the main functionalities that covers multiple application requirements is user profiling. At first stages of development of the application a standard Android Login Activity was used and had hardcoded user credentials.
Later on the old Login Activity was replaced by a new one that uses NoSQL database provided by Parse.com (currently shut down by Facebook) to store user profiles on a cloud Parse.com server.  
After user logs in, users name is then displayed at the bottom of the Main screen. From the Main screen user can access the functionality described in the document below.  

![X|X](http://i68.tinypic.com/2zgetle.jpg)
### Google Maps API
The major functionality on what the whole idea of the application is based is custom marking a map and allowing other users to see the markers, their text and name of a person that placed it. For the moment markers are stored in a SQLite database that allows markers to be seen by different users on same mobile device.
  
![X|X](http://i63.tinypic.com/2lctc45.jpg)
### Global chat room
The chat functionality is implemented using Java web socket technology. Users have their unique Chat Names and can send public messages any time they turn on and log into the application. The following screenshots seen in Figure 5 were taken from a computer running Tomcat v7 server with the web chat server application.
  
![X|X](http://i66.tinypic.com/2mos6s3.jpg)
  
To be able to use the applications Global Chat functionality a Tomcat server has to be running so that users could connect to it. An Android chat server application written in JavaScript is uploaded to the server and it is launched from within an Eclipse IDE on to a local network. An alternative to using a local Tomcat Server is to host the android chat server application on a cloud back-end server.

### Notes storing
Users can save text notes they take using the app. Notes are stored in SQLite - a very light weight database which comes with Android OS as standard. 
  
![X|X](http://i65.tinypic.com/28k7z4o.jpg)

### Classes built and their functions
1.1	ParseApplication – Class for initializing Parse library with a unique application ID. It is declared in Android Manifest file as a separate application so it would be run only once when the Pets Hangout application starts.  
1.2	LoginSignupActivity – Activity for creating new or logging in to existing user profiles.  
1.3	MainActivity – It is an initial activity that is run when the application starts. From its UI other main activities are launched.  
1.4	FindUsers –	Purpose of this activity is to query the cloud database and retrieve a list of particular users that match the query.  
1.5	NotesActivity – This activity allows users to store, update or delete text notes into/from the local database.  
1.6	EditorActivity – Used for editing the text notes.  
Helper classes used for manipulating notes in Notes and Editor Activities:  
1.6.1 	DBOpenHelper – Defines the database structure and manages connections to it.  
1.6.2 	NotesProvider – Is a standardised mechanism for getting to the applications data regardless if it’s a SQLite database, JSON text or any kinds of other structured data. NotesProvider class extends Content provider class which has methods for manipulating the data.  
1.6.3  	NotesCursorAdapter – A custom CursorAdapter class for changing the way the text is displayed in the notes ListView dynamically.  
  
Helper classes used in Chat activity:  
2.1	HybriParser – A helper class for web socket chat.  
2.2	Message – This class defines each chat message and flags them.  
2.3	Utils – A class for managing user’s session utilising Androids shared preferences.  
2.4	WebSocketClient – A helper class for web socket chat which defines the client side.  
2.5	WsConfig – Socket configuration is defined in this class.  
Helper classes used in Maps activity:  
2.6	MarkerDataSource – This class handles adding and deleting marker from/to the database.  
2.7	MyMarkerObject – Class defining an object holding marker information to be passed and saved to the database.  
2.8	MySQLHelper – A class that defines the columns and handles the creating of the database.  

### License
GNU AGPL-v3
(2015)


   [lb1]: <https://github.com/BoltsFramework/Bolts-Android>
   [lb2]: <https://github.com/markushi/android-circlebutton>
   [lb3]: <https://github.com/koush/android-websockets/>
