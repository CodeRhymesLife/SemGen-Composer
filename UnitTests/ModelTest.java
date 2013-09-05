import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullName_VerifyExceptionThrow() {
		fail("Not yet implemented");
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullProperties_VerifyExceptionThrow() {
		fail("Not yet implemented");
	}
	
	@Test
	public void getName_getName_VerifyCorrectNameRetrieved() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addProperty_Null_VerifyFalse() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addProperty_AlreadyInModel_VerifyFalse() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addProperty_NewProperty_VerifyFalse() {
		fail("Not yet implemented");
	}
	
	@Test (expected = NullPointerException.class)
	public void addModelActionListener_Null_VerifyExceptionThrown() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addModelActionListener_ValidListener_VerifyListenerAdded() {
		fail("Not yet implemented");
	}
	
	@Test (expected = NullPointerException.class)
	public void removeModelActionListener_Null_VerifyExceptionThrow() {
		fail("Not yet implemented");
	}
	
	@Test
	public void removeModelActionListener_ValidListener_VerifyListenerRemoved() {
		fail("Not yet implemented");
	}
}
