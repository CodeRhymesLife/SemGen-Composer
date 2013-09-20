package semGen.models.properties;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import semGen.models.properties.ModelProperty;


public class ModelPropertyTest {
	private final String _name = "TestName";
	private final String _variableName = "X";
	private final String _equation = "TestEquation";
	private ModelProperty _modelProperty;
	
	@Before
	public void setUp(){
		_modelProperty = new ModelProperty(_name, _variableName, _equation);
	}
	
	@Test (expected = NullPointerException.class)
	public void Constructor_NullName_VerifyExceptionThrown() {
		new ModelProperty(null, _variableName, _equation);
	}
	
	@Test (expected = NullPointerException.class)
	public void Constructor_NullVariableName_VerifyExceptionThrown() {
		new ModelProperty(_name, null, _equation);
	}
	
	@Test (expected = NullPointerException.class)
	public void Constructor_NullEquation_VerifyExceptionThrown() {
		new ModelProperty(_name, _variableName, null);
	}
	
	@Test
	public void getName_Call_VerifyNameIsTheNamePassedInTheConstructor() {
		assertEquals(_name, _modelProperty.getName());;
	}

	@Test
	public void getVariableName_Call_VerifyVariableNameIsTheNamePassedInTheConstructor() {
		assertEquals(_variableName, _modelProperty.getVariableName());
	}
	
	@Test
	public void getEquation_Call_VerifyEquationIsTheNamePassedInTheConstructor() {
		assertEquals(_equation, _modelProperty.getEquation());
	}
}
