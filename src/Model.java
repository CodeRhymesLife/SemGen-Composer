import java.util.Dictionary;

/**
 * Holds information about a model.
 * 
 * @author rjames
 *
 */
public class Model {
	
	public Model(String name, IModelProperty[] properties){
		
	}
	
	/*
	 * Get model name
	 * 
	 * Returns:
	 * Model name
	 */
	public String getName(){
		return null;
	}
	
	/*
	 * Add property to model.
	 * 
	 * Returns:
	 * True if model was successfully added. False otherwise
	 */
	public boolean addProperty(IModelProperty property){
		return false;
	}
	
	/*
	 * Remove the model property from this model
	 * 
	 * Returns:
	 * True if model was successfully removed. False otherwise
	 */
	public boolean removeProperty(IModelProperty property){
		return false;
	}
	
	/*
	 * Add listener for model actions
	 */
	public boolean addModelActionListener(ModelActionListener listener){
		return false;
	}
	
	/*
	 * Remove listener for model actions
	 */
	public boolean removeModelActionListener(ModelActionListener listener){
		return false;
	}
}
