import semGen.models.Model;
import semGen.models.ModelRepositoryActionListener;


/*
 * Model repository action listener used for unit tests
 */
class TestModelRepositoryActionListener implements ModelRepositoryActionListener{

	public Model LastAddedModel;
	public Model LastRemovedModel;

	@Override
	public void modelAdded(Model model) {
		LastAddedModel = model;
	}

	@Override
	public void modelRemoved(Model model) {
		LastRemovedModel = model;
	}
}