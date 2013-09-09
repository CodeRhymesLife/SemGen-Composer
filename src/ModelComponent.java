import java.awt.Graphics;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.plaf.IconUIResource;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Shape;

/*
 * Represents model on composer
 */
public class ModelComponent extends JPanel {
	// Title
	private JLabel _lblTitle;
	
	// Box representing model
	private ModelBox _modelBox;
	
	/**
	 * Create the panel.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public ModelComponent(Model model) {
		setBackground(Color.WHITE);
		
		// Create and add label
		_lblTitle = new JLabel(model.getName());
		add(_lblTitle);

		_modelBox = new ModelBox();
		this.add(_modelBox);
		
		this.setSize(getPreferredSize());
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(_modelBox.getWidth(), _modelBox.getHeight() + _lblTitle.getHeight() + 50);
	}
	
	/*
	 * Part of Model ui that you move and click
	 */
	private class ModelBox extends JPanel{
		private static final int Width = 150;
		private static final int Height = 100;
		private static final int BorderArc = 20;
		
		// Image file path
		private static final String GripImagePath = SemGemConstants.ImagesDir + "model_grip.jpg";
		
		// Grip indicating model is move-able
		private ImageComponent _grip;
		
		public ModelBox(){
			this.setBackground(Color.WHITE);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			
			// Set layout so grip is centered
			this.setLayout(new GridBagLayout());
			
			// Create and add grip component
			_grip = new ImageComponent(GripImagePath, 50, 30);
			this.add(_grip);
			
			this.setSize(getPreferredSize());
		}
		
		@Override
		public Dimension getPreferredSize(){
			return new Dimension(Width, Height);
		}
		
		/*
		 * Draw rounded rectangles
		 * 
		 * (non-Javadoc)
		 * @see javax.swing.JComponent#paintBorder(java.awt.Graphics)
		 */
		@Override
	    protected void paintBorder(Graphics g) {
	         g.setColor(getForeground());
	         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, BorderArc, BorderArc);
	    }
	}
}
