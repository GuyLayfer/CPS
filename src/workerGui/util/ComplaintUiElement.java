package workerGui.util;

import core.worker.Complaint;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplaintUiElement.
 */
public class ComplaintUiElement {
	
	/** The Constant displayLength. */
	private static final int displayLength = 100;
	
	/** The content. */
	private SimpleStringProperty content = new SimpleStringProperty(this, "content");
	
	/** The brief content. */
	private SimpleStringProperty briefContent = new SimpleStringProperty(this, "briefContent");
	
	/** The customer id. */
	private SimpleIntegerProperty customerId = new SimpleIntegerProperty(this, "customerId");
	
	/** The time left to reply. */
	private SimpleStringProperty timeLeftToReply = new SimpleStringProperty(this, "timeLeftToReply");
	
	/** The complaint. */
	private Complaint complaint;

	/**
	 * Instantiates a new complaint ui element.
	 *
	 * @param complaint the complaint
	 */
	public ComplaintUiElement(Complaint complaint) {
		this.content.set(complaint.getContent());
		this.timeLeftToReply.set(complaint.toString());
		this.customerId.set(complaint.getCustomerId());
		String shortContent = getContent()
				.subSequence(0, getContent().length() < displayLength ? getContent().length() : displayLength)
				.toString();
		briefContent.set(shortContent);
		this.complaint = complaint;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Complaint getValue() {
		return this.complaint;
	}

	/**
	 * Gets the brief content.
	 *
	 * @return the brief content
	 */
	public String getBriefContent() {
		return briefContent.get();
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content.get();
	}

	/**
	 * Gets the time left to reply.
	 *
	 * @return the time left to reply
	 */
	public String getTimeLeftToReply() {
		return timeLeftToReply.get();
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content.set(content);
	}

	/**
	 * Sets the time left to reply.
	 *
	 * @param timeLeftToReply the new time left to reply
	 */
	public void setTimeLeftToReply(String timeLeftToReply) {
		this.timeLeftToReply.set(timeLeftToReply);
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public int getCustomerId() {
		return customerId.get();
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(int customerId) {
		this.customerId.set(customerId);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getContent();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ComplaintUiElement complaintElement = (ComplaintUiElement) o;

		return content != null ? content.equals(complaintElement.content) : complaintElement.content == null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return content != null ? content.hashCode() : 0;
	}
}
