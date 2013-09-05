import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ModelTest {
	private String _name;
	private IModelProperty[] _properties;
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
	
	@Test (expected = NullPointerException.class)
	public void addModelActionListener_Null_VerifyExceptionThrown() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addModelActionListener_ValidListener_VerifyListenerAdded() {
		fail("Not yet implemented");
	}
	
	@Test (expected = NullPointerException.class)
	public void removeModelActionListener_Null_VerifyExceptionThrow() {
		fail("Not yet implemented");
	}
	
	@Test
	public void removeModelActionListener_ValidListener_VerifyListenerRemoved() {
		fail("Not yet implemented");
	}
}
