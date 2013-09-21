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
public class PropertyMappingCreator {
	// Flyout used to select properties
	private static PropertyMappingsFlyout _propertiesFlyout;

	// Component representing the property mapping
	private PropertyMappingComponent _propertyMappingComponent;
	
	// Model to create property mapping for
	private MergedModel _mergedModel;
	
	// Property selection listeners
	private ModelPropertySelectedListener _model1Listener;
	private ModelPropertySelectedListener _model2Listener;
	
	public PropertyMappingCreator(MergedModel mergedModel){
		if(mergedModel == null)
			throw new NullPointerException("mergedModel");
		
		_mergedModel = mergedModel;
		
		// Create property selection listeners
		_model1Listener = new ModelPropertySelectedListener(this);
		_model2Listener = new ModelPropertySelectedListener(this);
		
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
		
		// Select the next property
		this.selectNextProperty();
	}
	
	/*
	 * Selects the next property. If properties from both models have been
	 * selected a new merged model property is created and added to the merged model
	 */
	private void selectNextProperty(){
		// If we haven't selected a property from the first
		// list the properties for selection
		if(_model1Listener.getSelectedProperty() == null){
			listPropertiesForSelection(_mergedModel.getSourceModel1(), _model1Listener, FlyoutPosition.Left);
		}
		// Otherwise if we havent selected a property from the
		// second model list the properties for selection
		else if(_model2Listener.getSelectedProperty() == null){
			listPropertiesForSelection(_mergedModel.getSourceModel2(), _model2Listener, FlyoutPosition.Right);
		}
		// Otherwise if both properties have been selected create the merged property
		// and add it to the merged model
		else{
			// Hide the properties flyout
			_propertiesFlyout.setVisible(false);
			
			// Stop listening for the property selection event
			_propertiesFlyout.getPropertiesTable().setPropertySelectionListener(null);
			
			// Add the new mapping to the merged model
			_mergedModel.addProperty(new MergedModelProperty(_model1Listener.getSelectedProperty(), _model2Listener.getSelectedProperty()));
		}
		
	}
	
	/*
	 * Lists the properties in the given model for selection
	 */
	private void listPropertiesForSelection(Model model, ModelPropertySelectedListener listener, FlyoutPosition position){
		ModelPropertiesTable propertiesTable = _propertiesFlyout.getPropertiesTable();
		propertiesTable.setProperties(model.getProperties());
		
		// Listen for the property selection event
		_propertiesFlyout.getPropertiesTable().setPropertySelectionListener(listener);
		
		// Show the flyout around the first property component
		_propertiesFlyout.showAroundComponent(_propertyMappingComponent, position);
	}
	
	private class ModelPropertySelectedListener implements ModelPropertyListener{
		// Mapping creator
		private PropertyMappingCreator _mappingCreator;
		
		// Selected Property
		private IModelProperty _selectedProperty;
		
		public ModelPropertySelectedListener(PropertyMappingCreator mappingCreator){
			_mappingCreator = mappingCreator;
		}
		
		/*
		 * Gets the selected property
		 */
		public IModelProperty getSelectedProperty(){
			return _selectedProperty;
		}
		
		/*
		 * Called when a property has been selected from the table
		 * 
		 * (non-Javadoc)
		 * @see semGen.models.properties.ModelPropertyListener#propertyEvent(semGen.models.properties.IModelProperty)
		 */
		@Override
		public void propertyEvent(IModelProperty property) {
			_selectedProperty = property;
			
			// Select the next property
			_mappingCreator.selectNextProperty();
		}
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
