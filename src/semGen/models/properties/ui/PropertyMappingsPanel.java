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
import semGen.models.ModelListener;
import semGen.models.ModelRepositoryActionListener;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import ui.RoundedCornerJPanel;


public class PropertyMappingsPanel extends RoundedCornerJPanel implements ModelListener {
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
		btnAddNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyMappingsPanel.this.createNewPropertyMapping();
			}
		});
	}
	
	/**
	 * Listens for properties added to the current model
	 */
	@Override
	public void propertyAdded(IModelProperty property) {
		addPropertyMappingComponentForMergedModelProperty(property);
		refreshPropertiesPanel();
	}

	/**
	 * Listens for properties removed from the current model
	 */
	@Override
	public void propertyRemoved(IModelProperty property) {
		throw new UnsupportedOperationException("Property Mappings Panel Property Removed");
	}
	
	/*
	 * Add close action listener
	 */
	public void addCloseActionListener(ActionListener listener){
		_btnDone.addActionListener(listener);
	}
	
	/**
	 * Add property component to properties panel
	 * @param propertyComponent component to add
	 */
	public void addPropertyComponent(Component propertyComponent){
		_propertiesPanel.add(propertyComponent);
		refreshPropertiesPanel();
	}
	
	/**
	 * Remove property component from properties panel
	 * @param propertyComponent component to remove
	 */
	public void removePropertyComponent(Component propertyComponent){
		_propertiesPanel.remove(propertyComponent);
		refreshPropertiesPanel();
	}
	
	/*
	 * Set a new model in the property mappings panel
	 */
	public void setModel(MergedModel model){	
		assert(model != null);
		
		unsetCurrentModel();
		
		// Save the model
		_model = model;
		
		// Listen for property actions
		_model.addModelListener(this);
		
		// Set the model name label
		_lblModelName.setText(_model.getName());
		
		// Clear the properties panel
		_propertiesPanel.removeAll();
		
		// Add a property mapping component for each model property
		for(Iterator<IModelProperty> i = _model.getProperties().iterator(); i.hasNext(); ) {
			IModelProperty property = i.next();
			addPropertyMappingComponentForMergedModelProperty(property);
		}
	}

	/**
	 * Create a new property mapping
	 */
	private void createNewPropertyMapping(){
		PropertyMappingCreator mappingCreator = new PropertyMappingCreator(_model);
		mappingCreator.create(this);
	}
	
	/**
	 * Unset the current model if one exists
	 */
	private void unsetCurrentModel(){
		if(_model == null)
			return;
		
		_model.removeModelListener(this);
	}
	
	/**
	 * Add a property mapping component for the given merged model property
	 * @param mergedModelProperty Merged model property to add property component for
	 */
	private void addPropertyMappingComponentForMergedModelProperty(IModelProperty property){
		if(!(property instanceof MergedModelProperty))
			// TODO: Add support for non-merged properties
			return;
		
		MergedModelProperty mergedModelProperty = (MergedModelProperty)property;
		
		// Create a component to show the mapping
		PropertyMappingComponent propertyMappingComponent = new PropertyMappingComponent(mergedModelProperty);
		
		addPropertyComponent(propertyMappingComponent);
	}
	
	/**
	 * Refresh the properties panel
	 */
	private void refreshPropertiesPanel(){
		_propertiesPanel.validate();
		_propertiesPanel.repaint();
	}
}
