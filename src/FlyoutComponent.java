import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class FlyoutComponent extends JPanel {
	/**
	 * Create the panel.
	 */
	public FlyoutComponent() {
		this.setBackground(Color.BLACK);
		this.setSize(getPreferredSize());
		
		// Flyouts are invisible by default. Call showAroundComponent
		// to make it visible
		this.setVisible(false);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(100, 100);
	}
	
	/*
	 * Show this flyout around the given component 
	 */
	public void showAroundComponent(Component component){
		this.setVisible(false);
		
		this.setLocation(component.getLocationOnScreen());
		this.setBounds(new Rectangle(
				this.getLocation(), this.getPreferredSize()));
		
		this.setVisible(true);
	}

	private class TriangleComponent extends JComponent{
		/* 
		 * Paint triangle
		 * 
		 * (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Dimension size = this.getSize();

			Point p1 = new Point(size.width / 3, (2 * size.height) / 3);
			Point p2 = new Point(size.width / 2, size.height / 3);
			Point p3 = new Point((2 * size.width) / 3, (2 * size.height) / 3);

			int[] xs = { p1.x, p2.x, p3.x };
			int[] ys = { p1.y, p2.y, p3.y };
			Polygon triangle = new Polygon(xs, ys, xs.length);

			g.fillPolygon(triangle);
		}
	}
}
