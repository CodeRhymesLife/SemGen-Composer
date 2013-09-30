package semGen.models.ui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import semGen.SemGen;
import semGen.models.Model;
import semGen.ui.ComposerJFrame;
import ui.FlyoutComponent;
import ui.FlyoutPosition;
import ui.FlyoutUtility;
import ui.ObjectActionListener;


/*
 * Flyout for models
 */
public class ModelFlyout extends FlyoutComponent {
	// Flyout title
	private static final String Title = "Model Actions";
	
	// Shows command buttons
	private JPanel _commandButtonPanel;
	
	// Shows mergeable models
	private JPanel _mergeableModelsPanel;
	
	// Button list of mergeable models
	private JPanel _mergeableModelButtonsListPanel;
	
	// Currently selected model box
	private ModelBox _currentModelBox;
	
	public ModelFlyout(){
		super(Title);
		
		Container composerPane = ComposerJFrame.getInstance().getContentPane();
		
		// Add to composer pane
		composerPane.add(this);		
		
		this.getContentPanel().setLayout(new BoxLayout(this.getContentPanel(), BoxLayout.Y_AXIS));
		
		// Setup content panel
		this.setupCommandButtonPanel();
		this.getContentPanel().add(_commandButtonPanel);
		
		// Setup mergeable models panel
		this.setupMergeableModelsPanel();
		this.getContentPanel().add(_mergeableModelsPanel);
		
		// Show command buttons panel at first
		this.showCommandButtonPanel();
		
		// Close the flyout when the composer pane is clicked
		composerPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
            	ModelFlyout.this.setVisible(false);
            }
		});
	}
	
	/*
	 * Registers a model box with this flyout
	 */
	public void RegisterModelBox(ModelBox modelBox){
		// Listen for mouse events
		modelBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
            	Component target = event.getComponent();
        		
            	ModelFlyout.this._currentModelBox = (ModelBox)target;
    			
    			// Ensure we're showing the command button panel
    			ModelFlyout.this.showCommandButtonPanel();
    			
    			// Show this flyout around the component
    			ModelFlyout.this.showAroundComponent(target, FlyoutPosition.Left);
            }
		});
	}
	
	/*
	 * Setup the mergeable models panel
	 */
	private void setupMergeableModelsPanel(){
		_mergeableModelsPanel = new JPanel();
		
		// Make this panel transparent
		_mergeableModelsPanel.setOpaque(false);
		
		// Stack the buttons
		_mergeableModelsPanel.setLayout(new BoxLayout(_mergeableModelsPanel, BoxLayout.Y_AXIS));
		
		// Add back button
		final ModelFlyout thisFlyout = this;
		_mergeableModelsPanel.add(createActionButton("<-",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisFlyout.showCommandButtonPanel();
			}
		}));
		
		// Add mergeable model buttons panel
		this.setupMergeableModelButtonsListPanel();
		_mergeableModelsPanel.add(_mergeableModelButtonsListPanel);		
		
		// Ensure the button's panel is the same size as the command button panel
		// so the flyout doenst dynamically change size when we show the mergeable
		// panel
		_mergeableModelsPanel.setPreferredSize(_commandButtonPanel.getSize());
		_mergeableModelsPanel.setSize(_mergeableModelsPanel.getPreferredSize());
	}
	
	/*
	 * Setup panel that lists the mergeable models
	 */
	private void setupMergeableModelButtonsListPanel(){
		// Add mergeable model buttons
		_mergeableModelButtonsListPanel = new JPanel();

		// Make this panel transparent
		_mergeableModelButtonsListPanel.setOpaque(false);
		
		// Stack the buttons
		_mergeableModelButtonsListPanel.setLayout(new BoxLayout(_mergeableModelButtonsListPanel, BoxLayout.Y_AXIS));
	}
	
	/*
	 * Setup the content panel
	 */
	private void setupCommandButtonPanel(){
		_commandButtonPanel = new JPanel();
		
		// Make this panel transparent
		_commandButtonPanel.setOpaque(false);
		
		// Stack the buttons
		_commandButtonPanel.setLayout(new BoxLayout(_commandButtonPanel, BoxLayout.Y_AXIS));
		
		// Add buttons
		this.addButtonsToCommandButtonsPanel();
		
		// Set the panel's size
		_commandButtonPanel.setSize(_commandButtonPanel.getPreferredSize());
	}
	
	/*
	 * Add each command button to the content panel
	 */
	private void addButtonsToCommandButtonsPanel(){	
		final ModelFlyout thisFlyout = this;
		
		// Annotate
		_commandButtonPanel.add(createActionButton("Annotate",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SemGen.getInstance().Annotate(thisFlyout._currentModelBox.getModel());
			}
		}));
		
		// Clone
		_commandButtonPanel.add(createActionButton("Clone",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SemGen.getInstance().Clone(thisFlyout._currentModelBox.getModel());
			}
		}));
		
		// Encode
		_commandButtonPanel.add(createActionButton("Encode",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SemGen.getInstance().Encode(thisFlyout._currentModelBox.getModel());
			}
		}));
		
		// Extract
		_commandButtonPanel.add(createActionButton("Extract",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SemGen.getInstance().Extract(thisFlyout._currentModelBox.getModel());
			}
		}));
		
		// Merge
		_commandButtonPanel.add(createActionButton("Merge",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				thisFlyout.showMergeableModelsPanel();
			}
		}));
		
		// More Info
		_commandButtonPanel.add(createActionButton("More Info",
		new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ComposerJFrame.getInstance().ShowMoreInfo(thisFlyout._currentModelBox.getModel());
			}
		}));
	}
	
	/*
	 * Show the command buttons panel
	 */
	private void showCommandButtonPanel(){
		_mergeableModelsPanel.setVisible(false);
		_commandButtonPanel.setVisible(true);
	}
	
	/*
	 * Show the mergeable models panel
	 */
	private void showMergeableModelsPanel(){
		_commandButtonPanel.setVisible(false);
		
		this.buildMergeableModelButtonsListPanel();
		
		_mergeableModelsPanel.setVisible(true);
	}
	
	/*
	 * Creates a button for each model that mergeable
	 * and adds it to the mergeable model button list panel
	 */
	private void buildMergeableModelButtonsListPanel(){
		// Empty the container
		_mergeableModelButtonsListPanel.removeAll();
		
		// Loop through each model is the repository and add it to the panel if
		// the flyout model can merge with it
		final ModelFlyout thisFlyout = this;
		Model flyoutModel = this._currentModelBox.getModel();
		for(Iterator<Model> i = SemGen.getInstance().getModelRepository().getModels().iterator(); i.hasNext(); ) {
			Model mergeableModel = i.next();
			
			// If the flyout model can merge with the model in the repository
			// add it to the panel
			if(SemGen.isMergeable(flyoutModel, mergeableModel))
				// When this button is clicked it will merge the two model
				_mergeableModelButtonsListPanel.add(createActionButton(mergeableModel.getName(),
						new ObjectActionListener<Model>(mergeableModel) {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								SemGen.getInstance().Merge(thisFlyout._currentModelBox.getModel(), this.getObject());	
							}
						}));
		}
		
		_mergeableModelButtonsListPanel.setSize(_mergeableModelButtonsListPanel.getPreferredSize());
	}
	
	/*
	 * Creates a new button that executes the given action when
	 * the button is clicked
	 */
	private JButton createActionButton(String title, ActionListener listener){
		JButton button = FlyoutUtility.createFlyoutButtonForBoxLayoutPanel(title);	
		button.addActionListener(listener);
		return button;
	}
}
