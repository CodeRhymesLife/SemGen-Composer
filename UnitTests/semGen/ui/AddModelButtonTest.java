package semGen.ui;

import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Test;

import semGen.ui.AddModelButton;


public class AddModelButtonTest {
	@Test
	public void Constructor_CreateButton_VerifyCaption() {
		AddModelButton button = new AddModelButton();

		assertEquals("Verify model button caption set properly in the constructor",
				AddModelButton.Caption, button.getText());
	}
}
