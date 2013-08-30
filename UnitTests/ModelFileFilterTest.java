import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class ModelFileFilterTest {

	@Test
	public void Accept_Directory_VerifyTrue() throws IOException {
		File file = CreateDirectory("DirectoryName", Long.toString(System.nanoTime()));
		
		ModelFileFilter filter = new ModelFileFilter();
		
		// Ensure we accept directories
		assertTrue(filter.accept(file));
		
		if(!file.delete())
			fail("Failed to delete directory");
	}

	@Test
	public void Accept_NonSupportedFile_VerifyFalse() throws IOException {
		// This is an unsupported extension
		String unsupportedExt = "." + Long.toString(System.nanoTime());
		
		// Create file with unsupported ext
		File file = File.createTempFile("FileName", unsupportedExt);
		
		ModelFileFilter filter = new ModelFileFilter();
		
		// Ensure we dont accept files with non supported extension
		assertFalse(filter.accept(file));
		
		if(!file.delete())
			fail("Failed to delete file");
	}
	
	@Test
	public void Accept_SupportedFiles_VerifyTrue() throws IOException {
		ModelFileFilter filter = new ModelFileFilter();
		
		// Crate a file with each extension and verify it's a valid file type
		for(int i = 0; i < ModelFileFilter.SupportedModelTypes.length; i++)
		{
			String ext = "." + ModelFileFilter.SupportedModelTypes[i];
			
			// Create file
			File file = File.createTempFile("FileName", ext);
			
			// Ensure we dont accept files with non supported extension
			assertTrue(filter.accept(file));
			
			// Clean up
			if(!file.delete())
				fail("Failed to delete file");
		}
	}
	
	@Test
	public void Description_GetValue_VerifyValueIsAsExpected() throws IOException {
		ModelFileFilter filter = new ModelFileFilter();
		assertEquals(ModelFileFilter.Description, filter.getDescription());
	}
	
	/*
	 * Create a directory.
	 */
	private File CreateDirectory(String name, String ext) throws IOException{
		File file = File.createTempFile(name, ext);
		
		if(!file.delete())
			fail("Failed to delete file");
		
		if(!file.mkdir())
			fail("Failed to make directory");
		
		return file;
	}
}
