package semGen.models.ui;

import javax.swing.JPanel;

import semGen.models.MergedModel;
import semGen.models.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.text.GapContent;

public class MergedModelComponent extends JPanel implements IModelComponent {

	private static final int HorizontalGapBetweenSourceModels = 80;
	private static final int VerticalGapBetweenSourceModelsAndMergedModel = 20;
	private static final int MergeBarThickness = 2;
	private JButton _btnEdit;
	
	// Model associated with this model component
	private MergedModel _model;
	private ModelComponent _modelComponentForMergedModel;
	private ModelComponent _modelComponentForModel1;
	private ModelComponent _modelComponentForModel2;
	
	/**
	 * Create the panel.
	 */
	public MergedModelComponent(MergedModel mergedModel) {
		_model = mergedModel;
		
		setOpaque(false);
		setLayout(null);
		
		// Work around for WindowBuilderPro
		// It passes null into the constructor
		Model model1 = null;
		Model model2 = null;
		if(mergedModel != null) {
			model1 = mergedModel.getSourceModel1();
			model2 = mergedModel.getSourceModel2();
		}
		
		_modelComponentForModel1 = new ModelComponent(model1);
		_modelComponentForModel1.setLocation(0, 0);
		add(_modelComponentForModel1);
		
		_modelComponentForModel2 = new ModelComponent(model2);
		_modelComponentForModel2.setLocation(_modelComponentForModel1.getWidth() + HorizontalGapBetweenSourceModels, 0);
		add(_modelComponentForModel2);
		
		// I expect them to be the same, but y not...
		int maxSourceModelHeight = Math.max(_modelComponentForModel1.getHeight(), _modelComponentForModel2.getHeight());
		int centerX = _modelComponentForModel1.getLocation().x + _modelComponentForModel1.getWidth() + HorizontalGapBetweenSourceModels / 2;
		
		_modelComponentForMergedModel = new ModelComponent(mergedModel);
		_modelComponentForMergedModel.setLocation(centerX - _modelComponentForMergedModel.getWidth() / 2,
				maxSourceModelHeight + VerticalGapBetweenSourceModelsAndMergedModel);
		add(_modelComponentForMergedModel);
		
		_btnEdit = new JButton("Edit");
		_btnEdit.setSize(_btnEdit.getPreferredSize());
		_btnEdit.setLocation(centerX - _btnEdit.getWidth() / 2,
				(maxSourceModelHeight - _btnEdit.getHeight()) / 2);
		add(_btnEdit);
		
		JPanel horizontalMergeBar = new JPanel();
		horizontalMergeBar.setBackground(Color.BLACK);
		horizontalMergeBar.setSize(HorizontalGapBetweenSourceModels, MergeBarThickness);
		horizontalMergeBar.setLocation(_modelComponentForModel1.getLocation().x + _modelComponentForModel1.getWidth(),
				(maxSourceModelHeight - horizontalMergeBar.getHeight()) / 2);
		add(horizontalMergeBar);
		
		JPanel verticalMergeBar = new JPanel();
		verticalMergeBar.setBackground(Color.BLACK);
		verticalMergeBar.setSize(MergeBarThickness, _modelComponentForMergedModel.getLocation().y - horizontalMergeBar.getLocation().y);
		verticalMergeBar.setLocation(centerX - verticalMergeBar.getWidth() / 2, horizontalMergeBar.getLocation().y);
		add(verticalMergeBar);
		
		refreshSize();
	}
	
	/**
	 * Add an action listener to the edit button
	 * @param editButtonActionListener Action listener to add to the edit button
	 */
	public void addEditActionListener(ActionListener editButtonActionListener){
		_btnEdit.addActionListener(editButtonActionListener);
	}

	/**
	 * Returns the merged model associated with this component
	 */
	@Override
	public Model getModel() {
		return _model;
	}

	/**
	 * Add delete listener to merged model
	 */
	@Override
	public void addDeleteActionListener(MouseListener deleteListener) {
		_modelComponentForMergedModel.addDeleteActionListener(deleteListener);
		refreshSize();
	}
	
	/**
	 * Refresh the size of this component
	 */
	private void refreshSize(){
		int maxSourceModelHeight = Math.max(_modelComponentForModel1.getHeight(), _modelComponentForModel2.getHeight());
		int sourceModelComponentsCombinedWidth = _modelComponentForModel1.getWidth() + HorizontalGapBetweenSourceModels + _modelComponentForModel2.getWidth();
		
		int width = Math.max(sourceModelComponentsCombinedWidth, _modelComponentForMergedModel.getWidth());
		int height = maxSourceModelHeight +	VerticalGapBetweenSourceModelsAndMergedModel + _modelComponentForMergedModel.getHeight();
		this.setPreferredSize(new Dimension(width, height));
	}
}
