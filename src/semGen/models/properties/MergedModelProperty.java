package semGen.models.properties;
import java.security.InvalidParameterException;

import semGen.models.MergedModel;
import semGen.models.Model;

/**
 * Property for merged models. When a model is
 * merged properties from each model must be mapped and
 * a source property is used. This class takes care of choosing
 * the value from the source property
 * 
 * @author rjames
 *
 */
public class MergedModelProperty implements IModelProperty {

	private IModelProperty _property1;
	private IModelProperty _property2;

	// Property used as the source to retrieve information
	// aobut this merged property
	private IModelProperty _sourceProperty;
	
	// Parent model
	private MergedModel _parentModel;
	
	public MergedModelProperty(MergedModel parent, IModelProperty property1, IModelProperty property2){
		if(parent == null)
			throw new NullPointerException("parent");
		
		if(property1 == null)
			throw new NullPointerException("property1");
		
		if(property2 == null)
			throw new NullPointerException("property2");
		
		_parentModel = parent;
		_property1 = property1;
		_property2 = property2;
	}
	
	/*
	 * Set the source property
	 */
	public void setSourceProperty(IModelProperty newSourceProperty){
		if(newSourceProperty == null)
			throw new NullPointerException("newSourceProperty");
		
		// Throw and exception if we are trying to set a source property
		// that we don't know about
		if(newSourceProperty != _property1 && newSourceProperty != _property2)
			throw new InvalidParameterException("newSourceProperty");
		
		_sourceProperty = newSourceProperty;
	}
	
	/*
	 * Get the source property
	 */
	public IModelProperty getSourceProperty(){
		return _sourceProperty;
	}
	
	/*
	 * Get property 1
	 */
	public IModelProperty getProperty1(){
		return _property1;
	}
	
	/*
	 * Get property 2
	 */
	public IModelProperty getProperty2(){
		return _property2;
	}
	
	/**
	 * Gets the source property's parent model
	 */
	@Override
	public Model getParentModel() {
		return _parentModel;
	}
	
	/* 
	 * Gets the source property's name
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getName()
	 */
	@Override
	public String getName() {
		return _sourceProperty == null ? null : _sourceProperty.getName();
	}

	/* Gets the source property's variable name
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getVariableName()
	 */
	@Override
	public String getVariableName() {
		return _sourceProperty == null ? null : _sourceProperty.getVariableName();
	}

	/* Gets the source property's equation
	 * 
	 * (non-Javadoc)
	 * @see IModelProperty#getEquation()
	 */
	@Override
	public String getEquation() {
		return _sourceProperty == null ? null : _sourceProperty.getEquation();
	}
}
