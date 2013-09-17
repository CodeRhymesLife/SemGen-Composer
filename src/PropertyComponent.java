import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.BoxLayout;


public class PropertyComponent extends JPanel {
	private JLabel _lblEquation;

	/**
	 * Create the panel.
	 */
	public PropertyComponent() {
		setMinimumSize(new Dimension(150, 60));
		setMaximumSize(new Dimension(150, 60));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		add(panel_1);
		
		JLabel lblPropertyName = new JLabel("Property Name:");
		panel_1.add(lblPropertyName);
		
		JLabel _lblPropertyValue = new JLabel("Property Value");
		panel_1.add(_lblPropertyValue);
		
		JPanel panel = new JPanel();
		add(panel);
		
		_lblEquation = new JLabel("Equation");
		panel.add(_lblEquation);

	}

}
