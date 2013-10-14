package semGen.models.properties.ui;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import semGen.SemGemConstants;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.MergedModelProperty;
import semGen.models.properties.ModelPropertyListener;
import semGen.ui.ComposerJFrame;
import ui.FlyoutComponent;
import ui.FlyoutPosition;

import java.awt.Component;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
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
	
	// Image path
	private final static String RedXPath = SemGemConstants.ImagesDir + "red-x.png";
	
	private final static int CloseButtonWidth = 20;
	private final static int CloseButtonHeight = 20;
	
	public PropertyMappingComponent(MergedModelProperty mergedModelProperty) {
		this();

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
	}
	
	protected PropertyMappingComponent() {
		
		JButton deleteButton = new JButton();
		deleteButton.setContentAreaFilled(false);
		deleteButton.setBorder(null);
		deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ImageIcon redX = new ImageIcon(RedXPath);
		// Resize image
		{
			Image img = redX.getImage();
			Image resizedImage = img.getScaledInstance(CloseButtonWidth, CloseButtonHeight, java.awt.Image.SCALE_SMOOTH);
			redX.setImage(resizedImage);
			deleteButton.setIcon(redX);
		}
		add(deleteButton);	
		
		_property1Component = new PropertyComponent();
		_property1Component.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(_property1Component);
		
		JPanel panelLeftSeparator = new JPanel();
		panelLeftSeparator.setBackground(Color.BLACK);
		add(panelLeftSeparator);
		
		_comboBoxPropertySelector = new JComboBox(new DefaultComboBoxModel());
		PropertyMappingComboBoxRenderer renderer = new PropertyMappingComboBoxRenderer();
		_comboBoxPropertySelector.setRenderer(renderer);
		_comboBoxPropertySelector.setMaximumRowCount(3);
		_comboBoxPropertySelector.addActionListener(this);
		add(_comboBoxPropertySelector);
		
		JPanel panelRightSeparator = new JPanel();
		panelRightSeparator.setBackground(Color.BLACK);
		add(panelRightSeparator);
		
		_property2Component = new PropertyComponent();
		_property2Component.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(_property2Component);
		
		// Add dummy object at the end to balance out the close button
		Component deleteButtonBalancer = Box.createRigidArea(new Dimension(CloseButtonWidth, CloseButtonHeight));
		add(deleteButtonBalancer);
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
