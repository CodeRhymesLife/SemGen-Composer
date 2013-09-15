/*
 * Listens for model events
 */
public interface ModelListener {
	/*
	 * Called when a property is added
	 * to a model
	 */
	void propertyAdded(IModelProperty property);
	
	/*
	 * Called when a property is removed
	 * from a model
	 */
	void propertyRemoved(IModelProperty property);
}
