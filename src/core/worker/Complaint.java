package core.worker;

import java.time.ZoneId;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class Complaint.
 */
public class Complaint {
	
	/** The Constant daysToReply. */
	private static final int daysToReply = 1;

	/** The content. */
	private String content;
	
	/** The customer id. */
	private int customerId;

	/** The time issued. */
	private Date timeIssued;

	/** The due reply date. */
	private Date dueReplyDate;
	
	/** The complaint id. */
	private int complaintId;
	// No setter on propose. All time implementation is here and should stay here so it'll be consisted throughout the program.

	/**
	 * Instantiates a new complaint.
	 *
	 * @param content the content
	 * @param customerId the customer id
	 * @param timeIssued the time issued
	 * @param complaintId the complaint id
	 */
	public Complaint(String content, int customerId, Date timeIssued, int complaintId) {
		setContent(content);
		setCustomerId(customerId);
		setTimeIssued(timeIssued);
		setComplaintId(complaintId);
	}

	/**
	 * Gets the time left to reply.
	 *
	 * @return the time left to reply
	 */
	public long getTimeLeftToReply() {
		return dueReplyDate.getTime() - (new Date().getTime());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Gets the time issued.
	 *
	 * @return the time issued
	 */
	public Date getTimeIssued() {
		return timeIssued;
	}

	/**
	 * Gets the due reply date.
	 *
	 * @return the due reply date
	 */
	public Date getDueReplyDate() {
		return dueReplyDate;
	}

	/**
	 * Gets the customer id.
	 *
	 * @return the customer id
	 */
	public int getCustomerId() {
		return customerId;
	}
	
	/**
	 * Gets the complaint id.
	 *
	 * @return the complaint id
	 */
	public int getComplaintId() {
		return complaintId;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Sets the time issued.
	 *
	 * @param timeIssued the new time issued
	 */
	public void setTimeIssued(Date timeIssued) {
		this.timeIssued = timeIssued;
		dueReplyDate = Date.from(
				(timeIssued.toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime())
						.plusDays(daysToReply)
						.atZone(ZoneId.systemDefault())
						.toInstant());
	}

	/**
	 * Sets the customer id.
	 *
	 * @param customerId the new customer id
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	/**
	 * Sets the complaint id.
	 *
	 * @param complaintId the new complaint id
	 */
	public void setComplaintId(int complaintId) {
		this.complaintId = complaintId;
	}
}
