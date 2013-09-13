import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.IconUIResource;
import javax.swing.Icon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Shape;

/*
 * Represents model on composer
 */
public class ModelComponent extends JPanel {

	// Flyout for all models
	// Decalred here because static vars cant be decalred
	// on inner classes unless initialized with const
	private static final ModelFlyout Flyout = new ModelFlyout();

	// Model associated with this model componenet
	private Model _model;
	
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
		_model = model;
		
		setBackground(Color.WHITE);
		
		// Create and add label
		_lblTitle = new JLabel(model.getName());
		add(_lblTitle);

		_modelBox = new ModelBox();
		this.add(_modelBox);
		
		// Register the model box with the flyout
		Flyout.RegisterModelBox(_modelBox);
		
		this.setSize(getPreferredSize());
		
		setBounds(new Rectangle(
                getLocation(), getPreferredSize()));
	}
	
	/*
	 * Get the associated model
	 */
	public Model getModel(){
		return _model;
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(_modelBox.getWidth(), _modelBox.getHeight() + _lblTitle.getHeight() + 50);
	}
}
