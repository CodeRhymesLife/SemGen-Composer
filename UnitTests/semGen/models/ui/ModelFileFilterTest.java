package semGen.models.ui;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import semGen.FileHelper;
import semGen.models.ui.ModelFileFilter;


public class ModelFileFilterTest {

	@Test
	public void Accept_Directory_VerifyTrue() throws IOException {
		File directory = FileHelper.getInstance().GetFile(this, FileHelper.DirectoryName);
		
		ModelFileFilter filter = new ModelFileFilter();
		
		// Ensure we accept directories
		assertTrue(filter.accept(directory));
	}

	@Test
	public void Accept_NonSupportedFile_VerifyFalse() throws IOException {
		File fileWithInvalidExtension = FileHelper.getInstance().GetFile(this, FileHelper.InvalidModelExtensionFileName);
		
		ModelFileFilter filter = new ModelFileFilter();
		
		// Ensure we dont accept files with non supported extension
		assertFalse(filter.accept(fileWithInvalidExtension));
	}
	
	@Test
	public void Accept_SupportedFile_VerifyTrue() throws IOException {
		File fileWithValidExtension = FileHelper.getInstance().GetFile(this, FileHelper.ValidModelFileName);
		
		ModelFileFilter filter = new ModelFileFilter();
		
		// Ensure we accept files with supported extensions
		assertTrue(filter.accept(fileWithValidExtension));
	}
	
	@Test
	public void Description_GetValue_VerifyValueIsAsExpected() throws IOException {
		ModelFileFilter filter = new ModelFileFilter();
		assertEquals(ModelFileFilter.Description, filter.getDescription());
	}
}
