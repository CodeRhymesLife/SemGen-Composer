package ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import semGen.ui.ComposerJFrame;


public class FlyoutComponent extends JPanel {
	private JLabel _title;
	private RoundedCornerJPanel _titleAndContentContainer;
	private JPanel _contentPanel;
	private TriangleComponent _triangle;
	
	// Offset of the content panel within the content panel container
	private static final int ContentPanelOffset = 20;
	
	// Content panel container border arc
	private static final int ContentPanelContainerBorderArc = 10;
	
	public FlyoutComponent(){
		this(null);
	}
	
	public FlyoutComponent(String title) {
		// Absolute positioning
		this.setLayout(null);
		
		// Make this transparent
		setOpaque(false);
		
		// Create triangle component
		_triangle = new TriangleComponent();
		this.add(_triangle);
		
		this.addTitleAndContentContainer();
		this.addTitle();
		this.addContentPanel();
		
		// Set the title
		this.setTitle(title);
		
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
		int triangleBuffer = this.getTitleAndContentContainerOffset() * 2;
		
		// Provide enought room for the triangle to move around all sides
		// of the content panel
		return new Dimension(_titleAndContentContainer.getWidth() + triangleBuffer,
				_titleAndContentContainer.getHeight() + triangleBuffer);
	}
	
	/*
	 * Set the title's value. If null then the title is hidden.
	 */
	public void setTitle(String title){
		if(title == null)
			_title.setVisible(false);
		else{
			_title.setText(title);
			_title.setVisible(true);
		}
	}
	
	/*
	 * Show this flyout around the given component
	 */
	public void showAroundComponent(Component component, FlyoutPosition direction){
		// Refresh composer jframe so all objects have a chance to resize
		// Fix for: https://github.com/rcjames1004/SemGen-Composer/issues/18
		ComposerJFrame.refresh();
		
		this.setVisible(false);
		
		// Ensure the triangle is facing in the correct direction
		_triangle.setDirection(direction);
		
		// Set the content panel's size
		_titleAndContentContainer.setSize(_titleAndContentContainer.getPreferredSize());

		// Set the flyout's size
		this.setSize(this.getPreferredSize());
		
		// Set the location of the flyout and each component
		this.setFlyoutLocationAroundComponent(component, direction);
		
		// Set the triangle's location within the flyout
		this.setTriangleLocation(component, direction);
		
		this.setBounds(new Rectangle(
				this.getLocation(), this.getPreferredSize()));
		
		this.setVisible(true);
	}
	
	/*
	 * Properly places the flyout around the given component
	 */
	private void setFlyoutLocationAroundComponent(Component component, FlyoutPosition direction){
		// Get component position relative to flyout parent
		Point componentPosition = SwingUtilities.convertPoint(component.getParent(), component.getLocation(), this.getParent());
		
		Dimension componentSize = component.getSize();
		
		Dimension flyoutSize = this.getSize();
		
		// Get the preferred flyout position based on the direction
		Point preferredFlyoutPosition = new Point();
		switch(direction){
			case Left:
				preferredFlyoutPosition.x = componentPosition.x - flyoutSize.width;
				preferredFlyoutPosition.y = componentPosition.y + componentSize.height / 2 - flyoutSize.height / 2;
				break;
			case Right:
				preferredFlyoutPosition.x = componentPosition.x + componentSize.width;
				preferredFlyoutPosition.y = componentPosition.y + componentSize.height / 2 - flyoutSize.height / 2;
				break;
			case Top:
				preferredFlyoutPosition.x = componentPosition.x + componentSize.width / 2 - flyoutSize.width / 2;
				preferredFlyoutPosition.y = componentPosition.y - flyoutSize.height;
				break;
			case Bottom:
				preferredFlyoutPosition.x = componentPosition.x + componentSize.width / 2 - flyoutSize.width / 2;
				preferredFlyoutPosition.y = componentPosition.y + componentSize.height;
				break;
		}
		
		// Get the actual flyout position.
		//
		// Check to see if the preferred flyout position is outside of its parent's boundaries.
		// If it is outside of the parent's boundaries skoot it over
		Point newFlyoutPosition = new Point();
		switch(direction){
			case Left:
			case Right:
				newFlyoutPosition.x = preferredFlyoutPosition.x;
				
				int lowerBoundY = 0;
				int upperBoundY = this.getParent().getHeight();
				newFlyoutPosition.y = Math.min(upperBoundY, Math.max(lowerBoundY, preferredFlyoutPosition.y));
				break;
			case Top:
			case Bottom:
				newFlyoutPosition.y = preferredFlyoutPosition.y;
				
				int lowerBoundX = 0;
				int upperBoundX = this.getParent().getWidth();
				newFlyoutPosition.x = Math.min(upperBoundX, Math.max(lowerBoundX, preferredFlyoutPosition.x));
				break;
		}
		
		// Set the flyout's location
		this.setLocation(newFlyoutPosition);
	}
	
