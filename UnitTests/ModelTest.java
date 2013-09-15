import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class ModelTest {
	private String _name;
	private ArrayList<IModelProperty> _properties;
	private TestModelListener _actionListener;
	private Model _model;
	
	@Before
	public void setUp() throws Exception {
		_name = "test model name";
		_properties = new ArrayList<IModelProperty>();
		_properties.add(new ModelProperty("test name", "test var name", "test equation"));
		_actionListener = new TestModelListener();
		
		_model = new Model(_name, _properties);
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullName_VerifyExceptionThrow() {
		new Model(null, _properties);
	}
	
	@Test
	public void Constructor_NullProperties_VerifyNoExceptionThrow() {
		new Model(_name);
		new Model(_name, null);
		
		assertTrue("Verify passing in null properties doesnt throw an exception",
				true);
	}
	
	@Test
	public void getName_getName_VerifyCorrectNameRetrieved() {
		assertEquals("Verify name is the same name that we gave the model",
				_name, _model.getName());
	}
	
	@Test
	public void addProperty_Null_VerifyFalse() {
		assertFalse("Verify we can't add null properties",
				_model.addProperty(null));
	}
	
	@Test
	public void addProperty_AlreadyInModel_VerifyFalse() {
		assertFalse("Verify properties that are already in the model are not added again",
				_model.addProperty(_properties.get(0)));
	}
	
	@Test
	public void addProperty_NewProperty_VerifyTrue() {
		assertTrue("Verify a new property is added to the model successfully",
				_model.addProperty(new ModelProperty("test", "test", "test")));
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
		IModelProperty property = new ModelProperty("test", "test", "test");
		_model.addProperty(property);
		
		// Verify listener received a callback
		assertSame("Verify the listener was called with the correct property after the property was added to the model",
				property,
				_actionListener.LastAddedProperty);
	}
	
	@Test
	public void addModelListener_ValidListener_VerifyListenerCalledWhenPropertyRemoved() {
		// Add property
		IModelProperty property = new ModelProperty("test", "test", "test");
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
		IModelProperty property = new ModelProperty("test", "test", "test");
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
