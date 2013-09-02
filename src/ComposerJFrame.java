import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	
	// Containing panel
	private static JPanel _panel;
	
	// Add model button
	private static Component _addModelButton;
	
	/**
	 * @throws HeadlessException
	 */
	public ComposerJFrame() throws HeadlessException {
		// Set the title
		super(Title);
		
		this.setBackground(Color.WHITE);
		
		// Setup the UI components
		createPanel();
		createAddModelButton();
	}
	
	/*
	 * Create the panel that holds all UI objects
	 */
	private void createPanel(){
		_panel = new JPanel();
		_panel.setBackground(Color.WHITE);
		this.add(_panel);
	}
	
	/*
	 * Create the add model button and add it to the panel
	 */
	private void createAddModelButton(){
		_addModelButton = new AddModelButton(this);
		_panel.add(_addModelButton);
	}
}
