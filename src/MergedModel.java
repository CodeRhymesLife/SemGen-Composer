import java.util.Collection;


public class MergedModel extends Model {
	// Source models
	private Model _sourceModel1;
	private Model _sourceModel2;
	
	public MergedModel(String name, Collection<IModelProperty> properties, Model sourceModel1, Model sourceModel2) {
		super(name, properties);
		if(sourceModel1 == null)
			throw new NullPointerException("sourceModel1");
		
		if(sourceModel2 == null)
			throw new NullPointerException("sourceModel2");
	}
}
