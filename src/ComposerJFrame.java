import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.SwingConstants;

/**
 * 
 */

/**
 * @author Ryan James
 *
 */
public class ComposerJFrame extends JFrame {
	// Instance of singleton
	private static final ComposerJFrame Instance = new ComposerJFrame(SemGen.getInstance());
	
	// Title for frame
	private final static String Title = "SemGen Composer";
	
	// Add model button
	private AddModelButton _addModelButton;
	
	// Property mappings panel
	private PropertyMappingsPanel _propertyMappingsPanel;
	
	public ComposerJFrame(SemGen semGen){
		// Set the title
		super(Title);
		
		Container pane = getContentPane();
		
		// This panel accepts absolutely positioned elements
		pane.setLayout(null);
		
		pane.setBackground(Color.WHITE);
		
		addAddModelButton();
		addPropertyMappingsPanel();
		
		listForSemGenChanges(semGen);
	}
	
	public static ComposerJFrame getInstance(){
		return Instance;
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
	 * Create the add model button and add it to the panel
	 */
	private void addAddModelButton(){
		_addModelButton = new AddModelButton(this);
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
	 * Listen and handle changes in SemGen
	 */
	private void listForSemGenChanges(SemGen semGen){
		final ComposerJFrame thisFrame = this;
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
				Container pane = thisFrame.getContentPane();
				pane.add(modelComponent);
				
				// If a new merged model was added
				// show it's property mappings
				if(model instanceof MergedModel){
					// Set the added model on the property mappings panel
					_propertyMappingsPanel.setModel(model);
					
					// Align and show the panel
					int x = (pane.getWidth() - _propertyMappingsPanel.getWidth()) / 2;
					int y = 100;
					_propertyMappingsPanel.setLocation(new Point(x, y));
					_propertyMappingsPanel.setVisible(true);
				}
				
				// Repaint
				pane.validate();
				pane.repaint();
			}
			
			// Temp code until we have a layout solution. If you really want the number
			// of models get them from the repository
			private int numModels = 0;
			private Point getModelPosition(ModelComponent component){
				numModels++;
				return new Point(50 + numModels * component.getWidth() + numModels * 60,
						60 + component.getHeight());
			}
		});
	}
}
