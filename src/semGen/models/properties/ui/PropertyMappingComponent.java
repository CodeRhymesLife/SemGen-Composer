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
	}
}
