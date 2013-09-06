import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ModelRepositoryTest {
	private Model _model;
	private TestModelRepositoryActionListener _listener;
	private ModelRepository _repository;
	
	@Before
	public void setUp() throws Exception {
		_model = new Model("test model");
		_listener = new TestModelRepositoryActionListener();
		_repository = new ModelRepository();
	}

	@Test
	public void addModel_NullModel_VerifyFalse() {
		assertFalse("Verify null not added to model repository",
				_repository.addModel(null));
	}
	
	@Test
	public void addModel_ValidModel_VerifyTrue() {
		assertTrue("Verify valid model successfully added to repository",
				_repository.addModel(_model));
	}
	
	@Test
	public void addModel_ModelAlreadyInRepository_VerifyFalse() {
		// Add model
		_repository.addModel(_model);
		
		assertFalse("Verify models are not added if they already exist in the repository",
				_repository.addModel(_model));
	}
	
	@Test
	public void addModel_ValidModel_VerifyListenerCalledWhenModelAdded() {
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Add Model
		_repository.addModel(_model);
		
		assertSame("Verify the listener was called with the correct model",
				_model,
				_listener.LastAddedModel);
	}
	
	@Test
	public void removeModel_Null_VerifyFalse() {
		assertFalse("Verify null models cannot be removed",
				_repository.removeModel(null));
	}
	
	@Test
	public void removeModel_ExistingModel_VerifyTrue() {
		// Add model
		_repository.addModel(_model);
		
		assertTrue("Verify existing models can be removed",
				_repository.removeModel(_model));
	}
	
	@Test
	public void removeModel_ModelNotInRepository_VerifyFalse() {
		assertFalse("Verify models that are not in the repository are not removed",
				_repository.removeModel(_model));
	}
	
	@Test
	public void removeModel_ExistingModel_VerifyListenerCalledWhenModelRemoved() {
		// Add model
		_repository.addModel(_model);
		
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		// Remove model
		_repository.removeModel(_model);

		assertSame("Verify listener called with correct model when model removed",
				_model,
				_listener.LastRemovedModel);
	}
	
	@Test
	public void addModelRepositoryActionListener_Null_VerifyFalse() {
		assertFalse("Verify null listeners are not added to repository",
				_repository.addModelRepositoryActionListener(null));
	}
	
	@Test
	public void addModelRepositoryActionListener_ValidListener_VerifyTrue() {
		assertTrue("Verify valid listeners are added to repository",
				_repository.addModelRepositoryActionListener(_listener));
	}
	
	@Test
	public void removeModelRepositoryActionListener_Null_VerifyFalse() {
		assertFalse("Verify null listeners are not added to repository",
				_repository.removeModelRepositoryActionListener(null));
	}
	
	@Test
	public void removeModelRepositoryActionListener_ExistingListener_VerifyTrue() {
		// Add listener
		_repository.addModelRepositoryActionListener(_listener);
		
		assertTrue("Verify existing listener removed from repository",
				_repository.removeModelRepositoryActionListener(_listener));
	}
	
	/*
	 * Model repository action listener used for unit tests
	 */
	private class TestModelRepositoryActionListener implements ModelRepositoryActionListener{

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
}
