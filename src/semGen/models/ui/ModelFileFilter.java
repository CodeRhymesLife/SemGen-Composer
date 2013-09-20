package semGen.models.ui;
import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class ModelFileFilter extends FileFilter {
	// Array of supported model types
	public final static String[] SupportedModelTypes = new String[] {"cellml", "mod", "owl", "sbml", "xml"};
	
	// Description shown in file chooser
	// "SemGen Model (*.supportedType1, *.supportedType2, etc...)"
	public final static String Description =
			String.format("SemGen Model (*.%s)", StringUtils.join(SupportedModelTypes, ", *."));
	
	/*
	 * Accept only directories and the supported file types
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		// Accept directories
		if(f.isDirectory())
			return true;
		
		// Only accept files with the supported types
		return FilenameUtils.isExtension(f.getName(), SupportedModelTypes);
	}

	@Override
	public String getDescription() {
		return Description;
	}

}
