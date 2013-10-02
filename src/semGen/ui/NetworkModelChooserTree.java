package semGen.ui;

import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class NetworkModelChooserTree extends JTree {
	public NetworkModelChooserTree(DefaultMutableTreeNode root){
		super(root);
		
		this.setDragEnabled(true);
		
		this.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		// Create a NetworkModelChooserTransferHandler used to add models
		// via drag and drop
	    setTransferHandler(new NetworkModelChooserTransferHandler());
	}
	
	/**
	 * Handles adding models by dragging them from the network model chooser
	 * to the composer
	 */
	private class NetworkModelChooserTransferHandler extends TransferHandler {
	    /**
	     * We only support importing to the composer
	     */
	    @Override
	    public boolean canImport(TransferHandler.TransferSupport info) {
	    	if(!info.isDrop()) {
	            return false;
	        }
	    	
	    	// Ensure the data is the right flavor
	        if(!info.isDataFlavorSupported(ModelFilesTransferable.getModelFilesDataFlavor())) {
	            return false;
	        }
	        
	        // We only transport to the composer
	        if(info.getComponent() != ComposerJFrame.getInstance().getContentPane())
	        	return false;
	        
	        return true;
	    }

	    /**
	     * Create a transferable containing model files of all the models that should be transfered
	     */
	    @Override
	    protected Transferable createTransferable(JComponent c) {
	    	JTree tree = (JTree)c;
	    	TreePath[] paths = tree.getSelectionPaths();
	        if(paths != null) {
	            // Get all the leaf nodes (models) from the dragged node
	        	ArrayList<File> modelFiles = new ArrayList<File>();
	            DefaultMutableTreeNode draggedNode = (DefaultMutableTreeNode)paths[0].getLastPathComponent();
	            buildModelFileListFromNode(modelFiles, draggedNode);
	            
	            // Store and return the models in a transferable
	            File[] modelFilesToTransfer = modelFiles.toArray(new File[modelFiles.size()]);
	            return new ModelFilesTransferable(modelFilesToTransfer);
	        }
	        return null;
	    }
	    
	    /**
	     * Recursively look for leaf nodes (models) and add the associated model file to the model files list
	     */
	    private void buildModelFileListFromNode(ArrayList<File> modelFiles, DefaultMutableTreeNode node){
	    	// If this is a leaf (model) add it's file to the list
	    	if(node.isLeaf()){
	    		File dummyFile = new File(node.toString());
	    		modelFiles.add(dummyFile);
	    	}
	    	// Otherwise get all this nodes children and
	    	// build up the model file list
	    	else{
	    		Enumeration<DefaultMutableTreeNode> children = node.children();
	        	while(children.hasMoreElements()){
	        		buildModelFileListFromNode(modelFiles, children.nextElement());
	        	}
	    	}
	    }
	    
	    /**
	     * We support copy.
	     */
	    @Override
	    public int getSourceActions(JComponent c) {
	        return TransferHandler.COPY;
	    }
	}
}
