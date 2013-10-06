package semGen.models.properties.ui;
import javax.swing.JPanel;

import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelPropertyListener;
import semGen.ui.ComposerJFrame;
import ui.FlyoutComponent;
import ui.FlyoutPosition;

import java.awt.Component;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JComboBox;


public class PropertyMappingComponent extends JPanel {
	// Property components
	protected PropertyComponent _property1Component;
	protected PropertyComponent _property2Component;
	protected JComboBox _comboBoxPropertySelector;
	
	public PropertyMappingComponent(MergedModelProperty mergedModelProperty) {
		this();

		// Set the properties on the property components
		_property1Component.setProperty(mergedModelProperty.getProperty1());
		_property2Component.setProperty(mergedModelProperty.getProperty2());
	}
	
	protected PropertyMappingComponent() {
		
		_property1Component = new PropertyComponent();
		_property1Component.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(_property1Component);
		
		JPanel panelLeftSeparator = new JPanel();
		panelLeftSeparator.setBackground(Color.BLACK);
		add(panelLeftSeparator);
		
		_comboBoxPropertySelector = new JComboBox();
		add(_comboBoxPropertySelector);
		
		JPanel panelRightSeparator = new JPanel();
		panelRightSeparator.setBackground(Color.BLACK);
		add(panelRightSeparator);
		
		_property2Component = new PropertyComponent();
		_property2Component.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(_property2Component);
	}
}
