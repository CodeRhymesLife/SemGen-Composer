import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import semGen.SemGen;
import semGen.models.Model;
import semGen.models.properties.IModelProperty;
import semGen.models.properties.ModelProperty;
import semGen.ui.ComposerJFrame;

/**
 * 
 */

/**
 * @author rjames
 *
 */
public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Add handler to catch unhandled exceptions
		ComposerExceptionHandler globalExceptionHandler = new ComposerExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);
		
		// Create our frame and set defaults
		ComposerJFrame frame = ComposerJFrame.getInstance();
		frame.initialize();
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Show frame
		frame.setVisible(true);
		
		// Test code. Add dummy models
		// TODO: Remove before launch.
		Model firstDummyModel = new Model("First Model");
		addDummyPropertiesToModel(firstDummyModel, 100);
		SemGen.getInstance().getModelRepository().addModel(firstDummyModel);
		
		Model secondDummyModel = new Model("Second Model");
		addDummyPropertiesToModel(secondDummyModel, 100);
		SemGen.getInstance().getModelRepository().addModel(secondDummyModel);
	}

	/*
	 * Gets dummy properties to add.
	 * 
	 * TODO: Remove this code before ship
	 */
	private static void addDummyPropertiesToModel(Model model, int num){
		for(int i = 0; i < num; i++){
			model.addProperty(new ModelProperty(model, "Property " + i, "Var" + i, "mx + " + i));
		}
	}
}
