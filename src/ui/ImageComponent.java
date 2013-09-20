package ui;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/*
 * Draws image
 */
public class ImageComponent extends JComponent {
	// Image
	private BufferedImage  _image;
	
	// Image size
	private int _width;
	private int _height;
	
	public ImageComponent(String path, int width, int height){
		// Save size
		_width = width;
		_height = height;
		
		// Read in grip image for drawing later
		try {
			_image = ImageIO.read(new File(path));
		} catch (IOException e) {
			assert(false);
			e.printStackTrace();
		}
		
		this.setSize(getPreferredSize());
	}
	
	/*
	 * Return the size of the image
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(_width, _height);
	}
	
	/*
	 * Paint the image
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(_image, 0, 0, this.getWidth(), this.getHeight(), null);      
    }
}
