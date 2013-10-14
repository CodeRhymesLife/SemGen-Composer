package semGen.ui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import semGen.SemGen;
import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.ModelRepositoryActionListener;
import semGen.models.properties.ui.PropertyMappingsPanel;
import ui.FlyoutComponent;

/**
 * 
 */

/**
 * @author Ryan James
 *
 */
public class ComposerJFrame extends JFrame {
	// Instance of singleton
	private static final ComposerJFrame Instance = new ComposerJFrame();
	
	// Title for frame
	private final static String Title = "SemGen Composer";
	
	private ComposerModelComponentPanel _modelComponentPanel;
	
	// Property mappings panel
	private PropertyMappingsPanel _propertyMappingsPanel;
	
	// Panel to choose network models from
	private NetworkModelChooserPanel _networkModelChooserPanel;
	
	private ComposerJFrame(){
		// Set the title
		super(Title);
		
		// Listen for items dropped on the composer
		this.getContentPane().setDropTarget(new DropTarget(){
			
			/**
			 * Add dropped model files to SemGen
			 */
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
            	// Extract transfer data.
                File[] modelFilesToAdd = null;
                try {
                    Transferable t = dtde.getTransferable();
                    
                    // We only care about model files
                    if(!t.isDataFlavorSupported(ModelFilesTransferable.getModelFilesDataFlavor()))
                    	return;
                    
                    // Get the model files
                    modelFilesToAdd = (File[])t.getTransferData(ModelFilesTransferable.getModelFilesDataFlavor());
                } catch(UnsupportedFlavorException ufe) {
                    System.out.println("UnsupportedFlavor: " + ufe.getMessage());
                } catch(java.io.IOException ioe) {
                    System.out.println("I/O error: " + ioe.getMessage());
                }
                
                // Add models to SemGen
                for(File modelFile : modelFilesToAdd){
                    SemGen.getInstance().addModelFromFile(modelFile);
                }
                
                super.drop(dtde);
            }
        });
	}
	
	public static ComposerJFrame getInstance(){
		return Instance;
	}
	
	/*
	 * Refreshes the composer's content pane
	 */
	public static void refresh(){
		Container contentPane = getInstance().getContentPane();
		contentPane.validate();
		contentPane.repaint();
	}
	
	/*
	 * Initialize the composer
	 */
	public void initialize()
	{
		Container pane = getContentPane();
		
		// This panel accepts absolutely positioned elements
		pane.setLayout(null);
		
		pane.setBackground(Color.WHITE);
		
		addPropertyMappingsPanel();
		addNetworkModelChooserPanel();
		
		// Add this panel last so we can draw it first
		addModelComponentsPanel();
		
		listForSemGenChanges(SemGen.getInstance());
	}
	
	/*
	 * Show more info about the given model
	 */
	public void ShowMoreInfo(Model model){
		throw new UnsupportedOperationException("More Info");
	}
	
	/*
	 * Opens network model chooser panel
	 */
	public void chooseNetowrkModel(){
		_networkModelChooserPanel.setSize(_networkModelChooserPanel.getPreferredSize());
		_networkModelChooserPanel.setVisible(true);
	}
	
	/**
	 * Open the property mappings panel for the merged model
	 * @param model model to open property mappings panel for
	 */
	public void openPropertyMappingsPanel(MergedModel model){
		if(model == null)
			return;
		
		// Set the added model on the property mappings panel
		_propertyMappingsPanel.setModel(model);
		
		// Align and show the panel
		int x = (this.getContentPane().getWidth() - _propertyMappingsPanel.getWidth()) / 2;
		int y = 100;
		_propertyMappingsPanel.setLocation(new Point(x, y));
		_propertyMappingsPanel.setVisible(true);
		
		ComposerJFrame.refresh();
	}
	
	/**
	 * Add flyout to the composer's content panel at the proper index
	 * @param flyout flyout to add to the composer
	 */
	public static void addFlyout(FlyoutComponent flyout){
		if(flyout == null)
			return;
		
		// Draw the flyout on top of everything else
		ComposerJFrame.getInstance().getContentPane().add(flyout, 1);
	}
	
	private void addModelComponentsPanel(){
		_modelComponentPanel = new ComposerModelComponentPanel();
		
		// Set the size of the component panel to the size of
		// the composer jframe
		Rectangle frameBounds = this.getBounds();
		_modelComponentPanel.setBounds(new Rectangle(
				_modelComponentPanel.getLocation(),
				java.awt.Toolkit.getDefaultToolkit().getScreenSize()));
		
		this.getContentPane().add(_modelComponentPanel);
	}
	
	/*
	 * Add property mappins panel
	 */
	private void addPropertyMappingsPanel(){
		// Add property mappings panel
		_propertyMappingsPanel = new PropertyMappingsPanel();
		
		// Hide the property mappings panel when close is clicked
		_propertyMappingsPanel.addCloseActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_propertyMappingsPanel.setVisible(false);
			}
		});
		_propertyMappingsPanel.setVisible(false);
		
		// Force the property mappings panel to draw over everything else
		this.getContentPane().add(_propertyMappingsPanel, 0);
	}
	
	/*
	 * Add panel used to choose network models
	 */
	private void addNetworkModelChooserPanel(){
		_networkModelChooserPanel = new NetworkModelChooserPanel();
		
		// Place panel in upper right corner
		_networkModelChooserPanel.setLocation(new Point(0, 0));
		_networkModelChooserPanel.setVisible(false);
		this.getContentPane().add(_networkModelChooserPanel, 0);
	}
	
	/*
	 * Listen and handle changes in SemGen
	 */
	private void listForSemGenChanges(SemGen semGen){
		semGen.getModelRepository().addModelRepositoryActionListener(new ModelRepositoryActionListener() {
			
			@Override
			public void modelRemoved(Model model) {
				ComposerJFrame.this._modelComponentPanel.removeModel(model);
				ComposerJFrame.refresh();
			}
			
			@Override
			public void modelAdded(Model model) {
				ComposerJFrame.this._modelComponentPanel.addModel(model);
				
				// Close network model chooser when a model is selected
				_networkModelChooserPanel.setVisible(false);
				
				ComposerJFrame.refresh();
			}
		});
	}
}
