package semGen.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import semGen.SemGemConstants;

public class DeleteButton extends JButton {

	// Image path
	private final static String RedXPath = SemGemConstants.ImagesDir + "red-x.png";
		
	public DeleteButton(int width, int height){
		setContentAreaFilled(false);
		setBorder(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		ImageIcon redX = new ImageIcon(RedXPath);
		// Resize image
		{
			Image img = redX.getImage();
			Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			redX.setImage(resizedImage);
			setIcon(redX);
		}
		
		setPreferredSize(new Dimension(width, height));
	}
}
