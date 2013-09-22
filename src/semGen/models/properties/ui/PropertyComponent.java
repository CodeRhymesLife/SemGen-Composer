package semGen.models.properties.ui;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import semGen.models.properties.IModelProperty;


public class PropertyComponent extends JPanel {
	private JLabel _lblEquationValue;
	private JLabel _lblPropertyValue;

	/**
	 * Create the panel.
	 */
	public PropertyComponent() {
		setMinimumSize(new Dimension(150, 60));
		setMaximumSize(new Dimension(150, 60));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panelPropertyNameContainer = new JPanel();
		add(panelPropertyNameContainer);
		
		JLabel lblPropertyNameTitle = new JLabel("Property Name:");
		panelPropertyNameContainer.add(lblPropertyNameTitle);
		
		_lblPropertyValue = new JLabel("?");
		panelPropertyNameContainer.add(_lblPropertyValue);
		
		JPanel panelEquationContainer = new JPanel();
		add(panelEquationContainer);
		
		JLabel lblEquationTitle = new JLabel("Equation");
		panelEquationContainer.add(lblEquationTitle);
		
		_lblEquationValue = new JLabel("?");
		panelEquationContainer.add(_lblEquationValue);
	}
	
	/*
	 * Sets values from the property on the component
	 */
	public void loadProperty(IModelProperty property){
		_lblPropertyValue.setText(property.getName());
		_lblEquationValue.setText(property.getEquation());
	}
}
