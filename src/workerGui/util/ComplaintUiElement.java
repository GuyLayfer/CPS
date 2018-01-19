package workerGui.util;

import core.worker.Complaint;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ComplaintUiElement {
	private static final int displayLength = 100;
	private SimpleStringProperty content = new SimpleStringProperty(this, "content");
	private SimpleStringProperty briefContent = new SimpleStringProperty(this, "briefContent");
	private SimpleIntegerProperty customerId = new SimpleIntegerProperty(this, "customerId");
	private SimpleStringProperty timeLeftToReply = new SimpleStringProperty(this, "timeLeftToReply");
	private Complaint complaint;

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

	public Complaint getValue() {
		return this.complaint;
	}

	public String getBriefContent() {
		return briefContent.get();
	}

	public String getContent() {
		return content.get();
	}

	public String getTimeLeftToReply() {
		return timeLeftToReply.get();
	}

	public void setContent(String content) {
		this.content.set(content);
	}

	public void setTimeLeftToReply(String timeLeftToReply) {
		this.timeLeftToReply.set(timeLeftToReply);
	}

	public int getCustomerId() {
		return customerId.get();
	}

	public void setCustomerId(int customerId) {
		this.customerId.set(customerId);
	}

	@Override
	public String toString() {
		return getContent();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ComplaintUiElement complaintElement = (ComplaintUiElement) o;

		return content != null ? content.equals(complaintElement.content) : complaintElement.content == null;
	}

	@Override
	public int hashCode() {
		return content != null ? content.hashCode() : 0;
	}
}
