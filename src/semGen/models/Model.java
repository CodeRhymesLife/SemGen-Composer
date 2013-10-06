package semGen.models;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import semGen.models.properties.IModelProperty;
import semGen.models.properties.ModelProperty;

/**
 * Holds information about a model.
 * 
 * @author rjames
 *
 */
public class Model {
	// Model name
	private String _name;
	
	// Model properties
	// TODO: Run perf tests to see if this needs to change to a dictionary
	// for quicker retrieval
	private ArrayList<IModelProperty> _properties;
	
	// Listeners for model actions
	private ArrayList<ModelListener> _listeners;
	
	public Model(String name){
		if(name == null)
			throw new NullPointerException("name");
		
		_name = name;
		_properties = new ArrayList<>();
		_listeners = new ArrayList<>();
	}
	
	/*
	 * Get model name
	 * 
	 * Returns:
	 * Model name
	 */
	public String getName(){
		return _name;
	}
	
	/*
	 * Gets a list of model properties that are in this model
	 * 
	 * NOTE: To ensure encapsulation this function returns
	 * a copy of the internal list. Use sparingly.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<IModelProperty> getProperties(){
		return (ArrayList<IModelProperty>) _properties.clone();
	}
	
	/*
	 * Add property to model.
	 * 
	 * Returns:
	 * True if model was successfully added. False otherwise
	 */
	public boolean addProperty(IModelProperty property){
		if(property == null ||
		_properties.contains(property) ||
		!_properties.add(property))
			return false;
		
		// If this is a ModelProperty and it's parent is not this
		// model do not add it
		if(property instanceof ModelProperty &&
		property.getParentModel() != this)
			return false;
		
		InformListenersAboutModelAction(ModelAction.PropertyAdded, property);
		return true;
	}
	
	/*
	 * Remove the model property from this model
	 * 
	 * Returns:
	 * True if model was successfully removed. False otherwise
	 */
	public boolean removeProperty(IModelProperty property){
		if(property == null || !_properties.remove(property))
			return false;
		
		InformListenersAboutModelAction(ModelAction.PropertyRemoved, property);
		return true;
	}
	
	/*
	 * Add listener for model actions
	 */
	public boolean addModelListener(ModelListener listener){
		if(listener == null)
			return false;
		
		return _listeners.add(listener);
	}
	
	/*
	 * Remove listener for model actions
	 */
	public boolean removeModelListener(ModelListener listener){
		if(listener == null)
			return false;
		
		return _listeners.remove(listener);
	}
	
	/*
	 * Inform each listener about the model action
	 */
	private void InformListenersAboutModelAction(ModelAction action, IModelProperty property){
		for(Iterator<ModelListener> i = _listeners.iterator(); i.hasNext(); ) {
			ModelListener listener = i.next();
			
			switch(action){
				case PropertyAdded:
					listener.propertyAdded(property);
					break;
				case PropertyRemoved:
					listener.propertyRemoved(property);
					break;
			}
		}
	}
	
	/*
	 * Actions the model can take that listeners can listen for
	 */
	private enum ModelAction{
		PropertyAdded,
		PropertyRemoved
	}
}
