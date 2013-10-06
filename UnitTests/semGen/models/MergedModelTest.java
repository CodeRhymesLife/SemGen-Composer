package semGen.models;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.properties.IModelProperty;


public class MergedModelTest {
	private Model _sourceModel1;
	private Model _sourceModel2;
	private MergedModel _mergedModel;
	
	@Before
	public void setup(){
		_sourceModel1 = new Model("Source 1");
		_sourceModel2 = new Model("Source 2");
		_mergedModel = new MergedModel("Merged model", _sourceModel1, _sourceModel2);
	}
	
	@Test (expected = NullPointerException.class)
	public void Constructor_NullSourceModel1_VerifyException() throws NullPointerException {
		new MergedModel("model name", null, new Model("test model 2"));
	}

	@Test (expected = NullPointerException.class)
	public void Constructor_NullSourceModel2_VerifyException() throws NullPointerException {
		new MergedModel("model name", new Model("test model 2"), null);
	}
	
	@Test
	public void getSourceModel1_Call_VerifySourceModel1Returned(){
		assertEquals("Verify souce model 1 returned",
				_sourceModel1,
				_mergedModel.getSourceModel1());
	}
	
	@Test
	public void getSourceModel2_Call_VerifySourceModel2Returned(){
		assertEquals("Verify souce model 2 returned",
				_sourceModel2,
				_mergedModel.getSourceModel2());
	}
}
