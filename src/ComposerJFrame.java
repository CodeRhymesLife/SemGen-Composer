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
	private static Component _addModelButton;
	
	/**
	 * @throws HeadlessException
	 */
	public ComposerJFrame() throws HeadlessException {
		// Set the title
		super(Title);
	}
	
	/*
	 * Setup and show ui
	 */
	public void createAndShowUI(){
		this.setLayout(null);
		
		Container pane = this.getContentPane();
		pane.setBackground(Color.WHITE);
		
		createAddModelButton(pane);
		
		this.setVisible(true);
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
}
