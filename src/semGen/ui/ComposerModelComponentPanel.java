package semGen.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import semGen.models.MergedModel;
import semGen.models.Model;
import semGen.models.ui.MergedModelComponent;
import semGen.models.ui.ModelComponent;
import ui.HorizontalFlowLayout;
import ui.ObjectActionListener;

class ComposerModelComponentPanel extends JPanel {
	// Add model button
	private AddModelButton _addModelButton;
	private JPanel _jPanelcomponentContainer;
	private ModelComponent modelComponent_1;
	
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
	}
	
	public void addModel(Model model){
		Component modelComponent = null;
		
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
			
			modelComponent = mergedModelComponent;
		}
		// Otherwise create a normal model component
		else
			modelComponent = new ModelComponent(model);
		
		// Add the component to the container
		_jPanelcomponentContainer.add(modelComponent);
	}
}