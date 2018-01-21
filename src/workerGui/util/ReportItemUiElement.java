package workerGui.util;

import core.worker.ReportItem;
import javafx.beans.property.SimpleStringProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportItemUiElement.
 */
public class ReportItemUiElement {
	
	/** The description. */
	private SimpleStringProperty description = new SimpleStringProperty(this, "reportItemDescription");
	
	/** The detail. */
	private SimpleStringProperty detail = new SimpleStringProperty(this, "reportItemDetail");
	
	/** The report item. */
	private ReportItem reportItem;
	
	/**
	 * Instantiates a new report item ui element.
	 *
	 * @param reportItem the report item
	 */
	public ReportItemUiElement(ReportItem reportItem) {
		this.description.set(reportItem.reportItemDescription);
		this.detail.set(reportItem.reportItemDetail);
		this.reportItem = reportItem;
	}
	
	/**
	 * Gets the vlue.
	 *
	 * @return the vlue
	 */
	public ReportItem getVlue() {
		return reportItem;
	}
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description.get();
	}

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public String getDetail() {
		return detail.get();
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description.set(description);
	}

	/**
	 * Sets the detail.
	 *
	 * @param detail the new detail
	 */
	public void setDetail(String detail) {
		this.detail.set(detail);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getDescription();
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

		ReportItemUiElement reportItemUiElement = (ReportItemUiElement) o;

		return description != null ? description.equals(reportItemUiElement.description) : reportItemUiElement.description == null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return description != null ? description.hashCode() : 0;
	}
}
