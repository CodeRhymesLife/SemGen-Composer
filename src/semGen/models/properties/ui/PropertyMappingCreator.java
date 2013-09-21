package semGen.models.properties.ui;

import java.awt.Container;
import java.util.ArrayList;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelPropertyListener;
import semGen.ui.ComposerJFrame;
import ui.FlyoutComponent;
import ui.FlyoutPosition;

/*
 * Creates a property mapping
 */
public class PropertyMappingCreator implements ModelPropertyListener {
	// Flyout used to select properties
	private static PropertyMappingsFlyout _propertiesFlyout;

	// Component representing the property mapping
	private PropertyMappingComponent _propertyMappingComponent;
	
	// Model to create property mapping for
	private MergedModel _mergedModel;
	
	public PropertyMappingCreator(MergedModel mergedModel){
		if(mergedModel == null)
			throw new NullPointerException("mergedModel");
		
		_mergedModel = mergedModel;
		
		// If the flyout hasn't been created yet create it
		if(_propertiesFlyout == null){
			_propertiesFlyout = new PropertyMappingsFlyout();
			Container flyoutParent = ComposerJFrame.getInstance().getContentPane();
			flyoutParent.add(_propertiesFlyout);
			flyoutParent.setComponentZOrder(_propertiesFlyout, 0);
		}
	}
	
	/*
	 * Create property mapping
	 */
	public void create(Container propertyMappingComponenetParent){
		if(propertyMappingComponenetParent == null)
			throw new NullPointerException("propertyMappingComponenetParent");
		
		// Create and add the property mapping component
		_propertyMappingComponent = new PropertyMappingComponent();
		propertyMappingComponenetParent.add(_propertyMappingComponent);
		propertyMappingComponenetParent.validate();
		propertyMappingComponenetParent.repaint();
		
		// Pick from source 1 first
		ModelPropertiesTable propertiesTable = _propertiesFlyout.getPropertiesTable();
		propertiesTable.setProperties(_mergedModel.getSourceModel1().getProperties());
		
		// Listen for the property selection event
		_propertiesFlyout.getPropertiesTable().setPropertySelectionListener(this);
		
		// Show the flyout around the first property component
		_propertiesFlyout.showAroundComponent(_propertyMappingComponent, FlyoutPosition.Left);
	}
	
	/*
	 * Called when a model property is selected from the property list.
	 * 
	 * (non-Javadoc)
	 * @see semGen.models.properties.ModelPropertyListener#propertyEvent(semGen.models.properties.IModelProperty)
	 */
	@Override
	public void propertyEvent(IModelProperty property) {
		throw new UnsupportedOperationException("Handle property selected");
	}
	
	/*
	 * Flyout for property mappings
	 */
	private class PropertyMappingsFlyout extends FlyoutComponent{
		// Lists properties
		private ModelPropertyListPanel _propertyList;
		
		public PropertyMappingsFlyout(){
			_propertyList = new ModelPropertyListPanel();			
			this.getContentPanel().add(_propertyList);
		}
		
		/*
		 * Get the model properties table
		 */
		public ModelPropertiesTable getPropertiesTable(){
			return _propertyList.getTable();
		}
	}
}
