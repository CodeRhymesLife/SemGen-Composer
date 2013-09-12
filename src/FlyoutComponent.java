import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class FlyoutComponent extends JPanel {
	private JPanel _contentPanel;
	private TriangleComponent _triangle;
	
	/**
	 * Create the panel.
	 */
	public FlyoutComponent() {
		// Create content panel
		_contentPanel = new JPanel();
		_contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPanel.setPreferredSize(new Dimension(100, 100));
		this.add(_contentPanel);
		
		// Create triangel component
		_triangle = new TriangleComponent();
		this.add(_triangle);
		
		this.setSize(getPreferredSize());
		
		// Flyouts are invisible by default. Call showAroundComponent
		// to make it visible
		this.setVisible(false);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(_contentPanel.getWidth() + _triangle.getWidth(),
				_contentPanel.getHeight() + _triangle.getHeight());
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
		private static final int Height = 50;
		private static final int Width = 50;
		
		public TriangleComponent(){
			this.setBackground(Color.WHITE);
		}
		
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
		
		@Override
		public Dimension getPreferredSize(){
			return new Dimension(Width, Height);
		}
	}
}
