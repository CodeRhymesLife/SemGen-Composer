import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import java.awt.Component;


public class PropertyMappingsPanel extends JPanel {
	public static final int Height = 500;
	public static final int Width = 600;
	private static final int BorderArc = 20;
	
	/**
	 * Create the panel.
	 */
	public PropertyMappingsPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel lblModelName = new JLabel("Model Name");
		lblModelName.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblModelName.setFont(new Font("Calibri", Font.PLAIN, 24));
		add(lblModelName);
		
		JLabel lblPropertyMappings = new JLabel("Property Mappings");
		lblPropertyMappings.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblPropertyMappings);

		this.setSize(this.getPreferredSize());
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(Width, Height);
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
         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, BorderArc, BorderArc);
    }
}
