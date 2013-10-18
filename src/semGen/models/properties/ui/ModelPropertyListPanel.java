package semGen.models.properties.ui;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;

import semGen.models.properties.IModelProperty;


public class ModelPropertyListPanel extends JPanel {
	private JTextField _textFilter;
	private ModelPropertiesTable _table;
	private JPanel panelFilterContainer;
	private JLabel lblFilter;
	
	/**
	 * Create the panel.
	 */
	public ModelPropertyListPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelFilterContainer = new JPanel();
		panelFilterContainer.setOpaque(false);
		add(panelFilterContainer);
		panelFilterContainer.setLayout(new BoxLayout(panelFilterContainer, BoxLayout.X_AXIS));
		
		lblFilter = new JLabel("Filter:");
		panelFilterContainer.add(lblFilter);
		
		_textFilter = new JTextField();
		panelFilterContainer.add(_textFilter);
		
		_table = new ModelPropertiesTable();
		_table.setCellSelectionEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(_table);
		_table.setFillsViewportHeight(true);
		add(scrollPane);
	}
	
	/*
	 * Set properties in table
	 */
	public ModelPropertiesTable getTable(){
		return _table;
	}	
}
