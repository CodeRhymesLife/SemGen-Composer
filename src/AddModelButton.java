import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

/*
 * Button used to add models to the composer
 */
public class AddModelButton extends JButton implements ActionListener {
	// Image path
	public final static String GreenPlusPath = "images/GreenPlus.png";
	
	// Button caption
	public final static String Caption = "Add Model";
	
	// Border color
	public final static Color BorderColor = Color.BLACK;
	
	// Height
	public final static int Height = 50;
	
	// Width
	public final static int Width = 120;
	
	// Parent window for the file chooser
	private Component _fileChooserParent;
	
	// Used to choose model files
	private JFileChooser _fileChooser;
	
	// Listens for file chosen action
	private AddModelButtonActionListener _fileChosenListener;
	
	public AddModelButton(Component fileChooserParent){
		super(Caption);
		
		setupUI();
		createFileChooser(fileChooserParent);
		
		// Listen for click events
		this.addActionListener(this);
	}
	
	/*
	 * Sets up the ui
	 */
	private void setupUI(){
		this.setIcon(new ImageIcon(GreenPlusPath));
		this.setToolTipText(Caption);
		
		// Set styles
		this.setContentAreaFilled(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setBorder(null);
		
		// Align everything from the left
		this.setHorizontalAlignment(SwingConstants.LEFT);
		
		// Text shows to the right of the image
	    this.setHorizontalTextPosition(SwingConstants.RIGHT);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(Width, Height);
	}
	
	/*
	 * Set the action listener that is called when
	 * a model file is chosen.
	 */
	public void setAddModelButtonActionListener(AddModelButtonActionListener listener){
		if(listener != null)
			_fileChosenListener = listener;
	}
	
	/*
	 * Create the file chooser
	 */
	private void createFileChooser(Component parent){
		if(parent == null)
			throw new NullPointerException("parent");
		
		// Save the file chooser's parent so we can open
		// the file chooser in the correct context
		_fileChooserParent = parent;
		
		// Create the file chooser
		_fileChooser = new ModelFileChooser();
	}

	/*
	 * Called when the button is clicked. Opens file chooser.
	 * TODO: Execute callback with file info
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = _fileChooser.showOpenDialog(_fileChooserParent);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = _fileChooser.getSelectedFile();
            
            // Inform our listener that a file has been chosen
            if(_fileChosenListener != null)
            	_fileChosenListener.modelFileChosen(file);
        }
	}
}


