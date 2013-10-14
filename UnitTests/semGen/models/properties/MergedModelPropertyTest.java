package semGen.models.properties;
import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.junit.Before;
import org.junit.Test;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelProperty;


public class MergedModelPropertyTest {
	private MergedModel _model;
	private IModelProperty _property1;
	private IModelProperty _property2;

	private static MergedModelProperty _mergedModelProperty;
	
	/*
	 * Setup properties
	 */
	@Before
	public void setUp() {
		_model = new MergedModel("test merged model", new Model("test model 1"), new Model("test model 2"));
		_property1 = new ModelProperty(new Model("parent model 1"), "prop 1 test name", "prop 1 test var name", "prop 1 test equation");
		_property2 = new ModelProperty(new Model("parent model 2"), "prop 2 test name", "prop 2 test var name", "prop 2 test equation");
		
		_mergedModelProperty = new MergedModelProperty(_model, _property1, _property2);
	}
	
	@Test (expected = NullPointerException.class)
	public void MergedModelProperty_NullModel_VerifyException(){
		new MergedModelProperty(null, _property1, _property2);
	}
	
	@Test (expected = NullPointerException.class)
	public void MergedModelProperty_NullProperty1_VerifyException(){
		new MergedModelProperty(_model, null, _property2);
	}
	
	@Test (expected = NullPointerException.class)
	public void MergedModelProperty_NullProperty2_VerifyException(){
		new MergedModelProperty(_model, _property1, null);
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
		MergedModelProperty invalidPropertyToSetAsSource = new MergedModelProperty(_model, _property1, _property2);
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
	public void getParentModel_GetModelSetInConstructor_VerifyParentModelCorrect() {
		assertEquals("Verify the parent model is the model that was set in the constructor",
				_model,
				_mergedModelProperty.getParentModel());
	}
	
	@Test
	public void getName_NoSourceProperty_VerifyNull() {
		assertNull("Verify getName returns null when no source property is set",
				_mergedModelProperty.getName());
	}
	
	@Test
	public void getName_SetSourcePropertyToProperty1_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getName returns the source propery's vlaue",
				_property1.getName(), _mergedModelProperty.getName());
	}
	
	@Test
	public void getName_SetSourcePropertyToProperty2_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property2);
		
		assertEquals("Verify getName returns the source propery's vlaue",
				_property2.getName(), _mergedModelProperty.getName());
	}
	
	@Test
	public void getVariableName_NoSourceProperty_VerifyNull() {
		assertNull("Verify getVariableName returns null when no source property is set",
				_mergedModelProperty.getVariableName());
	}
	
	@Test
	public void getVariableName_SetSourcePropertyToProperty1_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getVariableName returns the source propery's vlaue",
				_property1.getVariableName(), _mergedModelProperty.getVariableName());
	}
	
	@Test
	public void getVariableName_SetSourcePropertyToProperty2_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property2);
		
		assertEquals("Verify getVariableName returns the source propery's vlaue",
				_property2.getVariableName(), _mergedModelProperty.getVariableName());
	}
	
	@Test
	public void getEquation_NoSourceProperty_VerifyNull() {
		assertNull("Verify getEquation returns null when no source property is set",
				_mergedModelProperty.getEquation());
	}
	
	@Test
	public void getEquation_SetSourcePropertyToProperty1_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property1);
		
		assertEquals("Verify getEquation returns the source propery's vlaue",
				_property1.getEquation(), _mergedModelProperty.getEquation());
	}
	
	@Test
	public void getEquation_SetSourcePropertyToProperty2_VerifySourcePropertyValueRetrieved() {
		_mergedModelProperty.setSourceProperty(_property2);
		
		assertEquals("Verify getEquation returns the source propery's vlaue",
				_property2.getEquation(), _mergedModelProperty.getEquation());
	}
}
