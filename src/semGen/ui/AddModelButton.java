package semGen.ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import semGen.SemGemConstants;
import semGen.models.ui.ModelFileChooser;
import ui.FlyoutPosition;

/*
 * Button used to add models to the composer
 */
public class AddModelButton extends JButton {
	// Image path
	public final static String GreenPlusPath = SemGemConstants.ImagesDir + "GreenPlus.png";
	
	// Button caption
	public final static String Caption = "Add Model";
	
	// Border color
	public final static Color BorderColor = Color.BLACK;
	
	// Flyout that provides selection options
	private AddButtonFlyout _flyout;
	
	public AddModelButton(){
		super(Caption);
		setupUI();
		
		// Create flyout
		_flyout = new AddButtonFlyout();
		
		// Show the flyout below us when we're clicked
		this.addActionListener(new ActionListener() {
			
			/*
			 * Show the flyout below the add button
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				AddModelButton button = AddModelButton.this;
				
				// If we haven't added the flyout to its parent
				// add it
				if(button._flyout.getParent() == null)
					ComposerJFrame.getInstance().getContentPane().add(button._flyout);
				
				button._flyout.showAroundComponent(button, FlyoutPosition.Bottom);
			}
		});
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
	    
	    // set size
	    sizeToFit();
	}
	
	/*
	 * Adjust size based on image and text
	 */
	private void sizeToFit(){
		Icon plusImage = this.getIcon();
		FontMetrics metrics = getFontMetrics(getFont()); 
		
		// Text width + image width
        int width = metrics.stringWidth(getText()) + plusImage.getIconWidth();
        
        // Max height between the text and image
        int height = Math.max(metrics.getHeight(), plusImage.getIconHeight()) + 1;
        
        // Set size
        Dimension newDimension =  new Dimension(width,height);
        setPreferredSize(newDimension);
        setBounds(new Rectangle(
                       getLocation(), getPreferredSize()));
	}
}


