package eu.luckyj.petshangout.helpers;

/**
 * MyMarkerObject.java
 * Version Rev 1
 * 07/09/2015
 * @reference https://www.youtube.com/watch?v=fa3c0Nc8c4Q
 */
 // Class description: Object holding marker information to be passed and saved to the DB

public class MyMarkerObject {

	private long id;
	private String title;
	private String snippet;
	private String position;

	public MyMarkerObject() {
	}

	public MyMarkerObject(long id, String title, String snippet, String position) {
		this.id = id;
		this.title = title;
		this.snippet = snippet;
		this.position = position;
	}

	public MyMarkerObject(String title, String snippet, String position) {
		this.title = title;
		this.snippet = snippet;
		this.position = position;
	}

	// Getter and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
