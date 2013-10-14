package semGen.models;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import semGen.models.Model;
import semGen.models.ModelListener;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelProperty;


public class ModelTest {
	private static final String _name = "model name";
	private TestModelListener _actionListener;
	private ModelProperty _defaultProperty;
	private Model _model;
	
	@Before
	public void setUp() throws Exception {
		_actionListener = new TestModelListener();
		
		_model = new Model(_name);
		_defaultProperty = new ModelProperty(_model, "test name", "test var name", "test equation");
		_model.addProperty(_defaultProperty);
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullName_VerifyExceptionThrow() {
		new Model(null);
	}
	
	@Test
	public void getName_getName_VerifyCorrectNameRetrieved() {
		assertEquals("Verify name is the same name that we gave the model",
				_name, _model.getName());
	}
	
	@Test
	public void getProperties_NoProperties_VerifyEmptyList() {
		assertEquals("Verify the model has 0 properties",
				0,
				(new Model("new test model")).getProperties().size());
	}
	
	@Test
	public void getProperties_PropertyInModel_VerifyListContainsModel() {
		assertEquals("Verify the model has 1 property",
				1,
				_model.getProperties().size());
		
		assertEquals("Verify model in repository",
				_defaultProperty,
				_model.getProperties().get(0));
	}
	
	@Test
	public void getProperties_ModifyList_VerifyListInModelNotChanged() {
		// Remove the property from the list
		ArrayList<IModelProperty> properties = _model.getProperties();
		properties.clear();
		
		assertEquals("Verify all properties were removed from the list we retrieved",
				0,
				properties.size());
		
		assertEquals("Verify the model's property list was unchanged",
				1,
				_model.getProperties().size());
	}
	
	@Test
	public void addProperty_Null_VerifyFalse() {
		assertFalse("Verify we can't add null properties",
				_model.addProperty(null));
	}
	
	@Test
	public void addProperty_AlreadyInModel_VerifyFalse() {
		assertFalse("Verify properties that are already in the model are not added again",
				_model.addProperty(_defaultProperty));
	}
	
	@Test
	public void addProperty_NewProperty_VerifyTrue() {
		assertTrue("Verify a new property is added to the model successfully",
				_model.addProperty(new ModelProperty(_model, "test", "test", "test")));
	}
	
	@Test
	public void addProperty_NewModelPropertyWithDifferentParentModel_VerifyFalse() {
		assertFalse("Verify a new ModelProperty is not added to the model if it has a different parent model",
				_model.addProperty(new ModelProperty(new Model("different parent model"), "test", "test", "test")));
	}
	
	@Test
	public void addModelListener_Null_VerifyFalse() {
		assertFalse("Verify false is returned when addModelListener is called with null",
				_model.addModelListener(null));
	}
	
	@Test
	public void addModelListener_ValidListener_VerifyTrue() {
		assertTrue("Verify true is returned when addModelListener is called with a valid listener",
				_model.addModelListener(_actionListener));
	}
	
	@Test
	public void addModelListener_ValidListener_VerifyListenerCalledWhenPropertyAdded() {
		// Add listener
		_model.addModelListener(_actionListener);
		
		// Add property
		IModelProperty property = new ModelProperty(_model, "test", "test", "test");
		_model.addProperty(property);
		
		// Verify listener received a callback
		assertSame("Verify the listener was called with the correct property after the property was added to the model",
				property,
				_actionListener.LastAddedProperty);
	}
	
	@Test
	public void addModelListener_ValidListener_VerifyListenerCalledWhenPropertyRemoved() {
		// Add property
		IModelProperty property = new ModelProperty(_model, "test", "test", "test");
		_model.addProperty(property);
		
		// Add listener
		_model.addModelListener(_actionListener);
		
		// Remove property
		_model.removeProperty(property);
		
		// Verify listener received a callback
		assertSame("Verify the listener was called with the correct property after the property was removed to the model",
				property,
				_actionListener.LastRemovedProperty);
	}
	
	@Test
	public void removeModelListener_Null_VerifyFalse() {
		assertFalse("Verify false is returned when removeModelListener is called with null",
				_model.removeModelListener(null));
	}
	
	@Test
	public void removeModelListener_ExistingListener_VerifyTrue() {
		// Add listener
		_model.addModelListener(_actionListener);
		
		assertTrue("Verify true returned when removeModelListener is called with an existing listener",
				_model.removeModelListener(_actionListener));
	}
	
	@Test
	public void removeModelListener_NonExistingListener_VerifyFalse() {
		assertFalse("Verify false returned when removeModelListener is called with a non-existent listener",
				_model.removeModelListener(new TestModelListener()));
	}
	
	@Test
	public void removeModelListener_ExistingListener_VerifyListenerNotCalledWhenPropertyRemoved() {
		// Add listener
		_model.addModelListener(_actionListener);
		
		// Remove listener
		_model.removeModelListener(_actionListener);
		
		// Add property
		IModelProperty property = new ModelProperty(_model, "test", "test", "test");
		_model.addProperty(property);
		
		// Verify listener not called with property
		assertNull("Verify the listener was not called after it was removed",
				_actionListener.LastAddedProperty);
	}
	
	/*
	 * Model action listener used for unit tests
	 */
	private class TestModelListener implements ModelListener{

		public IModelProperty LastAddedProperty;
		public IModelProperty LastRemovedProperty;
		
		@Override
		public void propertyAdded(IModelProperty property) {
			LastAddedProperty = property;
		}

		@Override
		public void propertyRemoved(IModelProperty property) {
			LastRemovedProperty = property;
		}		
	}
}
