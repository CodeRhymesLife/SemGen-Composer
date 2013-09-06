/*
 * Listens for model repository actions
 */
public interface ModelRepositoryActionListener {
	/*
	 * Handles model added event
	 */
	void modelAdded(Model model);
	
	/*
	 * Handles model removed event
	 */
	void modelRemoved(Model model);
}