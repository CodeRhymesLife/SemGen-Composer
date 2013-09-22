package semGen.models.properties.ui;
import javax.swing.JPanel;

import semGen.models.properties.IModelProperty;
import semGen.models.properties.ModelPropertyListener;
import semGen.ui.ComposerJFrame;
import ui.FlyoutComponent;
import ui.FlyoutPosition;

import java.awt.Component;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;


public class PropertyMappingComponent extends JPanel {
	// Property components
	private PropertyComponent _property1;
	private PropertyComponent _property2;
	/**
	 * Create the panel.
	 */
	public PropertyMappingComponent() {
		
		_property1 = new PropertyComponent();
		_property1.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(_property1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		add(panel);
		
		_property2 = new PropertyComponent();
		_property2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(_property2);
	}
	
	/*
	 * Get the component for property 1
	 */
	public PropertyComponent getProperty1Componenet(){
		return _property1;
	}
	
	/*
	 * Get the component for property 2
	 */
	public PropertyComponent getProperty2Componenet(){
		return _property2;
	}
}
