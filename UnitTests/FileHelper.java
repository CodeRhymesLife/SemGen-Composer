import java.io.File;
import java.net.URL;

/*
 * Singleton class used to help with file handling.
 * This class is a singleton so we can use it to build the resources path
 */
public class FileHelper {
	// Resource dir
	public static final String DirectoryName = "";
	
	public static final String InvalidModelExtensionFileName = "InvalidModelExtension.invalidext";
	public static final String ValidModelFileName = "ValidModel.owl";
	
	// Instance of file helper
	private static FileHelper _instance;
	
	private final String _resourcesDir;
	
	private FileHelper(){
		_resourcesDir = "/resources/";
	}
	
	/*
	 * Get instance of file helper
	 */
	public static FileHelper getInstance(){
		if(_instance == null)
			_instance = new FileHelper();
		
		return _instance;
	}
	
	/*
	 * Gets flie from resource dir
	 */
	public File GetFile(Object testClass, String fileName){
		URL url = testClass.getClass().getResource(_resourcesDir + fileName);
		File file = new File(url.getFile());
		return file;
	}
}
