import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Color;


public class PropertyMappingComponent extends JPanel {

	/**
	 * Create the panel.
	 */
	public PropertyMappingComponent() {
		
		PropertyComponent propertyComponent = new PropertyComponent();
		propertyComponent.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(propertyComponent);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		add(panel);
		
		PropertyComponent propertyComponent_1 = new PropertyComponent();
		propertyComponent_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		add(propertyComponent_1);

	}

}
