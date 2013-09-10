import java.awt.Frame;
import java.io.File;

import javax.swing.JFrame;

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
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Listen for the new model file chosen action
		frame.getAddModelButton().setAddModelButtonActionListener(new AddModelButtonActionListener() {
			
			/*
			 * Listen for new model files and add them to SemGen
			 * 
			 * (non-Javadoc)
			 * @see AddModelButtonActionListener#modelFileChosen(java.io.File)
			 */
			@Override
			public void modelFileChosen(File file) {
				try{
					SemGen.getInstance().addModelFromFile(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		// Show frame
		frame.setVisible(true);
	}

}
