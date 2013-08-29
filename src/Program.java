import javax.swing.JFrame;

/**
 * 
 */

/**
 * @author Ryan
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
		ComposerJFrame frame = new ComposerJFrame();
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
