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
import ui.FlyoutUtility;

/*
 * Creates a property mapping
 */
public class PropertyMappingCreator {
	// Flyout used to select properties
	private static PropertyMappingsFlyout _propertiesFlyout;

	// Component representing the property mapping
	private IncompletePropertyMappingComponent _incompletePropertyMappingComponent;
	
	// Property mappings panel
	private PropertyMappingsPanel _propertyMappingsPanel;
	
	// Model to create property mapping for
	private MergedModel _mergedModel;
	
	// Property selection listeners
	private ModelPropertyListener _model1PropertySelectionListener;
	private ModelPropertyListener _model2PropertySelectionListener;
	
	public PropertyMappingCreator(MergedModel mergedModel){
		if(mergedModel == null)
			throw new NullPointerException("mergedModel");
		
		_mergedModel = mergedModel;
		
		// If the flyout hasn't been created yet create it
		if(_propertiesFlyout == null){
			_propertiesFlyout = new PropertyMappingsFlyout();
			ComposerJFrame.addFlyout(_propertiesFlyout);
		}
	}
	
	/*
	 * Create property mapping
	 */
	public void create(PropertyMappingsPanel propertyMappingsPanel){
		if(propertyMappingsPanel == null)
			throw new NullPointerException("propertyMappingsPanel");
		
		_propertyMappingsPanel = propertyMappingsPanel;
		
		// Create and add the property mapping component
		_incompletePropertyMappingComponent = new IncompletePropertyMappingComponent();
		propertyMappingsPanel.addPropertyComponent(_incompletePropertyMappingComponent);
		
		// Listen for a property selected from model 1
		_model1PropertySelectionListener = new ModelPropertyListener() {
			
			@Override
			public void propertyEvent(IModelProperty property) {
				_incompletePropertyMappingComponent.getProperty1Component().setProperty(property);
				
				// Select the next property
				PropertyMappingCreator.this.selectNextProperty();
			}
		};
		
		// Listen for a property selected from model 2
		_model2PropertySelectionListener = new ModelPropertyListener() {
			
			@Override
			public void propertyEvent(IModelProperty property) {
				_incompletePropertyMappingComponent.getProperty2Component().setProperty(property);
				
				// Select the next property
				PropertyMappingCreator.this.selectNextProperty();
			}
		};
		
		// Select the next property
		this.selectNextProperty();
	}
	
	/*
	 * Selects the next property. If properties from both models have been
	 * selected a new merged model property is created and added to the merged model
	 */
	private void selectNextProperty(){
		// If we haven't selected a property from the first model
		// list the first model's properties for selection
		if(!_incompletePropertyMappingComponent.getProperty1Component().hasProperty()){
			listPropertiesForSelection(_mergedModel.getSourceModel1(), _model1PropertySelectionListener, FlyoutPosition.Left);
		}
		// Otherwise if we haven't selected a property from the second model
		// list the second model's properties for selection
		else if(!_incompletePropertyMappingComponent.getProperty2Component().hasProperty()){
			listPropertiesForSelection(_mergedModel.getSourceModel2(), _model2PropertySelectionListener, FlyoutPosition.Right);
		}
		// Otherwise if both properties have been selected create the merged property
		// and add it to the merged model
		else{
			// Hide the properties flyout
			_propertiesFlyout.setVisible(false);
			
			// Stop listening for the property selection event
			_propertiesFlyout.getPropertiesTable().setPropertySelectionListener(null);
			
			// Remove the property incomplete mapping component
			// now that it's complete. The property mappings panel will add its
			// own component when we add the new merged property
			_propertyMappingsPanel.removePropertyComponent(_incompletePropertyMappingComponent);
			
			// Add the new mapping to the merged model
			_mergedModel.addProperty(new MergedModelProperty(_incompletePropertyMappingComponent.getProperty1Component().getProperty(),
					_incompletePropertyMappingComponent.getProperty2Component().getProperty()));
		}
		
	}
	
	/*
	 * Lists the properties in the given model for selection
	 */
	private void listPropertiesForSelection(Model model, ModelPropertyListener listener, FlyoutPosition position){
		ModelPropertiesTable propertiesTable = _propertiesFlyout.getPropertiesTable();
		propertiesTable.setProperties(model.getProperties());
		
		// Listen for the property selection event
		_propertiesFlyout.getPropertiesTable().setPropertySelectionListener(listener);
		
		// Update the flyout's title
		_propertiesFlyout.setTitle(String.format("%s Properties", model.getName()));
		
		// Show the flyout around the first property component
		_propertiesFlyout.showAroundComponent(_incompletePropertyMappingComponent, position);
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
	
	/**
	 * Represents a property mapping in progress
	 * 
	 * @author rjames
	 *
	 */
	private class IncompletePropertyMappingComponent extends PropertyMappingComponent {
		public IncompletePropertyMappingComponent(){
			super();
			
			// Hide the combobox
			_comboBoxPropertySelector.setVisible(false);
		}
		
		/*
		 * Get the component for property 1
		 */
		public PropertyComponent getProperty1Component(){
			return _property1Component;
		}

		/*
		 * Get the component for property 2
		 */
		public PropertyComponent getProperty2Component(){
			return _property2Component;
		}
	}
}
