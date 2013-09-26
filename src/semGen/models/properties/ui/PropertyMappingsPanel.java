package semGen.models.properties.ui;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.border.LineBorder;

import semGen.models.MergedModel;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import ui.RoundedCornerJPanel;


public class PropertyMappingsPanel extends RoundedCornerJPanel {
	public static final int Height = 500;
	public static final int Width = 600;
	private static final int BorderArc = 20;
	private JButton _btnDone;
	private JLabel _lblModelName;
	private JPanel _propertiesPanel;
	
	// Current model
	private MergedModel _model;
	/**
	 * Create the panel.
	 */
	public PropertyMappingsPanel() {
		super(BorderArc);
		
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		_lblModelName = new JLabel("Model Name");
		_lblModelName.setAlignmentX(Component.CENTER_ALIGNMENT);
		_lblModelName.setFont(new Font("Calibri", Font.PLAIN, 24));
		add(_lblModelName);
		
		JLabel lblPropertyMappings = new JLabel("Property Mappings");
		lblPropertyMappings.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblPropertyMappings);
		
		_propertiesPanel = new JPanel();
		_propertiesPanel.setBackground(Color.WHITE);
		add(_propertiesPanel);
		
		Component aboveButtonGap = Box.createRigidArea(new Dimension(0,20));
		add(aboveButtonGap);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		add(buttonsPanel);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.setBackground(new Color(102, 204, 0));
		buttonsPanel.add(btnAddNew);
		
		Component betweenButtonGap = Box.createRigidArea(new Dimension(40,0));
		buttonsPanel.add(betweenButtonGap);
		
		_btnDone = new JButton("Done");
		_btnDone.setBackground(Color.WHITE);
		buttonsPanel.add(_btnDone);
		
		Component belowButtonGap = Box.createRigidArea(new Dimension(0,20));
		add(belowButtonGap);
		
		this.setSize(new Dimension(Width, Height));
		
		// Add listeners
		final PropertyMappingsPanel thisPanel = this;
		btnAddNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisPanel.addProperty();
			}
		});
	}
	
	/*
	 * Add close action listener
	 */
	public void addCloseActionListener(ActionListener listener){
		_btnDone.addActionListener(listener);
	}
	
	/*
	 * Set a new model in the property mappings panel
	 */
	public void setModel(MergedModel model){
		// Save the model
		assert(_model != null);
		_model = model;
		
		// Set the model name label
		_lblModelName.setText(_model.getName());
		
		// Clear the properties panel
		_propertiesPanel.removeAll();
		
		// Add a property mapping component for each property
		for(Iterator<IModelProperty> i = _model.getProperties().iterator(); i.hasNext(); ) {
			IModelProperty property = i.next();
			
			// We should only have merged properties
			assert(property instanceof MergedModelProperty);
			MergedModelProperty mergedModelProperty = (MergedModelProperty)property;
			
			// Create a component to show the mapping
			PropertyMappingComponent propertyMappingComponent = new PropertyMappingComponent();
			propertyMappingComponent.getProperty1Componenet().loadProperty(mergedModelProperty.getProperty1());
			propertyMappingComponent.getProperty2Componenet().loadProperty(mergedModelProperty.getProperty2());
			
			_propertiesPanel.add(propertyMappingComponent);
		}
		
		_propertiesPanel.validate();
		_propertiesPanel.repaint();
	}

	/*
	 * Add UI for a new property mapping
	 */
	private void addProperty(){
		PropertyMappingCreator mappingCreator = new PropertyMappingCreator(_model);
		mappingCreator.create(_propertiesPanel);
	}
}
