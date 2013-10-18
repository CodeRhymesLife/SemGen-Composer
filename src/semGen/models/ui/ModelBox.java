package semGen.models.ui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import semGen.SemGemConstants;
import semGen.models.Model;
import ui.ImageComponent;
import ui.RoundedCornerJPanel;


/*
 * Part of Model ui that you move and click
 */
public class ModelBox extends RoundedCornerJPanel {
	private static final int Width = 150;
	private static final int Height = 100;
	private static final int BorderArc = 20;
	
	// Image file path
	private static final String GripImagePath = SemGemConstants.ImagesDir + "model_grip.jpg";
	
	// Grip indicating model is move-able
	private ImageComponent _grip;
	
	public ModelBox(){
		super(BorderArc);
		setupUI();
		this.setSize(getPreferredSize());
		this.setMaximumSize(getPreferredSize());
	}

	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(Width, Height);
	}
	
	/*
	 * Gets parent ModelComponenet.
	 * 
	 * Note: this assumes that the parent is a ModelComponent.
	 * If that changes this will break.
	 * (non-Javadoc)
	 * @see java.awt.Component#getParent()
	 */
	public Model getModel(){
		return ((ModelComponent)SwingUtilities.getAncestorOfClass(ModelComponent.class, this)).getModel();
	}

	/*
	 * Setup the ui
	 */
	private void setupUI(){
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// Set layout so grip is centered
		this.setLayout(new GridBagLayout());
		
		// Create and add grip component
		_grip = new ImageComponent(GripImagePath, 50, 30);
		this.add(_grip);
		
		setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}
}
