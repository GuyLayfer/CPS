package core.worker;

import java.time.ZoneId;
import java.util.Date;

public class Complaint {
	private static final int daysToReply = 1;

	private String content;
	
	private int customerId;

	private Date timeIssued;

	private Date dueReplyDate;
	
	private int complaintId;
	// No setter on propose. All time implementation is here and should stay here so it'll be consisted throughout the program.

	public Complaint(String content, int customerId, Date timeIssued, int complaintId) {
		setContent(content);
		setCustomerId(customerId);
		setTimeIssued(timeIssued);
		setComplaintId(complaintId);
	}

	public long getTimeLeftToReply() {
		return dueReplyDate.getTime() - (new Date().getTime());
	}

	@Override
	public String toString() {
		long left = getTimeLeftToReply();
		if(left <= 0) {
			return "Already due.";
		}
		long diffSeconds = left / 1000 % 60;
		long diffMinutes = left / (60 * 1000) % 60;
		long diffHours = left / (60 * 60 * 1000);
		return diffHours + ":" 
				+ (diffMinutes < 10 ? "0" +  diffMinutes : diffMinutes) + ":" 
				+ (diffSeconds < 10 ? "0" + diffSeconds : diffSeconds);
	}

	public String getContent() {
		return content;
	}

	public Date getTimeIssued() {
		return timeIssued;
	}

	public Date getDueReplyDate() {
		return dueReplyDate;
	}

	public int getCustomerId() {
		return customerId;
	}
	
	public int getComplaintId() {
		return complaintId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimeIssued(Date timeIssued) {
		this.timeIssued = timeIssued;
		dueReplyDate = Date.from(
				(timeIssued.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime())
						.plusDays(daysToReply)
						.atZone(ZoneId.systemDefault())
						.toInstant());
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
}
