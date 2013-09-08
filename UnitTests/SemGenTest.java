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
