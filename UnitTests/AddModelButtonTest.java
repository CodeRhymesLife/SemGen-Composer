
import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Test;


public class AddModelButtonTest {

	@Test
	public void Constructor_CreateButton_VerifyCaptionSetProperly() {
		AddModelButton button = new AddModelButton(new JPanel());
		assertEquals(AddModelButton.Caption, button.getText());
	}
	
	@Test (expected = Exception.class)
	public void Constructor_CreateButton_VerifyExceptionThrownWhenParentNull() {
		new AddModelButton(null);
	}
}
