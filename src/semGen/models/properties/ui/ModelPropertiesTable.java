package semGen.models.properties.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;

import semGen.models.properties.IModelProperty;
import semGen.models.properties.ModelPropertyListener;

public class ModelPropertiesTable extends JTable implements MouseListener {
	// Data model
	private ModelTableDataModel _dataModel;
	
	// Property selection listener
	private ModelPropertyListener _selectionListener;
	
	public ModelPropertiesTable() {
		super(new ModelTableDataModel());
		
		_dataModel = (ModelTableDataModel)this.getModel();
		
		// Listen for mouse events
		this.addMouseListener(this);
	}
	
	/*
	 * Set properties in table datamodel
	 */
	public void setProperties(ArrayList<IModelProperty> properties){
		_dataModel.setProperties(properties);
	}
	
	/*
	 * Sets the listener that's called when property selection occurs
	 */
	public void setPropertySelectionListener(ModelPropertyListener listener){
		_selectionListener = listener;
	}
	
	/*
	 * Inform the property selection listener a property was selected
	 */
	private void informPropertySelectionListener(IModelProperty property){
		if(_selectionListener != null)
			_selectionListener.propertyEvent(property);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			int row = this.getSelectedRow();
			
			// Get the property at the row that was clicked;
			IModelProperty property = _dataModel.getPropertyAt(row);
			
			// Let the listeners know a property was selected
			this.informPropertySelectionListener(property);
	    }
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
