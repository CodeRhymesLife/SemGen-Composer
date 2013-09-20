import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;


public class PropertyMappingComponent extends JPanel {
	// Flyout used to select properties
	private static PropertyMappingsFlyout _propertiesFlyout;
	
	/**
	 * Create the panel.
	 */
	public PropertyMappingComponent() {
		
		PropertyComponent propertyComponent = new PropertyComponent();
		propertyComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(propertyComponent);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		add(panel);
		
		PropertyComponent propertyComponent_1 = new PropertyComponent();
		propertyComponent_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(propertyComponent_1);

		// If the flyout hasn't been created yet select it
		if(_propertiesFlyout == null){
			_propertiesFlyout = new PropertyMappingsFlyout();
			Container flyoutParent = ComposerJFrame.getInstance().getContentPane();
			flyoutParent.add(_propertiesFlyout);
			flyoutParent.setComponentZOrder(_propertiesFlyout, 0);
		}
	}
	
	/*
	 * Create a property mapping
	 */
	public void createPropertyMapping(ArrayList<IModelProperty> sourceProperties1, ArrayList<IModelProperty> sourceProperties2){
		// Pick from source 1 first
		_propertiesFlyout.setProperties(sourceProperties1);
		_propertiesFlyout.showAroundComponent(this, FlyoutPosition.Left);
		
		// TODO: After a property from source 1 is selected, select a property from source 2
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
		 * Show the given properties in the flyout 
		 */
		public void setProperties(ArrayList<IModelProperty> properties){
			_propertyList.setTableProperties(properties);
		}
	}
}
