package workerGui.controllers;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface IAddItemsToTable.
 *
 * @param <T> the generic type
 */
public interface IAddItemsToTable<T> {
	
	/**
	 * Adds the to table.
	 *
	 * @param pendingItems the pending items
	 */
	public void AddToTable(List<T> pendingItems);
}
