package semGen.ui;

import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import semGen.SemGen;

public class NetworkModelChooserPanel extends JPanel {
	/**
	 * Create the panel.
	 */
	public NetworkModelChooserPanel() {
		DefaultMutableTreeNode root =
		        new DefaultMutableTreeNode("Network Models");
		createDummyNodes(root);

		NetworkModelChooserTree networkModelTree = new NetworkModelChooserTree(root);
		
		JScrollPane scrollPane = new JScrollPane(networkModelTree);
		add(scrollPane);
	}
	
	/*
	 * Crate dummy nodes on the network model tree.
	 * 
	 * Eventually we'll make a network call to get a list of models on the server
	 */
	private void createDummyNodes(DefaultMutableTreeNode root){
		DefaultMutableTreeNode category = null;
	    
		// Add first category
	    category = new DefaultMutableTreeNode("Examples");
	    root.add(category);
	    
	    category.add(new DefaultMutableTreeNode("Baroreceptor"));
	    category.add(new DefaultMutableTreeNode("CardiovascularDynamics"));
		
		// Add first category
	    category = new DefaultMutableTreeNode("Category 1");
	    root.add(category);
	    
	    category.add(new DefaultMutableTreeNode("Model 1"));
	    category.add(new DefaultMutableTreeNode("Model 2"));
	    category.add(new DefaultMutableTreeNode("Model 3"));
	    category.add(new DefaultMutableTreeNode("Model 4"));
	    
	    // Add second category
	    category = new DefaultMutableTreeNode("Category 2");
	    root.add(category);
	    
	    category.add(new DefaultMutableTreeNode("Model 5"));
	    category.add(new DefaultMutableTreeNode("Model 6"));
	    category.add(new DefaultMutableTreeNode("Model 7"));
	    category.add(new DefaultMutableTreeNode("Model 8"));
	    
	    // Add third category
	    category = new DefaultMutableTreeNode("Category 3");
	    root.add(category);
	    
	    category.add(new DefaultMutableTreeNode("Model 9"));
	    category.add(new DefaultMutableTreeNode("Model 10"));
	    category.add(new DefaultMutableTreeNode("Model 11"));
	    category.add(new DefaultMutableTreeNode("Model 12"));
	}
}
