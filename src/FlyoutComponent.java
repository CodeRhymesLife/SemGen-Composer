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
		// Absolute positioning
		this.setLayout(null);
		
		// Make this transparent
		setOpaque(false);
		
		// Create triangle component
		_triangle = new TriangleComponent();
		this.add(_triangle);
		
		this.addContentPanel();
		
		this.setSize(this.getPreferredSize());
		
		// Flyouts are invisible by default. Call showAroundComponent
		// to make it visible
		this.setVisible(false);
	}
	
	/*
	 * Gets the content panel
	 */
	protected JPanel getContentPanel(){
		return _contentPanel;
	}
	
	/*
	 * Get the flyout's preferred size which is based on
	 * the content panel's size and the triangle's height
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize(){
		int triangleBuffer = this.getContentPanelOffset() * 2;
		
		// Provide enought room for the triangle to move around all sides
		// of the content panel
		return new Dimension(_contentPanel.getWidth() + triangleBuffer,
				_contentPanel.getHeight() + triangleBuffer);
	}
	
	/*
	 * Show this flyout around the given component
	 */
	public void showAroundComponent(Component component, FlyoutPosition direction){
		this.setVisible(false);
		
		// Ensure the triangle is facing in the correct direction
		_triangle.setDirection(direction);
		
		// Set the content panel's size
		_contentPanel.setSize(_contentPanel.getPreferredSize());

		// Set the flyout's size
		this.setSize(this.getPreferredSize());
		
		// Set the location of the flyout and each component
		this.setFlyoutLocationAroundComponent(component, direction);
		
		// Set the triangle's location within the flyout
		this.setTriangleLocation(direction);
		
		this.setBounds(new Rectangle(
				this.getLocation(), this.getPreferredSize()));
		
		this.setVisible(true);
	}
	
	/*
	 * Properly places the flyout around the given component
	 */
	private void setFlyoutLocationAroundComponent(Component component, FlyoutPosition direction){
		// Get the component's position relative to the flyout's position
		Point componentPosition = SwingUtilities.convertPoint(component.getParent(), component.getLocation(), this.getParent());
		Dimension componentSize = component.getSize();
		
		Dimension flyoutSize = this.getSize();
		Point newFlyoutPosition = new Point();
		
		// Update the new flyout position based on the direction
		switch(direction){
			case Left:
				newFlyoutPosition.x = componentPosition.x - flyoutSize.width;
				newFlyoutPosition.y = componentPosition.y + componentSize.height / 2 - flyoutSize.height / 2;
				break;
			case Right:
				newFlyoutPosition.x = componentPosition.x + componentSize.width;
				newFlyoutPosition.y = componentPosition.y + componentSize.height / 2 - flyoutSize.height / 2;
				break;
			case Top:
				newFlyoutPosition.x = componentPosition.x + componentSize.width / 2 - flyoutSize.width / 2;
				newFlyoutPosition.y = componentPosition.y - flyoutSize.height;
				break;
			case Bottom:
				newFlyoutPosition.x = componentPosition.x + componentSize.width / 2 - flyoutSize.width / 2;
				newFlyoutPosition.y = componentPosition.y + componentSize.height;
				break;
		}
		
		// Set the flyout's location
		this.setLocation(newFlyoutPosition);
	}
	
	/*
	 * Set the triangle's location based on the flyout's position
	 */
	private void setTriangleLocation(FlyoutPosition direction){
		Dimension flyoutSize = this.getSize();
		Point newTrianglePosition = new Point();

		switch(direction){
			case Left:
				newTrianglePosition.x = flyoutSize.width - _triangle.getWidth();
				newTrianglePosition.y = flyoutSize.height / 2 - _triangle.getHeight() / 2;
				break;
			case Right:
				newTrianglePosition.x = 0;
				newTrianglePosition.y = flyoutSize.height / 2  - _triangle.getHeight() / 2;
				break;
			case Top:
				newTrianglePosition.x = flyoutSize.width / 2  - _triangle.getWidth() / 2;
				newTrianglePosition.y = flyoutSize.height - _triangle.getHeight();
				break;
			case Bottom:
				newTrianglePosition.x = flyoutSize.width / 2 - _triangle.getWidth() / 2;
				newTrianglePosition.y = 0;
				break;
		}
		
		_triangle.setLocation(newTrianglePosition);
	}
	
	/*
	 * The content panel's offset within the flyout
	 */
	private int getContentPanelOffset(){
		return TriangleComponent.Height;
	}
	
	/*
	 * Add content panel to 
	 */
	private void addContentPanel(){
		// Create content panel
		_contentPanel = new JPanel();
		_contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_contentPanel.setBackground(Color.WHITE);

		// Give the triangle enough room to move around the outsize of the content panel
		_contentPanel.setLocation(new Point(this.getContentPanelOffset(), this.getContentPanelOffset()));
		
		// Add content panel
		this.add(_contentPanel);
	}
	
	/*
	 * Triangle component of the flyout
	 */
	private class TriangleComponent extends JComponent{
		private static final int Height = 20;
		private static final int Width = 20;
		
		// Triangle direction
		private FlyoutPosition _direction;
		
		public TriangleComponent(){
			_direction = FlyoutPosition.Left;
			this.setBackground(Color.WHITE);
			this.setSize(this.getPreferredSize());
		}
		
		/*
		 * Set the direction the triangle is facing
		 */
		public void setDirection(FlyoutPosition direction){
			_direction = direction;
			this.setSize(this.getPreferredSize());
		}
		
		/* 
		 * Paint triangle
		 * 
		 * (non-Javadoc)
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 */
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawPolygon(getTrianglePolygon());
		}
		
		/*
		 * Get the size of the triangle. This value is
		 * based on the direction the triangle is facing
		 * 
		 * (non-Javadoc)
		 * @see javax.swing.JComponent#getPreferredSize()
		 */
		@Override
		public Dimension getPreferredSize(){
			switch(_direction){
				case Left:
				case Right:
					return new Dimension(Height, Width);
				case Top:
				case Bottom:
				default:
					return new Dimension(Width, Height);
			}
		}
		
		/*
		 * Get the polygon represention of the triangle.
		 */
		private Polygon getTrianglePolygon(){
			Dimension size = this.getSize();

			// Three points used to draw the triangle
			Point p1 = null;
			Point p2 = null;
			Point p3 = null;
			
			switch(_direction){
				case Left:
					p1 = new Point(0, 0);
					p2 = new Point(size.width, size.height / 2);
					p3 = new Point(0, size.height);
					break;
				case Right:
					p1 = new Point(0, size.height / 2);
					p2 = new Point(size.width, 0);
					p3 = new Point(size.width, size.height);
					break;
				case Top:
					p1 = new Point(0, 0);
					p2 = new Point(size.width, 0);
					p3 = new Point(size.width / 2, size.height);
					break;
				case Bottom:
					p1 = new Point(0, size.height);
					p2 = new Point(size.width / 2, 0);
					p3 = new Point(size.width, size.height);
					break;
			}

			int[] xs = { p1.x, p2.x, p3.x };
			int[] ys = { p1.y, p2.y, p3.y };
			return new Polygon(xs, ys, xs.length);
		}
	}
}
