import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


public class ModelTableDataModel extends AbstractTableModel{
	private static final String Name = "Name";
	private static final String CodeWord = "Code Word";
	private static final String Equation = "Equation";
	
	// Column names
	private String[] _columnNames;
	
	// Model properties
	private ArrayList<IModelProperty> _properties;
	
	public ModelTableDataModel(){
		_columnNames = new String[] {Name,
	            CodeWord,
	            Equation};
	}
	
	/*
	 * Set an array of properties to display in the table
	 */
	public void setProperties(ArrayList<IModelProperty> properties){
		_properties = properties;
	}
	
	/*
	 * Get column name
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int col) {
        return _columnNames[col].toString();
    }
	
	/*
	 * Model table cells are not editable
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
    @Override
    public boolean isCellEditable(int row, int col) { return false; }
    
    /*
     * Gets the number of columns
     * 
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
	@Override
	public int getColumnCount() {
		return _columnNames.length;
	}

	/*
	 * Gets the number of rows
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return _properties == null ? 0 : _properties.size();
	}

	/*
	 * Gets the value at a particular cell
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int col) {
		IModelProperty property = _properties.get(row);
		switch(getColumnName(col)){
			case Name:
				return property.getName();
			case CodeWord:
				return property.getVariableName();
			case Equation:
				return property.getEquation();
		}
		
		assert(false);
		return null;
	}
}
