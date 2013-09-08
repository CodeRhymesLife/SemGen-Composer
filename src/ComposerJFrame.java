import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;

import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Ryan James
 *
 */
public class ComposerJFrame extends JFrame {
	// Title for frame
	private final static String Title = "SemGen Composer";
	
	// Add model button
	private AddModelButton _addModelButton;
	
	/**
	 * @throws HeadlessException
	 */
	public ComposerJFrame(SemGen semGen) throws HeadlessException {
		// Set the title
		super(Title);
		
		listForSemGenChanges(semGen);
	}
	
	/*
	 * Get the add model button
	 */
	public AddModelButton getAddModelButton(){
		return _addModelButton;
	}
	
	/*
	 * Setup and show ui
	 */
	public void createUI(){
		this.setLayout(null);
		
		Container pane = this.getContentPane();
		pane.setBackground(Color.WHITE);
		
		createAddModelButton(pane);
	}
	
	/*
	 * Create the add model button and add it to the panel
	 */
	private void createAddModelButton(Container pane){
		_addModelButton = new AddModelButton(this);
		pane.add(_addModelButton);
		
		// Place in the upper left corner
		Insets insets = pane.getInsets();
		Dimension size = _addModelButton.getPreferredSize();
		_addModelButton.setBounds(insets.left + 10, insets.top, size.width, size.height);
	}
	
	/*
	 * Listen and handle changes in SemGen
	 */
	private void listForSemGenChanges(SemGen semGen){
		semGen.getModelRepository().addModelRepositoryActionListener(new ModelRepositoryActionListener() {
			
			@Override
			public void modelRemoved(Model model) {
				throw new UnsupportedOperationException("Composer remove model");
			}
			
			@Override
			public void modelAdded(Model model) {
				throw new UnsupportedOperationException("Composer add model");
			}
		});
	}
}
