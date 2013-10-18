package semGen.models.properties.ui;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.border.LineBorder;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.ModelListener;
import semGen.models.ModelRepositoryActionListener;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.ui.ComposerJFrame;
import ui.RoundedCornerJPanel;
import javax.swing.SwingConstants;


public class PropertyMappingsPanel extends RoundedCornerJPanel implements ModelListener {
	private static final String PropertyMappingInProgressValidationMessage = "Property Mapping In Progress";
	private static final String ResolvePropertyMappingsValidationMessage = "Resolve Property Mappings";
	public static final int Height = 600;
	public static final int Width = 1000;
	private static final int BorderArc = 20;
	private JButton _btnDone;
	private JLabel _lblModelName;
	private JPanel _propertiesPanel;
	
	// Current model
	private MergedModel _model;
	
	// Maps models to model components
	private Map<MergedModelProperty, PropertyMappingComponent> _propertyMappingComponents;
	
	// Currently in progress property mappings creator
	private PropertyMappingCreator _mappingCreator;
	private JLabel _lblValidationMessage;
	private JLabel _lblSourceModel1Name;
	private JLabel _lblSourceModel2Name;
	
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
		
		JPanel modelNameContainer = new JPanel();
		modelNameContainer.setOpaque(false);
		modelNameContainer.setPreferredSize(new Dimension(Width, 70));
		modelNameContainer.setSize(modelNameContainer.getPreferredSize());
		add(modelNameContainer);
		modelNameContainer.setLayout(new BoxLayout(modelNameContainer, BoxLayout.X_AXIS));
		
		_lblSourceModel1Name = new JLabel("Model 1 name");
		_lblSourceModel1Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		_lblSourceModel1Name.setHorizontalAlignment(SwingConstants.CENTER);
		_lblSourceModel1Name.setVerticalAlignment(SwingConstants.TOP);
		_lblSourceModel1Name.setMaximumSize(new Dimension(Width, 40));
		_lblSourceModel1Name.setMinimumSize(new Dimension(Width, 40));
		modelNameContainer.add(_lblSourceModel1Name);
		
		_lblSourceModel2Name = new JLabel("Model 2 name");
		_lblSourceModel2Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		_lblSourceModel2Name.setHorizontalAlignment(SwingConstants.CENTER);
		_lblSourceModel2Name.setVerticalAlignment(SwingConstants.TOP);
		_lblSourceModel2Name.setMinimumSize(new Dimension(Width, 40));
		_lblSourceModel2Name.setMaximumSize(new Dimension(Width, 40));
		modelNameContainer.add(_lblSourceModel2Name);
		
		_propertiesPanel = new JPanel();
		_propertiesPanel.setBackground(Color.WHITE);
		add(_propertiesPanel);
		
		_lblValidationMessage = new JLabel("");
		_lblValidationMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
		_lblValidationMessage.setFont(new Font("Tahoma", Font.PLAIN, 18));
		_lblValidationMessage.setForeground(Color.RED);
		add(_lblValidationMessage);
		
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
		
		swallowAllMouseEvents();
		
		_propertyMappingComponents = new HashMap<MergedModelProperty, PropertyMappingComponent>();
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
		removePropertyMappingComponentForMergedModelProperty(property);
		refreshPropertiesPanel();
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
	
	/**
	 * Open the property mapping panel with the given model
	 * @param model model to open panel with
	 */
	public void open(MergedModel model){	
		assert(model != null);
		
		unsetCurrentModel();
		
		// Save the model
		_model = model;
		
		// Listen for property actions
		_model.addModelListener(this);
		
		// Set labels
		_lblModelName.setText(_model.getName());
		_lblSourceModel1Name.setText(model.getSourceModel1().getName());
		_lblSourceModel2Name.setText(model.getSourceModel2().getName());
		
		// Clear the properties panel
		_propertiesPanel.removeAll();
		
		// Add a property mapping component for each model property
		for(Iterator<IModelProperty> i = _model.getProperties().iterator(); i.hasNext(); ) {
			IModelProperty property = i.next();
			addPropertyMappingComponentForMergedModelProperty(property);
		}
		
		setVisible(true);
	}
	
	/**
	 * Close the panel
	 * @return true if close was successfull. False otherwise.
	 */
	public boolean close(){
		if(!propertyMappingInProgressValidation())
			return false;
		
		// If there are any unresolved properties
		// show a validation message
		if(_model != null)
			for(IModelProperty property : _model.getProperties()){
				if(property instanceof MergedModelProperty){
					MergedModelProperty mergedProperty = (MergedModelProperty)property;
					if(mergedProperty.getSourceProperty() == null){
						showValidationMessage(ResolvePropertyMappingsValidationMessage);
						return false;
					}
				}
			}
		
		setVisible(false);
		return true;
	}

	/**
	 * Tells whether a property mapping is in progress and shows
	 * a validation message if there is one
	 * @return True if property mapping is not in progress. False otherwise.
	 */
	private boolean propertyMappingInProgressValidation(){
		if(_mappingCreator == null || !_mappingCreator.getIsCreateInProgress()){
			showValidationMessage("");
			return true;
		}
		
		showValidationMessage(PropertyMappingInProgressValidationMessage);
		return false;
	}
	
	/**
	 * Create a new property mapping
	 */
	private void createNewPropertyMapping(){
		if(!propertyMappingInProgressValidation())
			return;
		
		_mappingCreator = new PropertyMappingCreator(_model);
		_mappingCreator.create(this);
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
		_propertyMappingComponents.put(mergedModelProperty, propertyMappingComponent);
	}
	
	/**
	 * Add a property mapping component for the given merged model property
	 * @param mergedModelProperty Merged model property to add property component for
	 */
	private void removePropertyMappingComponentForMergedModelProperty(IModelProperty property){
		if(!(property instanceof MergedModelProperty))
			// TODO: Add support for removing non-merged properties
			return;
		
		MergedModelProperty mergedModelProperty = (MergedModelProperty)property;
		
		// Create a component to show the mapping
		PropertyMappingComponent propertyMappingComponent = _propertyMappingComponents.remove(mergedModelProperty);
		removePropertyComponent(propertyMappingComponent);
	}
	
	/**
	 * Refresh the properties panel
	 */
	private void refreshPropertiesPanel(){
		showValidationMessage("");
		_propertiesPanel.validate();
		_propertiesPanel.repaint();
	}
	
	/**
	 * Show a validation message
	 * @param message message to show
	 */
	private void showValidationMessage(String message){
		_lblValidationMessage.setText(message);
		ComposerJFrame.refresh();
	}
	
	private void swallowAllMouseEvents(){
		// Swallow all mouse events
		// Hover was causing an underlying button to repaint on top of this control
		// so the temp, very ugly, hacky, quick solution is to swallow all mouse
		// events on this panel
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
