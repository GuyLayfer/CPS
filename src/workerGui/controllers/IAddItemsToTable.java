package workerGui.controllers;

import java.util.List;

public interface IAddItemsToTable<T> {
	public void AddToTable(List<T> pendingItems);
}
