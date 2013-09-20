import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.AbstractTableModel;
import java.awt.Component;
import javax.swing.JLabel;


public class ModelPropertyListPanel extends JPanel {
	private JTextField _textFilter;
	private JTable table;
	private JPanel panelFilterContainer;
	private JLabel lblFilter;

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
		
		String[] columnNames = {"Name",
                "Code Word",
                "Equation"};
		table = new JTable(new Object[][]{}, columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		add(scrollPane);
	}
}
