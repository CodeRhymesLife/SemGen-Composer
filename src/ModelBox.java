import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import semGen.SemGemConstants;
import semGen.models.Model;


/*
 * Part of Model ui that you move and click
 */
public class ModelBox extends JPanel {
	private static final int Width = 150;
	private static final int Height = 100;
	private static final int BorderArc = 20;
	
	// Image file path
	private static final String GripImagePath = SemGemConstants.ImagesDir + "model_grip.jpg";
	
	// Grip indicating model is move-able
	private ImageComponent _grip;
	
	public ModelBox(){
		setupUI();			
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
	 * Draw a rounded rectangle
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
	 */
	@Override
    protected void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, BorderArc, BorderArc);
    }
	
	/*
	 * Setup the ui
	 */
	private void setupUI(){
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		// Set layout so grip is centered
		this.setLayout(new GridBagLayout());
		
		this.setSize(getPreferredSize());
		
		// Create and add grip component
		_grip = new ImageComponent(GripImagePath, 50, 30);
		this.add(_grip);
	}
}
