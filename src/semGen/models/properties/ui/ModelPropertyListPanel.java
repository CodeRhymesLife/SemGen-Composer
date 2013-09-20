package semGen.models.properties.ui;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JLabel;

import semGen.models.properties.IModelProperty;


public class ModelPropertyListPanel extends JPanel {
	private JTextField _textFilter;
	private JTable table;
	private JPanel panelFilterContainer;
	private JLabel lblFilter;

	private ModelTableDataModel _dataModel;
	
	/**
	 * Create the panel.
	 */
	public ModelPropertyListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelFilterContainer = new JPanel();
		add(panelFilterContainer);
		panelFilterContainer.setLayout(new BoxLayout(panelFilterContainer, BoxLayout.X_AXIS));
		
		lblFilter = new JLabel("Filter:");
		panelFilterContainer.add(lblFilter);
		
		_textFilter = new JTextField();
		panelFilterContainer.add(_textFilter);
		
		_dataModel = new ModelTableDataModel();
		table = new JTable(_dataModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);
	}
	
	/*
	 * Set properties in table
	 */
	public void setTableProperties(ArrayList<IModelProperty> properties){
		_dataModel.setProperties(properties);
	}
}
