package semGen.models.properties;

import semGen.models.Model;

/*
 * Interface for model properties
 */
public interface IModelProperty {
	/**
	 * Gets the parent model
	 * @return Parent model
	 */
	Model getParentModel();
	
	/*
	 * Get the name of this property
	 */
	String getName();
	
	/*
	 * Gets the variable name
	 */
	String getVariableName();
	
	/*
	 * Get the value of this property
	 */
	String getEquation();
}
