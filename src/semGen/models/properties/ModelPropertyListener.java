package semGen.models.properties;

/*
 * Listener for property events
 */
public interface ModelPropertyListener {

	/*
	 * Called when a property event occurs
	 */
	public void propertyEvent(IModelProperty property);
}
