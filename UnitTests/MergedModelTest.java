import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;


public class MergedModelTest {
	@Test (expected = NullPointerException.class)
	public void Constructor_NullSourceModel1_VerifyException() throws NullPointerException {
		new MergedModel("model name", new ArrayList<IModelProperty>(), null, new Model("test model 2"));
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullSourceModel2_VerifyException() throws NullPointerException {
		new MergedModel("model name", new ArrayList<IModelProperty>(), new Model("test model 2"), null);
	}
}
