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

import javax.swing.JButton;

public class MergedModelComponent extends JPanel {

	private static final int HorizontalGapBetweenSourceModels = 80;
	private static final int VerticalGapBetweenSourceModelsAndMergedModel = 20;
	private static final int MergeBarThickness = 2;
	private JButton _btnEdit;
	
	/**
	 * Create the panel.
	 */
	public MergedModelComponent(MergedModel mergedModel) {
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
		
		ModelComponent modelComponentForModel1 = new ModelComponent(model1);
		modelComponentForModel1.setLocation(0, 0);
		add(modelComponentForModel1);
		
		ModelComponent modelComponentForModel2 = new ModelComponent(model2);
		modelComponentForModel2.setLocation(modelComponentForModel1.getWidth() + HorizontalGapBetweenSourceModels, 0);
		add(modelComponentForModel2);
		
		// I expect them to be the same, but y not...
		int maxSourceModelHeight = Math.max(modelComponentForModel1.getHeight(), modelComponentForModel2.getHeight());
		int sourceModelComponentsCombinedWidth = modelComponentForModel1.getWidth() + HorizontalGapBetweenSourceModels + modelComponentForModel2.getWidth();
		
		ModelComponent modelComponentForMergedModel = new ModelComponent(mergedModel);
		modelComponentForMergedModel.setLocation((sourceModelComponentsCombinedWidth - modelComponentForMergedModel.getWidth())/ 2,
				maxSourceModelHeight + VerticalGapBetweenSourceModelsAndMergedModel);
		add(modelComponentForMergedModel);
		
		_btnEdit = new JButton("Edit");
		_btnEdit.setSize(_btnEdit.getPreferredSize());
		_btnEdit.setLocation((sourceModelComponentsCombinedWidth - _btnEdit.getWidth()) / 2,
				(maxSourceModelHeight - _btnEdit.getHeight()) / 2);
		add(_btnEdit);
		
		JPanel horizontalMergeBar = new JPanel();
		horizontalMergeBar.setBackground(Color.BLACK);
		horizontalMergeBar.setSize(HorizontalGapBetweenSourceModels, MergeBarThickness);
		horizontalMergeBar.setLocation(modelComponentForModel1.getLocation().x + modelComponentForModel1.getWidth(),
				(maxSourceModelHeight - horizontalMergeBar.getHeight()) / 2);
		add(horizontalMergeBar);
		
		JPanel verticalMergeBar = new JPanel();
		verticalMergeBar.setBackground(Color.BLACK);
		verticalMergeBar.setSize(MergeBarThickness, modelComponentForMergedModel.getLocation().y - horizontalMergeBar.getLocation().y);
		verticalMergeBar.setLocation((sourceModelComponentsCombinedWidth - verticalMergeBar.getWidth()) / 2, horizontalMergeBar.getLocation().y);
		add(verticalMergeBar);
		
		int width = Math.max(sourceModelComponentsCombinedWidth, modelComponentForMergedModel.getWidth());
		int height = maxSourceModelHeight +	VerticalGapBetweenSourceModelsAndMergedModel + modelComponentForMergedModel.getHeight();
		this.setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Add an action listener to the edit button
	 * @param editButtonActionListener Action listener to add to the edit button
	 */
	public void addEditActionListener(ActionListener editButtonActionListener){
		_btnEdit.addActionListener(editButtonActionListener);
	}
}
