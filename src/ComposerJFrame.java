import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	private final String Title = "SemGen Composer";
	
	// Containing panel
	private static JPanel _panel;
	
	// Add model button
	private static JButton _addModelButton;
	
	/**
	 * @throws HeadlessException
	 */
	public ComposerJFrame() throws HeadlessException {
		createPanel();
		createAddModelButton();
		this.setTitle(Title);
	}
	
	/*
	 * Create the panel that holds all UI objects
	 */
	private void createPanel(){
		_panel = new JPanel();
		this.add(_panel);
	}
	
	/*
	 * Create the add model button, hook up events, and add it to the panel
	 */
	private void createAddModelButton(){
		_addModelButton = new JButton("Add Model");
		
		// Listen for button events
		_addModelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				// throw an exception until we implement adding a model
				throw new UnsupportedOperationException("Add Model");
			}
		});
		
		_panel.add(_addModelButton);
	}
}
