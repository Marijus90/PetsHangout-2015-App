package eu.luckyj.petshangout.chat;

/**
 * Message.java
 * Version Rev 1
 * 11/08/2015
 * @reference https://github.com/koush/android-websockets/
 */

/**
 * This model class defines each chat message where it contains message id,
 * text and a boolean flag(isSelf) to define message owner.
 * Using this boolean flag message will align left or right in the list view.
 */
public class Message {
	private String fromName, message;
	private boolean isSelf;

	public Message() {
	}

	public Message(String fromName, String message, boolean isSelf) {
		this.fromName = fromName;
		this.message = message;
		this.isSelf = isSelf;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean isSelf) {
		this.isSelf = isSelf;
	}
}