	/*
	 * Set the triangle's location based on the flyout's position
	 */
	private void setTriangleLocation(Component component, FlyoutPosition direction){
		// Get component position relative to triangle parent
		Point componentPosition = SwingUtilities.convertPoint(component.getParent(), component.getLocation(), _triangle.getParent());
		
		Dimension componentSize = component.getSize();
		
		Dimension flyoutSize = this.getSize();

		// Get the preferred triangle position
		Point preferredTrianglePosition = new Point();
		switch(direction){
			case Left:
				preferredTrianglePosition.x = flyoutSize.width - _triangle.getWidth();
				preferredTrianglePosition.y = componentPosition.y + componentSize.height / 2 - _triangle.getHeight() / 2;
				break;
			case Right:
				preferredTrianglePosition.x = 0;
				preferredTrianglePosition.y = componentPosition.y + componentSize.height / 2 - _triangle.getHeight() / 2;
				break;
			case Top:
				preferredTrianglePosition.x = componentPosition.x + componentSize.width / 2 - _triangle.getWidth() / 2;
				preferredTrianglePosition.y = flyoutSize.height - _triangle.getHeight();
				break;
			case Bottom:
				preferredTrianglePosition.x = componentPosition.x + componentSize.width / 2 - _triangle.getWidth() / 2;
				preferredTrianglePosition.y = 0;
				break;
		}
		
		// Get the actual triangle position.
		//
		// Check to see if the preferred triangle position is outside of its parent's (flyout) boundaries.
		// If it is outside of the parent's boundaries skoot it over
		Point newTrianglePosition = new Point();
		switch(direction){
			case Left:
			case Right:
				newTrianglePosition.x = preferredTrianglePosition.x;
				
				int lowerBoundY = ContentPanelContainerBorderArc + getTitleAndContentContainerOffset();
				int upperBoundY = this.getParent().getHeight() - ContentPanelContainerBorderArc - getTitleAndContentContainerOffset();
				newTrianglePosition.y = Math.min(upperBoundY, Math.max(lowerBoundY, preferredTrianglePosition.y));
				break;
			case Top:
			case Bottom:
				newTrianglePosition.y = preferredTrianglePosition.y;
				
				int lowerBoundX = ContentPanelContainerBorderArc + getTitleAndContentContainerOffset();
				int upperBoundX = this.getParent().getWidth() - ContentPanelContainerBorderArc- getTitleAndContentContainerOffset();
				newTrianglePosition.x = Math.min(upperBoundX, Math.max(lowerBoundX, preferredTrianglePosition.x));
				break;
		}
		
		_triangle.setLocation(newTrianglePosition);
	}
	
	/*
	 * The content panel's offset within the flyout
	 */
	private int getTitleAndContentContainerOffset(){
		return TriangleComponent.Height;
	}
	
	/*
	 * Add the title and content panel container
	 */
	private void addTitleAndContentContainer(){
		// Create content panel container
		_titleAndContentContainer = new RoundedCornerJPanel(ContentPanelContainerBorderArc);
		_titleAndContentContainer.setLayout(new BoxLayout(_titleAndContentContainer, BoxLayout.Y_AXIS));
		_titleAndContentContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		_titleAndContentContainer.setBackground(Color.WHITE);
		
		// Give the triangle enough room to move around the outside of the content panel
		_titleAndContentContainer.setLocation(new Point(this.getTitleAndContentContainerOffset(), this.getTitleAndContentContainerOffset()));
		
		// Add the content panel container
		this.add(_titleAndContentContainer);
	}
	
	/*
	 * Add the title
	 */
	private void addTitle(){
		// Create title component
		_title = new JLabel();
		_title.setFont(new Font("Calibri", Font.PLAIN, 18));
		_title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add space between the container and the title
		_title.setBorder(BorderFactory.createEmptyBorder(10, 10, 0 /* Content panel has top padding */, 10));
		_title.setOpaque(false);
		
		_titleAndContentContainer.add(_title);
	}
	
	/*
	 * Add content panel
	 */
	private void addContentPanel(){
		// Create content panel
		_contentPanel = new JPanel();
		_contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add space between the container and the content panel
		_contentPanel.setBorder(BorderFactory.createEmptyBorder(ContentPanelOffset, ContentPanelOffset, ContentPanelOffset, ContentPanelOffset));
		_contentPanel.setOpaque(false);
		
		// Add the content panel to its container
		_titleAndContentContainer.add(_contentPanel);
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
