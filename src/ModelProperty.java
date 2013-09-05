/**
 * Property for models. Exposes information about
 * a property on a model
 * 
 * @author rjames
 *
 */
public class ModelProperty implements IModelProperty {
	/*
	 * Property name
	 */
	private final String _name;
	
	/*
	 * Variable name. AKA code word
	 */
	private final String _variableName;
	
	/*
	 * Equation used to calculate this property's value
	 */
	private final String _equation;
	
	public ModelProperty(String name, String variableName, String equation){
		if(name == null)
			throw new NullPointerException("name");
		
		if(variableName == null)
			throw new NullPointerException("variableName");
		
		if(equation == null)
			throw new NullPointerException("equation");
		
		_name = name;
		_variableName = variableName;
		_equation = equation;
	}
	
	/*
	 * Get the name of this property
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getName()
	 */
	@Override
	public String getName() {
		return _name;
	}

	/*
	 * Gets the variable name
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getVariableName()
	 */
	@Override
	public String getVariableName() {
		return _variableName;
	}

	/*
	 * Get the value of this property
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getEquation()
	 */
	@Override
	public String getEquation() {
		return _equation;
	}
}
