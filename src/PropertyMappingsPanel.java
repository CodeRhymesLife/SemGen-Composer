import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;


public class PropertyMappingsPanel extends JPanel {
	public static final int Height = 500;
	public static final int Width = 600;
	private static final int BorderArc = 20;
	private JButton _btnDone;
	private JLabel _lblModelName;
	
	/**
	 * Create the panel.
	 */
	public PropertyMappingsPanel() {
		setBackground(Color.WHITE);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		_lblModelName = new JLabel("Model Name");
		_lblModelName.setAlignmentX(Component.CENTER_ALIGNMENT);
		_lblModelName.setFont(new Font("Calibri", Font.PLAIN, 24));
		add(_lblModelName);
		
		JLabel lblPropertyMappings = new JLabel("Property Mappings");
		lblPropertyMappings.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblPropertyMappings);
		
		JPanel propertiesPanel = new JPanel();
		propertiesPanel.setBackground(Color.WHITE);
		add(propertiesPanel);
		
		Component aboveButtonGap = Box.createRigidArea(new Dimension(0,20));
		add(aboveButtonGap);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setOpaque(false);
		add(buttonsPanel);
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		
		JButton btnAddNew = new JButton("Add New");
		btnAddNew.setBackground(new Color(102, 204, 0));
		buttonsPanel.add(btnAddNew);
		
		Component betweenButtonGap = Box.createRigidArea(new Dimension(40,0));
		buttonsPanel.add(betweenButtonGap);
		
		_btnDone = new JButton("Done");
		_btnDone.setBackground(Color.WHITE);
		buttonsPanel.add(_btnDone);
		
		Component belowButtonGap = Box.createRigidArea(new Dimension(0,20));
		add(belowButtonGap);
		
		this.setSize(new Dimension(Width, Height));
	}
	
	/*
	 * Add close action listener
	 */
	public void addCloseActionListener(ActionListener listener){
		_btnDone.addActionListener(listener);
	}
	
	/*
	 * Set a new model in the property mappings panel
	 */
	public void setModel(Model model){
		_lblModelName.setText(model.getName());
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
