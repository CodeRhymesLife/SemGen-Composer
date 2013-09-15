import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;


/*
 * Core of SemGen. Contains all information core to SemGen
 */
public class SemGen {
	// Model repository
	private ModelRepository _repository;
	
	// SemGen instance
	private static final SemGen Instance = new SemGen();
	
	public SemGen(){
		_repository = new ModelRepository();
	}
	
	/*
	 * Get instance of singleton
	 */
	public static SemGen getInstance(){
		return Instance;
	}
	
	/*
	 * Tells whether the given models are mergeable
	 */
	public static boolean isMergeable(Model model1, Model model2){
		if(model1 == null ||
			model2 == null ||
			model1 == model2)
			return false;
		
		// If model 1 is a mergeable model check whether
		// its sources are mergeable with model 2
		if(model1 instanceof MergedModel){
			MergedModel mergedModel = (MergedModel)model1;
			if(!isMergeable(mergedModel.getSourceModel1(), model2) ||
			!isMergeable(mergedModel.getSourceModel2(), model2))
				return false;
		}
		
		// If model 2 is a mergeable model check whether
		// its sources are mergeable with model 1
		if(model2 instanceof MergedModel){
			MergedModel mergedModel = (MergedModel)model2;
			if(!isMergeable(model1, mergedModel.getSourceModel1()) ||
			!isMergeable(model1, mergedModel.getSourceModel2()))
				return false;
		}
		
		return true;
	}
	
	/*
	 * Get model repository
	 */
	public ModelRepository getModelRepository(){
		return _repository;
	}
	
	/*
	 * Annotate the given model
	 */
	public void Annotate(Model model){
		throw new UnsupportedOperationException("Annotate");
	}
	
	/*
	 * Encode the given model
	 */
	public void Encode(Model model){
		throw new UnsupportedOperationException("Encode");
	}
	
	/*
	 * Extract the given model
	 */
	public void Extract(Model model){
		throw new UnsupportedOperationException("Extract");
	}
	
	/*
	 * Merge the given models
	 */
	public void Merge(Model model1, Model model2){
		if(!isMergeable(model1, model2))
			// TODO: Add error handling
			return;
		
		String name = String.format("%s %s", model1.getName(), model2.getName());
		_repository.addModel(new MergedModel(name, model1, model2));
	}
	
	/*
	 * Add model from file.
	 * If parsing errors are encountered this function will
	 * throw specific exceptions
	 */
	public Model addModelFromFile(File file) throws Exception {
		// Get the file name minus the ext
		String modelName = FilenameUtils.getBaseName(file.getName());
		
		// If we already have this model we
		// dont want to create it again
		if(_repository.hasModel(modelName))
			return null;
		
		// For now assume that this is a valid model
		// and just get the name
		// TODO: Validate model and create object from model contents
		Model newModel = CreateDummyModel(modelName);
		
		// Add to repository
		return _repository.addModel(newModel);
	}
	
	/*
	 * Creates dummy model with dummy properties
	 */
	private Model CreateDummyModel(String name){
		ArrayList<IModelProperty> modelProperties = new ArrayList<IModelProperty>();
		modelProperties.add(new ModelProperty("Property 1", "P1", "mx + 1"));
		modelProperties.add(new ModelProperty("Property 2", "P2", "mx + 2"));
		modelProperties.add(new ModelProperty("Property 3", "P3", "mx + 3"));
		modelProperties.add(new ModelProperty("Property 4", "P4", "mx + 4"));
		modelProperties.add(new ModelProperty("Property 5", "P5", "mx + 5"));
		
		return new Model(name, modelProperties);
	}
}
