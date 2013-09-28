package ui;

import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class FlyoutUtility {
	/*
	 * Creates a new button that executes the given action when
	 * the button is clicked
	 */
	public static JButton createFlyoutButtonForBoxLayoutPanel(String title){
		JButton button = new JButton(title);
		
		// Setup ui
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setContentAreaFilled(false);
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// Add space between buttons
		final int buttonMargin = 2;
		button.setBorder(BorderFactory.createEmptyBorder(buttonMargin, buttonMargin, buttonMargin, buttonMargin));
		
		return button;
	}
}
