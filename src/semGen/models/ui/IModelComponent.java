package semGen.models.ui;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import semGen.models.Model;

public interface IModelComponent {
	/**
	 * Gets the model component's model
	 * @return model
	 */
	public Model getModel();
	
	/**
	 * Add a delete action listener
	 * @param deleteListener delete listener
	 */
	public void addDeleteActionListener(MouseListener deleteListener);
}
