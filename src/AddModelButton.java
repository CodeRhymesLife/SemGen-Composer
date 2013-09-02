import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
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
public class AddModelButton extends JButton {
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
	
	public AddModelButton(Component fileChooserParent){
		setupUI();
		
		createFileChooser(fileChooserParent);
		
		listenForMouseEvents();
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
		removeBorder();
		
		// Text shows to the right of the image
	    this.setHorizontalTextPosition(SwingConstants.RIGHT);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(Width, Height);
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
	 * Listen and handle mouse events
	 */
	private void listenForMouseEvents(){
		final JButton addModelButton = this;
		this.addMouseListener(new MouseListener() {
			
			/*
			 * Called when the button is clicked. Opens file chooser.
			 * TODO: Execute callback with file info
			 * 
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int returnVal = _fileChooser.showOpenDialog(_fileChooserParent);
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = _fileChooser.getSelectedFile();
		            
		            // File handling coming soon
		            throw new UnsupportedOperationException("Add Model");
		        }
			}

			/*
			 * Show the caption when you hover over the button
			 * (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseEntered(MouseEvent arg0) {
				addModelButton.setText(Caption);
				addBorder();
			}

			/*
			 * Hide the caption when you leave the button
			 * (non-Javadoc)
			 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseExited(MouseEvent arg0) {
				addModelButton.setText("");
				removeBorder();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}
	
	/*
	 * Adds border
	 */
	private void addBorder(){
		this.setBorder(BorderFactory.createLineBorder(BorderColor));
	}
	
	/*
	 * Removes border
	 */
	private void removeBorder(){
		this.setBorder(BorderFactory.createEmptyBorder());
	}
}


