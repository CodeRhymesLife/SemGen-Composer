
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import semGen.models.Model;


public abstract class ModelActionListener implements ActionListener {
	private Model _model;
	
	public ModelActionListener(Model model){
		_model = model;
	}
	
	/*
	 * Get the associated model
	 */
	public Model getSavedModel(){
		return _model;
	}
}
