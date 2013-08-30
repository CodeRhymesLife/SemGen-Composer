import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

/*
 * Button used to add models to the composer
 */
public class AddModelButton extends JButton implements ActionListener {
	// Button caption
	public final static String Caption = "Add Model";
	
	// Parent window for the file chooser
	private Component _fileChooserParent;
	
	// Used to choose model files
	private JFileChooser _fileChooser;
	
	public AddModelButton(Component fileChooserParent){
		// Set caption
		super(Caption);
		
		if(fileChooserParent == null)
			throw new NullPointerException("fileChooserParent");
		
		// Save the file chooser's parent so we can open
		// the file chooser in the correct context
		_fileChooserParent = fileChooserParent;
		
		// Create the file chooser
		_fileChooser = new ModelFileChooser();
		
		// Handle button presses
		this.addActionListener(this);
	}
	
	/*
	 * Called when the button is clicked. Opens file chooser.
	 * TODO: Execute callback with file info
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event){
		int returnVal = _fileChooser.showOpenDialog(_fileChooserParent);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = _fileChooser.getSelectedFile();
            
            // File handling coming soon
            throw new UnsupportedOperationException("Add Model");
        }
	}
}


