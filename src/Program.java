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
	}
}
