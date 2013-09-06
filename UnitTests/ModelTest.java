import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ModelTest {
	private String _name;
	private IModelProperty[] _properties;
	private TestModelActionListener _actionListener;
	private Model _model;
	
	@Before
	public void setUp() throws Exception {
		_name = "test model name";
		_properties = new ModelProperty[1];
		_properties[0] = new ModelProperty("test name", "test var name", "test equation");
		
		_model = new Model(_name, _properties);
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullName_VerifyExceptionThrow() {
		new Model(null, new ModelProperty[1]);
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullProperties_VerifyExceptionThrow() {
		new Model("some name", null);
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
				_model.addProperty(_properties[0]));
	}
	
	@Test
	public void addProperty_NewProperty_VerifyTrue() {
		assertTrue("Verify a new property is added to the model successfully",
				_model.addProperty(new ModelProperty("test", "test", "test")));
	}
	
	@Test
	public void addModelActionListener_Null_VerifyFalse() {
		assertTrue("Verify false is returned when addModelActionListener is called with null",
				_model.addModelActionListener(null));
	}
	
	@Test
	public void addModelActionListener_ValidListener_VerifyListenerAdded() {
		assertTrue("Verify true is returned when addModelActionListener is called with a valid listener",
				_model.addModelActionListener(_actionListener));
	}
	
	@Test
	public void addModelActionListener_ValidListener_VerifyListenerCalledWhenPropertyAdded() {
		// Add listener
		_model.addModelActionListener(_actionListener);
		
		// Add property
		IModelProperty property = new ModelProperty("test", "test", "test");
		_model.addProperty(property);
		
		// Verify listener received a callback
		assertSame("Verify the listener was called with the correct property after the property was added to the model",
				property,
				_actionListener.LastAddedProperty);
	}
	
	@Test
	public void removeModelActionListener_Null_VerifyFalse() {
		assertTrue("Verify false is returned when removeModelActionListener is called with null",
				_model.removeModelActionListener(null));
	}
	
	@Test
	public void removeModelActionListener_ExistingListener_VerifyTrue() {
		fail("Not yet implemented");
	}
	
	@Test
	public void removeModelActionListener_NonExistingListener_VerifyFalse() {
		fail("Not yet implemented");
	}
	
	@Test
	public void removeModelActionListener_ExistingListener_VerifyListenerNotCalledWhenPropertyRemoved() {
		fail("Not yet implemented");
	}
	
	/*
	 * Model action listener used for unit tests
	 */
	private class TestModelActionListener implements ModelActionListener{

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
