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
import semGen.models.ui.ModelComponent;

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
	
	// Add model button
	private AddModelButton _addModelButton;
	
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
		
		addAddModelButton();
		addPropertyMappingsPanel();
		addNetworkModelChooserPanel();
		
		listForSemGenChanges(SemGen.getInstance());
	}
	
	/*
	 * Get the add model button
	 */
	public AddModelButton getAddModelButton(){
		return _addModelButton;
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
	
	/*
	 * Create the add model button and add it to the panel
	 */
	private void addAddModelButton(){
		_addModelButton = new AddModelButton();
		_addModelButton.setVerticalAlignment(SwingConstants.TOP);
		this.getContentPane().add(_addModelButton);
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
		this.getContentPane().add(_propertyMappingsPanel);
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
				throw new UnsupportedOperationException("Composer remove model");
			}
			
			@Override
			public void modelAdded(Model model) {
				// Place model component on frame
				// this is all temp code and needs to be replaced with some kind
				// of smart layout class/logic
				ModelComponent modelComponent = new ModelComponent(model);
				modelComponent.setLocation(this.getModelPosition(modelComponent));
				modelComponent.setBounds(new Rectangle( modelComponent.getLocation(),
						modelComponent.getPreferredSize()));
				
				// Repaint frame
				Container pane = ComposerJFrame.this.getContentPane();
				pane.add(modelComponent);
				
				// If a new merged model was added
				// show it's property mappings
				if(model instanceof MergedModel){
					// Set the added model on the property mappings panel
					_propertyMappingsPanel.setModel((MergedModel)model);
					
					// Align and show the panel
					int x = (pane.getWidth() - _propertyMappingsPanel.getWidth()) / 2;
					int y = 100;
					_propertyMappingsPanel.setLocation(new Point(x, y));
					_propertyMappingsPanel.setVisible(true);
				}
				
				// Close network model chooser when a model is selected
				_networkModelChooserPanel.setVisible(false);
				
				// Repaint
				pane.validate();
				pane.repaint();
			}
			
			// Temp code until we have a layout solution. If you really want the number
			// of models get them from the repository
			private int numModels = 0;
			private Point getModelPosition(ModelComponent component){
				int row = numModels / 5 + 1;
				int column = (numModels % 5);
				
				numModels++;
				return new Point((50 + component.getWidth() + 60) * (column + 1),
						(60 + component.getHeight()) * row);
			}
		});
	}
}
