package semGen.models.properties;
import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Test;

import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelProperty;


public class MergedModelPropertyTest {
	private static IModelProperty _property1;
	private static IModelProperty _property2;
	
	private static MergedModelProperty _mergedModelProperty;
	
	/*
	 * Setup properties
	 */
	@Before
	public void setUp() {
		_property1 = new ModelProperty("prop 1 test name", "prop 1 test var name", "prop 1 test equation");
		_property2 = new ModelProperty("prop 2 test name", "prop 2 test var name", "prop 2 test equation");
		
		_mergedModelProperty = new MergedModelProperty(_property1, _property2);
	}
	
	@Test (expected = NullPointerException.class)
	public void MergedModelProperty_NullProperty1_VerifyException(){
		new MergedModelProperty(null, _property2);
	}
	
	@Test (expected = NullPointerException.class)
	public void MergedModelProperty_NullProperty2_VerifyException(){
		new MergedModelProperty(_property1, null);
	}

	@Test
	public void setSourceProperty_SetValidSourceProperty_VerifySourcePropertySet() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals(_property1, _mergedModelProperty.getSourceProperty());
	}
	
	@Test
	public void setSourceProperty_SetValidSourcePropertyTwice_VerifySourcePropertySet() {
		_mergedModelProperty.setSourceProperty(_property1);
		_mergedModelProperty.setSourceProperty(_property2);
		
		assertEquals(_property2, _mergedModelProperty.getSourceProperty());
	}
	
	@Test (expected = NullPointerException.class)
	public void setSourceProperty_SetNullSourceProperty_VerifyExceptionThrown() {
		_mergedModelProperty.setSourceProperty(null);
	}
	
	@Test (expected = InvalidParameterException.class)
	public void setSourceProperty_SetInalidSourceProperty_VerifyExceptionThrown() {
		MergedModelProperty invalidPropertyToSetAsSource = new MergedModelProperty(_property1, _property2);
		_mergedModelProperty.setSourceProperty(invalidPropertyToSetAsSource);
	}

	@Test
	public void getSourceProperty_NoSourceProperty_VerifyNull() {
		assertNull(_mergedModelProperty.getSourceProperty());
	}
	
	@Test
	public void getSourceProperty_SetSourceProperty_VerifySourcePropertyIsThePropertyThatWasSet() {
		_mergedModelProperty.setSourceProperty(_property1);

		assertEquals(_property1, _mergedModelProperty.getSourceProperty());
	}
	
	@Test
	public void getProperty1_getProperty1_VerifyProperty1Retrieved() {
		assertEquals(_property1, _mergedModelProperty.getProperty1());
	}
	
	@Test
	public void getProperty2_getProperty2_VerifyProperty2Retrieved() {
		assertEquals(_property2, _mergedModelProperty.getProperty2());
	}
	
	@Test
	public void getName_NoSourceProperty_VerifyNull() {
		assertNull("Verify getName returns null when no source property is set",
				_mergedModelProperty.getName());
	}
	
	@Test
	public void getName_SetSourceProperty_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getName returns the source propery's vlaue",
				_property1.getName(), _mergedModelProperty.getName());
	}
	
	@Test
	public void getVariableName_NoSourceProperty_VerifyNull() {
		assertNull("Verify getVariableName returns null when no source property is set",
				_mergedModelProperty.getVariableName());
	}
	
	@Test
	public void getVariableName_SetSourceProperty_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getVariableName returns the source propery's vlaue",
				_property1.getVariableName(), _mergedModelProperty.getVariableName());
	}
	
	@Test
	public void getEquation_NoSourceProperty_VerifyNull() {
		assertNull("Verify getEquation returns null when no source property is set",
				_mergedModelProperty.getEquation());
	}
	
	@Test
	public void getEquation_SetSourceProperty_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getEquation returns the source propery's vlaue",
				_property1.getEquation(), _mergedModelProperty.getEquation());
	}
}
