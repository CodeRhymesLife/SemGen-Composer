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
	 */
	public String getName(){
		return null;
	}
	
	/*
	 * Add property to model
	 */
	public boolean addProperty(IModelProperty property){
		return false;
	}
	
	/*
	 * Remove the model property from this model
	 */
	public boolean removeProperty(IModelProperty property){
		return false;
	}
	
	/*
	 * Add listener for model actions
	 */
	public void addModelActionListener(ModelActionListener listener){
		
	}
	
	/*
	 * Remove listener for model actions
	 */
	public void removeModelActionListener(ModelActionListener listener){
		
	}
}
