package workerGui.util;

import core.worker.ReportItem;
import javafx.beans.property.SimpleStringProperty;

public class ReportItemUiElement {
	private SimpleStringProperty description = new SimpleStringProperty(this, "reportItemDescription");
	private SimpleStringProperty detail = new SimpleStringProperty(this, "reportItemDetail");
	private ReportItem reportItem;
	
	public ReportItemUiElement(ReportItem reportItem) {
		this.description.set(reportItem.reportItemDescription);
		this.detail.set(reportItem.reportItemDetail);
		this.reportItem = reportItem;
	}
	
	public ReportItem getVlue() {
		return reportItem;
	}
	
	public String getDescription() {
		return description.get();
	}

	public String getDetail() {
		return detail.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public void setDetail(String detail) {
		this.detail.set(detail);
	}

	@Override
	public String toString() {
		return getDescription();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ReportItemUiElement reportItemUiElement = (ReportItemUiElement) o;

		return description != null ? description.equals(reportItemUiElement.description) : reportItemUiElement.description == null;
	}

	@Override
	public int hashCode() {
		return description != null ? description.hashCode() : 0;
	}
}
