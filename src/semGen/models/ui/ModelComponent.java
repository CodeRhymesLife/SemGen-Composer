package semGen.models.ui;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.plaf.IconUIResource;
import javax.swing.Icon;

import semGen.SemGen;
import semGen.models.Model;
import semGen.models.ModelRepository;
import semGen.ui.DeleteButton;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Shape;

/*
 * Represents model on composer
 */
public class ModelComponent extends JPanel implements IModelComponent {

	// Flyout for all models
	// Decalred here because static vars cant be decalred
	// on inner classes unless initialized with const
	private static final ModelFlyout Flyout = new ModelFlyout();

	// Model associated with this model component
	private Model _model;
	
	// Title
	private JLabel _lblTitle;
	
	// Box representing model
	private ModelBox _modelBox;
	private JButton _btnDelete;
	
	/**
	 * Create the panel.
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public ModelComponent(Model model) {
		_model = model;
		
		setOpaque(false);
		
		String name = model != null ? model.getName() : "Model Name";
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel deleteButtonContainer = new JPanel();
		deleteButtonContainer.setPreferredSize(new Dimension(15, 15));
		deleteButtonContainer.setSize(deleteButtonContainer.getPreferredSize());
		deleteButtonContainer.setOpaque(false);
		add(deleteButtonContainer);
		
		_btnDelete = new DeleteButton(deleteButtonContainer.getWidth(), deleteButtonContainer.getHeight());
		_btnDelete.setHorizontalAlignment(SwingConstants.LEFT);
		_btnDelete.setBounds(0, 0, deleteButtonContainer.getWidth(), deleteButtonContainer.getHeight());
		_btnDelete.setVisible(false);
		deleteButtonContainer.setLayout(null);
		deleteButtonContainer.add(_btnDelete);
		
		// Create and add label
		_lblTitle = new JLabel(name);
		_lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(_lblTitle);

		_modelBox = new ModelBox();
		this.add(_modelBox);
		
		// Register the model box with the flyout
		Flyout.RegisterModelBox(_modelBox);
		
		this.setSize(getPreferredSize());
		
		setBounds(new Rectangle(
                getLocation(), getPreferredSize()));
		
		// Create a MergeModelTransferHandler used to merge models
		// via drag and drop
	    setTransferHandler(new MergeModelTransferHandler());
		
		// Since JPanel does not normally support drag-and-drop, we need an
	    // event handler to detect a drag and start the transfer.
	    _modelBox.addMouseMotionListener(new MouseMotionAdapter() {
	      public void mouseDragged(MouseEvent e) {
	    	  Flyout.setVisible(false);
	    	  getTransferHandler().exportAsDrag(ModelComponent.this, e, TransferHandler.COPY);
	      }
	    });
	}
	
	/*
	 * Get the associated model
	 */
	public Model getModel(){
		return _model;
	}
	
	/**
	 * Add action listener to delete button
	 */
	@Override
	public void addDeleteActionListener(ActionListener deleteListener) {
		_btnDelete.addActionListener(deleteListener);
		_btnDelete.setVisible(true);
	}
	
	/*
	 * Handles merging models via drag and drop
	 */
	private class MergeModelTransferHandler extends TransferHandler {
	    /**
	     * We only support importing strings.
	     */
	    @Override
	    public boolean canImport(TransferHandler.TransferSupport info) {
	        // Check for String flavor
	        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	            return false;
	        }
	        return true;
	    }

	    /**
	     * Get the model name of the source model being dragged
	     */
	    @Override
	    protected Transferable createTransferable(JComponent c) {
	    	ModelComponent modelComponent = (ModelComponent)c;
	        return new StringSelection(modelComponent.getModel().getName());
	    }
	    
	    /**
	     * We support copy.
	     */
	    @Override
	    public int getSourceActions(JComponent c) {
	        return TransferHandler.COPY;
	    }
	    
	    /**
	     * Perform the actual import.  We only support drop.
	     * Gets the source model and merges it with the model it's
	     * dropped on
	     */
	    @Override
	    public boolean importData(TransferHandler.TransferSupport info) {
	        if (!info.isDrop()) {
	            return false;
	        }

	        // Get the model name that is being dropped.
	        Transferable transferable = info.getTransferable();
	        String sourceModelName;
	        try {
	        	sourceModelName = (String)transferable.getTransferData(DataFlavor.stringFlavor);
	        }
	        catch (Exception e) { return false; }
	        
	        // Get the source model
	        Model sourceModel = SemGen.getInstance().getModelRepository().getModel(sourceModelName);
	        
	        // If the model doesn't exist return false
	        if(sourceModel == null)
	        	return false;
	        
	        // Get the destination model component
	        ModelComponent destinationModelComponent = (ModelComponent)info.getComponent();
	        
	        SemGen.getInstance().Merge(sourceModel, destinationModelComponent.getModel());
	        return true;
	    }
	}
}
