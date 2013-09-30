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
		
		// Test code.
		// TODO: Remove before launch.
		SemGen.getInstance().getModelRepository().addModel(new Model("First Model", getDummyProperties(100)));
		SemGen.getInstance().getModelRepository().addModel(new Model("Second Model", getDummyProperties(100)));
	}

	/*
	 * Gets dummy properties to add.
	 * 
	 * TODO: Remove this code before ship
	 */
	private static ArrayList<IModelProperty> getDummyProperties(int num){
		ArrayList<IModelProperty> properties = new ArrayList<IModelProperty>();
		for(int i = 0; i < num; i++){
			properties.add(new ModelProperty("Property " + i, "Var" + i, "mx + " + i));
		}
		
		return properties;
	}
}
