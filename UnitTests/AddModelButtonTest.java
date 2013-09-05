
import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Test;


public class AddModelButtonTest {
	
	@Test (expected = NullPointerException.class)
	public void Constructor_CreateButton_VerifyExceptionThrownWhenParentNull() {
		new AddModelButton(null);
	}
	
	@Test
	public void Constructor_CreateButton_VerifyCaption() {
		AddModelButton button = new AddModelButton(new JPanel());

		assertEquals("Verify model button caption set properly in the constructor",
				AddModelButton.Caption, button.getText());
	}
}
