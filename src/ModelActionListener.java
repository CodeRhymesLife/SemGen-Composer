/*
 * Listens for model events
 */
public interface ModelActionListener {
	/*
	 * Called when a property is added
	 * to a model
	 */
	void addedProperty();
	
	/*
	 * Called when a property is removed
	 * from a model
	 */
	void removedProperty();
}
