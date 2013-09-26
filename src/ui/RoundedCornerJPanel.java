package ui;

import java.awt.Graphics;

import javax.swing.JPanel;

public class RoundedCornerJPanel extends JPanel {
	private int _borderArc;
	
	public RoundedCornerJPanel(int borderArc)
	{
		_borderArc = borderArc;
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
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, _borderArc, _borderArc);
    }
}
