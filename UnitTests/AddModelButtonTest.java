
import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.junit.Test;


public class AddModelButtonTest {
	
	@Test (expected = Exception.class)
	public void Constructor_CreateButton_VerifyExceptionThrownWhenParentNull() {
		new AddModelButton(null);
	}
}
