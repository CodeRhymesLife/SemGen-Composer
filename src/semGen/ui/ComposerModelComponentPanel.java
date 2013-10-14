package semGen.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import semGen.SemGen;
import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.ui.IModelComponent;
import semGen.models.ui.MergedModelComponent;
import semGen.models.ui.ModelComponent;
import ui.HorizontalFlowLayout;
import ui.ObjectActionListener;

class ComposerModelComponentPanel extends JPanel {
	// Add model button
	private AddModelButton _addModelButton;
	private JPanel _jPanelcomponentContainer;
	private ModelComponent modelComponent_1;
	
	// Maps models to model components
	private Map<Model, IModelComponent> _modelComponents;
	
	/**
	 * Create the panel.
	 * @param composerJFrame TODO
	 */
	public ComposerModelComponentPanel() {
		setOpaque(false);
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		AddModelButton dmdlbtnAddModel = new AddModelButton();
		dmdlbtnAddModel.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(dmdlbtnAddModel);
		
		HorizontalFlowLayout vfl = new HorizontalFlowLayout();
		_jPanelcomponentContainer = new JPanel(vfl);
		vfl.setHGap(60);
        vfl.setVGap(60);
        vfl.setHPadding(200);
        vfl.setVPadding(60);
		_jPanelcomponentContainer.setOpaque(false);
		add(_jPanelcomponentContainer);
		
		_modelComponents = new HashMap<Model, IModelComponent>();
	}
	
	/**
	 * Remove a model component from the panel
	 * @param model model to remove
	 */
	public void removeModel(Model model){
		if(model == null)
			throw new NullPointerException("model");
		
		 IModelComponent modelComponent = _modelComponents.remove(model);
		if(modelComponent == null)
			return;
		
		// Add single components for the source models
		// if this is a merged model
		if(model instanceof MergedModel){
			MergedModel mergedModel = (MergedModel)model;
			addModel(mergedModel.getSourceModel1());
			addModel(mergedModel.getSourceModel2());
		}
		
		_jPanelcomponentContainer.remove((Component)modelComponent);
	}
	
	/**
	 * Add model to the panel
	 * @param model model to add
	 */
	public void addModel(Model model){
		IModelComponent modelComponent = null;
		
		// Remove the model if it exists
		removeModel(model);
		
		// If this is a merged model create a merged model component
		if(model instanceof MergedModel){
			MergedModel mergedModel = (MergedModel)model;
			MergedModelComponent mergedModelComponent = new MergedModelComponent(mergedModel);
			
			// Open the property mappings panel when the edit button is clicked
			mergedModelComponent.addEditActionListener(new ObjectActionListener<MergedModel>(mergedModel) {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ComposerJFrame.getInstance().openPropertyMappingsPanel(this.getObject());
				}
			});
			
			// Remove components for the source models
			removeModel(mergedModel.getSourceModel1());
			removeModel(mergedModel.getSourceModel2());
			
			modelComponent = mergedModelComponent;
		}
		// Otherwise create a normal model component
		else
			modelComponent = new ModelComponent(model);
		
		// Add delete listener
		modelComponent.addDeleteActionListener(new ObjectActionListener<Model>(model) {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SemGen.getInstance().getModelRepository().removeModel(this.getObject());
			}
		});
		
		// Add the component to the container
		_jPanelcomponentContainer.add((Component)modelComponent);
		
		// Save model component
		_modelComponents.put(model, modelComponent);
	}
}