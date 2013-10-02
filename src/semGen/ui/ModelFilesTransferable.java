package semGen.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Transferable used to transfer model files
 */
public class ModelFilesTransferable implements Transferable {
	// Data flavor
	private static DataFlavor[] Flavors;
	
	// Model files to transfer
	private File[] _modelFiles;

    public ModelFilesTransferable(File[] modelFiles) {
        _modelFiles = modelFiles;
    }
    
    /*
	 * Get model files data flavor
	 */
	public static DataFlavor getModelFilesDataFlavor(){
    	if(Flavors == null)
    	{
    		// Create the file data flavor
    		try {
                String mimeType = DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + File[].class.getName() + "\"";
                Flavors = new DataFlavor[1];
                Flavors[0] = new DataFlavor(mimeType);
            } catch(ClassNotFoundException e) {
                System.out.println("ClassNotFound: " + e.getMessage());
            }
    	}
    	
    	return Flavors[0];
    }

	/**
	 * Get the model files
	 */
    public Object getTransferData(DataFlavor flavor)
                             throws UnsupportedFlavorException {
        if(!isDataFlavorSupported(flavor))
            throw new UnsupportedFlavorException(flavor);
        return _modelFiles;
    }

    /**
     * Get the data flavors
     */
    public DataFlavor[] getTransferDataFlavors() {
        return Flavors;
    }

    /**
     * Checks to see if the data flavor is supported
     */
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return getModelFilesDataFlavor().equals(flavor);
    }
}