package semGen.models.properties.ui;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import semGen.models.Model;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelPropertyListener;
import semGen.ui.ComposerJFrame;
import semGen.ui.DeleteButton;
import ui.FlyoutComponent;
import ui.FlyoutPosition;

import java.awt.Component;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;


public class PropertyMappingComponent extends JPanel implements ActionListener {
	// Property components
	protected PropertyComponent _property1Component;
	protected PropertyComponent _property2Component;
	protected JComboBox _comboBoxPropertySelector;
	
	// Merged model property
	private MergedModelProperty _mergedModelProperty;
	private static final String ChooseRepresentationString = "Choose Representation";
	
	private final static int CloseButtonWidth = 20;
	private final static int CloseButtonHeight = 20;
	
	private static final int PropertyComponentHeight = 50;
	private static final int PropertyComponentWidth = 200;
	
	private final static int PropertySelectorContainerWidth = 450;
	private final static int PropertySelectorContainerHeight = PropertyComponentHeight;
	
	private static final int MergeBarThickness = 2;
	
	private final static int ComboBoxWidth = 250;
	private final static int ComboBoxHeight = 20;
	
	public PropertyMappingComponent(MergedModelProperty mergedModelProperty) {
		this();
		
		DeleteButton deleteButton = new DeleteButton(CloseButtonWidth, CloseButtonHeight);
		add(deleteButton);	
		
		addPropertyComponents();
		
		// Add dummy object at the end to balance out the close button
		Component deleteButtonBalancer = Box.createRigidArea(new Dimension(CloseButtonWidth, CloseButtonHeight));
		add(deleteButtonBalancer);

		// Save the merged model property
		_mergedModelProperty = mergedModelProperty;	
		
		// Set the properties on the property components
		_property1Component.setProperty(mergedModelProperty.getProperty1());
		_property2Component.setProperty(mergedModelProperty.getProperty2());
		
		// Set the properties on the combobox
		DefaultComboBoxModel model = (DefaultComboBoxModel)_comboBoxPropertySelector.getModel();
		model.addElement(ChooseRepresentationString);
		model.addElement(mergedModelProperty.getProperty1());
		model.addElement(mergedModelProperty.getProperty2());
		
		// If we have a source model select it
		if(mergedModelProperty.getSourceProperty() != null){
			model.setSelectedItem(mergedModelProperty.getSourceProperty());
		}
		
		// Remove this property when the close button is clicked
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyMappingComponent.this._mergedModelProperty.getParentModel().removeProperty(PropertyMappingComponent.this._mergedModelProperty);
			}
		});
	}
	
	protected PropertyMappingComponent() {
		this.setOpaque(false);
	}
	
	/**
	 * Sets the selected property as the source property
	 * on the merged model property
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        Object selectedItem = cb.getSelectedItem();
        
        // If we selected a model property add it and remove
        // the choose representation item
        if(selectedItem instanceof IModelProperty){
        	 _mergedModelProperty.setSourceProperty((IModelProperty)cb.getSelectedItem());
        }
    }
	
	/**
	 * Get component for property 1
	 * @return component for property 1
	 */
	public Component getProperty1Component(){
		return _property1Component;
	}
	
	/**
	 * Get component for property 2
	 * @return component for property 2
	 */
	public Component getProperty2Component(){
		return _property2Component;
	}
	
	/**
	 * Add property components
	 * 
	 * Note: I hate all of the sizing code. Its here because I needed to get
	 * this done in a short amount of time. Will clean up later.
	 */
	protected void addPropertyComponents(){
		_property1Component = new PropertyComponent();
		_property1Component.setPreferredSize(new Dimension(PropertyComponentWidth, PropertyComponentHeight));
		_property1Component.setSize(getPreferredSize());
		_property1Component.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(_property1Component);
		
		JPanel propertySelectorContainer = new JPanel();
		propertySelectorContainer.setLayout(null);
		propertySelectorContainer.setOpaque(false);
		propertySelectorContainer.setPreferredSize(new Dimension(PropertySelectorContainerWidth, PropertySelectorContainerHeight));
		propertySelectorContainer.setSize(propertySelectorContainer.getPreferredSize());
		add(propertySelectorContainer);
		
		_comboBoxPropertySelector = new JComboBox(new DefaultComboBoxModel());
		PropertyMappingComboBoxRenderer renderer = new PropertyMappingComboBoxRenderer();
		_comboBoxPropertySelector.setRenderer(renderer);
		_comboBoxPropertySelector.setMaximumRowCount(3);
		_comboBoxPropertySelector.addActionListener(this);
		_comboBoxPropertySelector.setBounds(new Rectangle(new Point((PropertySelectorContainerWidth - ComboBoxWidth) / 2, (PropertyComponentHeight / 2 - ComboBoxHeight) / 2),
				new Dimension(ComboBoxWidth, ComboBoxHeight)));
		propertySelectorContainer.add(_comboBoxPropertySelector);

		JPanel propertyMergeBar = new JPanel();
		propertyMergeBar.setBounds(new Rectangle(new Point(0, (PropertyComponentHeight / 2 - MergeBarThickness) / 2),
				new Dimension(PropertySelectorContainerWidth, MergeBarThickness)));
		propertyMergeBar.setBackground(Color.BLACK);
		propertySelectorContainer.add(propertyMergeBar);
		
		_property2Component = new PropertyComponent();
		_property2Component.setPreferredSize(new Dimension(PropertyComponentWidth, PropertyComponentHeight));
		_property2Component.setSize(getPreferredSize());
		_property2Component.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(_property2Component);
	}
	
	/**
	 * Renders combobox elements
	 * 
	 * @author rjames
	 *
	 */
	private class PropertyMappingComboBoxRenderer extends JLabel implements ListCellRenderer {
		public PropertyMappingComboBoxRenderer() {
			setOpaque(true);
			setVerticalAlignment(CENTER);
		}

		/**
		 * Renders the combobox value
		 */
		public Component getListCellRendererComponent(
		                    JList list,
		                    Object value,
		                    int index,
		                    boolean isSelected,
		                    boolean cellHasFocus)
		{
			
			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			
			String displayText;
			if(value instanceof String)
				displayText = (String)value;
			else{
				IModelProperty property = (IModelProperty)value;
				
				// Build the string to show in the combobox as follows:
				// <property name> (<property parent model name>)
				displayText = String.format("%s (%s)", property.getName(), property.getParentModel().getName());
			}
			
			setText(displayText);
			return this;
		}
	}
}
