package semGen.models;
import java.util.Collection;

import semGen.models.properties.IModelProperty;


public class MergedModel extends Model {
	// Source models
	private Model _sourceModel1;
	private Model _sourceModel2;
	
	public MergedModel(String name, Model sourceModel1, Model sourceModel2){
		this(name, null, sourceModel1, sourceModel2);
	}
	
	public MergedModel(String name, Collection<IModelProperty> properties, Model sourceModel1, Model sourceModel2) {
		super(name, properties);
		if(sourceModel1 == null)
			throw new NullPointerException("sourceModel1");
		
		if(sourceModel2 == null)
			throw new NullPointerException("sourceModel2");
		
		_sourceModel1 = sourceModel1;
		_sourceModel2 = sourceModel2;
	}
	
	/*
	 * Gets source model 1
	 */
	public Model getSourceModel1(){
		return _sourceModel1;
	}
	
	/*
	 * Gets source model 2
	 */
	public Model getSourceModel2(){
		return _sourceModel2;
	}
}
