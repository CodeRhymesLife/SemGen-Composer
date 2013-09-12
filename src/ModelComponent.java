import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.plaf.IconUIResource;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
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
	
	// Flyout for all models
	// Decalred here because static vars cant be decalred
	// on inner classes unless initialized with const
	private static ModelFlyout Flyout = null;
	
	/**
	 * Create the panel.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public ModelComponent(Model model) {
		// Initialize flyout
		if(Flyout == null){
			Flyout = new ModelFlyout();
		}
		
		setBackground(Color.WHITE);
		
		// Create and add label
		_lblTitle = new JLabel(model.getName());
		add(_lblTitle);

		_modelBox = new ModelBox();
		this.add(_modelBox);
		
		this.setSize(getPreferredSize());
		
		setBounds(new Rectangle(
                getLocation(), getPreferredSize()));
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(_modelBox.getWidth(), _modelBox.getHeight() + _lblTitle.getHeight() + 50);
	}
	
	/*
	 * Part of Model ui that you move and click
	 */
	private class ModelBox extends JPanel {
		private static final int Width = 150;
		private static final int Height = 100;
		private static final int BorderArc = 20;
		
		// Image file path
		private static final String GripImagePath = SemGemConstants.ImagesDir + "model_grip.jpg";
		
		// Grip indicating model is move-able
		private ImageComponent _grip;
		
		public ModelBox(){
			setupUI();
			
			Flyout.RegisterModelBox(this);
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
	
	/*
	 * Flyout for models
	 */
	private class ModelFlyout extends FlyoutComponent implements MouseListener {
		
		public ModelFlyout(){
			Container composerPane = ComposerJFrame.getInstance().getContentPane();
			
			// Add to composer pane
			composerPane.add(this);
			
			// Listen for mouse events on the composer content area
			composerPane.addMouseListener(this);
		}
		
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
		@Override
		public void mousePressed(MouseEvent arg0) {}
		
		@Override
		public void mouseExited(MouseEvent arg0) {}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		
		/*
		 * If a model box was clicked we will show the flyout around that model box.
		 * Otherwise make sure the flyout is closed
		 * 
		 * (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent arg0) {
			Component target = arg0.getComponent();
			
			// If a model box was not clicked close this flyout
			if(target.getClass() != ModelBox.class)
				this.setVisible(false);
			
			// Otherwise open the flyout around the model box
			else
				this.showAroundComponent(target, FlyoutPosition.Left);
		}
		
		/*
		 * Registers a model box with this flyout
		 */
		private void RegisterModelBox(ModelBox modelBox){
			// Listen for mouse eventes
			modelBox.addMouseListener(this);
		}
	}
}
