package semGen;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.io.FilenameUtils;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.ModelRepository;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelProperty;


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
	 * Clone the given model
	 */
	public void Clone(Model model){
		throw new UnsupportedOperationException("Clone");
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
		
		String newModelName = String.format("%s + %s", model1.getName(), model2.getName());
		
		MergedModel newMergedModel = new MergedModel(newModelName, model1, model2);
		
		// Add auto mapped properties
		addAutoMappedProperties(newMergedModel, model1, model2);
		
		_repository.addModel(newMergedModel);
	}
	
	/*
	 * Add model from file.
	 * If parsing errors are encountered this function will
	 * throw specific exceptions
	 */
	public Model addModelFromFile(File file) {
		// Get the file name minus the ext
		String modelName = FilenameUtils.getBaseName(file.getName());
		
		// If we already have this model we
		// dont want to create it again
		if(_repository.hasModel(modelName))
			return null;
		
		Model newModel = null;
		if(modelName.toLowerCase().equals("baroreceptor")){
			newModel = new Model(modelName);
			newModel.addProperty(new ModelProperty(newModel, "Heart Rate", "HR", "HR = h1 + h2^2 + a3"));
			newModel.addProperty(new ModelProperty(newModel, "Aortic Blood Pressure", "Pao", "Pao = 93"));
		}
		else if(modelName.toLowerCase().equals("cardiovasculardynamics")){
			newModel = new Model(modelName);
			newModel.addProperty(new ModelProperty(newModel, "heart rate", "hrate", "hrate = 77"));
			newModel.addProperty(new ModelProperty(newModel, "aortic pressure", "P", "P = (V - V0) / C"));
		}
		else
		{
			// For now assume that this is a valid model
			// and just get the name
			// TODO: Validate model and create object from model contents
			newModel = CreateDummyModel(modelName);
		}
		
		
		// Add to repository
		return _repository.addModel(newModel);
	}
	
	/*
	 * Creates dummy model with dummy properties
	 */
	private Model CreateDummyModel(String name){
		Model dummyModel = new Model(name);
		dummyModel.addProperty(new ModelProperty(dummyModel, "Property 1", "P1", "mx + 1"));
		dummyModel.addProperty(new ModelProperty(dummyModel, "Property 2", "P2", "mx + 2"));
		dummyModel.addProperty(new ModelProperty(dummyModel, "Property 3", "P3", "mx + 3"));
		dummyModel.addProperty(new ModelProperty(dummyModel, "Property 4", "P4", "mx + 4"));
		dummyModel.addProperty(new ModelProperty(dummyModel, "Property 5", "P5", "mx + 5"));
		
		return dummyModel;
	}
	
	/*
	 * Add automapped properties to the merged model
	 */
	private void addAutoMappedProperties(MergedModel mergedModel, Model model1, Model model2){
		// Test code
		// TODO: Write real logic
		// TODO: Add unit tests
		ArrayList<IModelProperty> model1Properties = model1.getProperties();
		ArrayList<IModelProperty> model2Properties = model2.getProperties();
		int maxPropertiesToMap = Math.min(2, Math.min(model1Properties.size(), model2Properties.size()));
		for(int i = 0; i < maxPropertiesToMap; i++){
			mergedModel.addProperty(new MergedModelProperty(mergedModel, model1Properties.get(i), model2Properties.get(i)));
		}
	}
}
