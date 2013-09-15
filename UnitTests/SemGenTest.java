import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;


public class SemGenTest {
	private TestModelRepositoryActionListener _listener;
	private SemGen _semGen;
	
	@Before
	public void setUp() throws Exception {
		_listener = new TestModelRepositoryActionListener();
		_semGen = new SemGen();
	}

	@Test
	public void Constructor_CreateSemGen_VerifyModelRepositoryNotNull() {
		assertNotNull("Verify Model Repository not null",
				_semGen.getModelRepository());
	}

	@Test
	public void isMergeable_NullModel1_VerifyFalse() {
		assertFalse("Verify a null model is not mergeable",
				SemGen.isMergeable(null, new Model("test model")));
	}
	
	@Test
	public void isMergeable_NullModel2_VerifyFalse() {
		assertFalse("Verify a null model is not mergeable",
				SemGen.isMergeable(new Model("test model"), null));
	}
	
	@Test
	public void isMergeable_SameModel_VerifyFalse() {
		Model model = new Model("test model");
		assertFalse("Verify a model is not mergeable with itself",
				SemGen.isMergeable(model, model));
	}
	
	@Test
	public void isMergeable_DifferentModels_VerifyTrue() {
		Model model1 = new Model("test model 1");
		Model model2 = new Model("test model 2");
		assertTrue("Verify different models are mergeable",
				SemGen.isMergeable(model1, model2));
	}
	
	@Test
	public void isMergeable_RegularModelAndMergedModel_VerifyTrue() {
		Model model1 = new Model("test model 1");
		Model model2 = new MergedModel("test model 2", new Model("source 1"), new Model("source 2"));
		assertTrue("Verify regular models are mergeable with merged models",
				SemGen.isMergeable(model1, model2));
	}
	
	@Test
	public void isMergeable_RegularModelAndMergedModelWithRegularModelAsSource1_VerifyFalse() {
		Model model1 = new Model("test model 1");
		Model model2 = new MergedModel("test model 2", model1, new Model("source 2"));
		
		assertFalse("Verify a merged model can't merge with its source 1 model",
				SemGen.isMergeable(model1, model2));
		
		// Swap argument order
		assertFalse("Verify a merged model can't merge with its source 1 model",
				SemGen.isMergeable(model2, model1));
	}
	
	@Test
	public void isMergeable_RegularModelAndMergedModelWithRegularModelAsSource2_VerifyFalse() {
		Model model1 = new Model("test model 1");
		Model model2 = new MergedModel("test model 2", new Model("source 1"), model1);
		
		assertFalse("Verify a merged model can't merge with its source 2 model",
				SemGen.isMergeable(model1, model2));
		
		// Swap argument order
		assertFalse("Verify a merged model can't merge with its source 2 model",
				SemGen.isMergeable(model1, model2));
	}
	
	@Test
	public void isMergeable_ModelsShareAncestor_VerifyFalse() {
		Model ancestorModel = new Model("ancestor model");
		Model model1 = new MergedModel("test model 1", ancestorModel, new Model("test model 1 only ancestor"));
		Model model2 = new MergedModel("test model 2", ancestorModel, new Model("test model 2 only ancestor"));
		
		assertFalse("Verify models are not mergeable if they share the same ancestor",
				SemGen.isMergeable(model1, model2));
		
		// Swap argument order
		assertFalse("Verify models are not mergeable if they share the same ancestor",
				SemGen.isMergeable(model1, model2));
	}
	
	@Test
	public void addModelFromFile_ValidModelFile_VerifyModelCreated() throws Exception {
		File validModelFile = FileHelper.getInstance().GetFile(this, FileHelper.ValidModelFileName);
		
		assertNotNull("Verify Model created successfully",
				_semGen.addModelFromFile(validModelFile));
	}
	
	@Test
	public void addModelFromFile_ExistingModel_VerifyModelNotCreated() throws Exception {
		File validModelFile = FileHelper.getInstance().GetFile(this, FileHelper.ValidModelFileName);
		
		// Add model
		_semGen.addModelFromFile(validModelFile);
		
		assertNull("Verify Model not created the second time we attempt to add it to the repository",
				_semGen.addModelFromFile(validModelFile));
	}
	
	@Test
	public void addModelFromFile_ValidModelFile_VerifyModelProperties() throws Exception {
		File validModelFile = FileHelper.getInstance().GetFile(this, FileHelper.ValidModelFileName);
		
		// Get model
		Model validModel = _semGen.addModelFromFile(validModelFile);
		
		String modelName = FilenameUtils.getBaseName(FileHelper.ValidModelFileName);
		assertEquals("Verify Modelname is correct",
				modelName,
				validModel.getName());
		
		// TODO: Validate other properties
	}
	
	@Test
	public void addModelFromFile_ValidModelFile_VerifyModelAddedToRepository() throws Exception {
		File validModelFile = FileHelper.getInstance().GetFile(this, FileHelper.ValidModelFileName);
		
		// Add repository listener
		_semGen.getModelRepository().addModelRepositoryActionListener(_listener);
		
		// Get model
		Model validModel = _semGen.addModelFromFile(validModelFile);
		
		assertSame("Verify the added model was added to the model repository",
				validModel,
				_listener.LastAddedModel);
	}
}
