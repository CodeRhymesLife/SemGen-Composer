import java.util.ArrayList;
import java.util.Iterator;

/*
 * Holds models and fires events when
 * models are added and removed
 */
public class ModelRepository {
	/*
	 * Collection of models mapped
	 * 
	 * Note: If we run into perf issues use hashmap.
	 * Not sure why but mapping the model name to key
	 * wasn't working for me. Entries were'nt getting
	 * added to the map
	 */
	private ArrayList<Model> _models;
	
	/*
	 * Collection of listeners listening for
	 * model repository events
	 */
	private ArrayList<ModelRepositoryActionListener> _listeners;
	
	public ModelRepository(){
		_models = new ArrayList<>();
		_listeners = new ArrayList<>();
	}
	
	/*
	 * Tells whether the given model is in the repository
	 */
	public boolean hasModel(String modelName){
		// Check to see if this model is in
		// the repository
		// TODO: This is slow, lookout for perf issues
		for(Iterator<Model> i = _models.iterator(); i.hasNext(); ) {
			Model model = i.next();
			
			// Do we have this model?
			if(model.getName().equals(modelName))
				return true;
		}
		
		// We didnt find the model
		return false;
	}
	
	/*
	 * Adds model to repository
	 */
	public Model addModel(Model model){
		if(model == null ||
		_models.contains(model) ||
		!_models.add(model))
			return null;
		
		InformListenersAboutModelAction(ModelRepositoryAction.ModelAdded, model);
		return model;
	}
	
	/*
	 * Removes model from repository
	 */
	public boolean removeModel(Model model){
		if(model == null ||
		!_models.remove(model))
			return false;
		
		InformListenersAboutModelAction(ModelRepositoryAction.ModelRemoved, model);
		return true;
	}
	
	/*
	 * Adds listener for model repository actions
	 */
	public boolean addModelRepositoryActionListener(ModelRepositoryActionListener listener){
		if(listener == null)
			return false;
		
		return _listeners.add(listener);
	}
	
	/*
	 * Removes listener for model repository actions
	 */
	public boolean removeModelRepositoryActionListener(ModelRepositoryActionListener listener){
		if(listener == null)
			return false;
		
		return _listeners.remove(listener);
	}
	
	/*
	 * Inform each listener about the model repository action
	 */
	private void InformListenersAboutModelAction(ModelRepositoryAction action, Model model){
		for(Iterator<ModelRepositoryActionListener> i = _listeners.iterator(); i.hasNext(); ) {
			ModelRepositoryActionListener listener = i.next();
			
			switch(action){
				case ModelAdded:
					listener.modelAdded(model);
				case ModelRemoved:
					listener.modelRemoved(model);
			}
		}
	}
	
	/*
	 * Actions the model can take that listeners can listen for
	 */
	private enum ModelRepositoryAction{
		ModelAdded,
		ModelRemoved
	}
}
