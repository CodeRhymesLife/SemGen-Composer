import javax.swing.JOptionPane;

/*
 * Handles uncaught exceptions in the SemGen Composer
 */
public class ComposerExceptionHandler implements Thread.UncaughtExceptionHandler{

	/*
	 * Handles uncaught exceptions
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Thread.UncaughtExceptionHandler#uncaughtException(java.lang.Thread, java.lang.Throwable)
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable e) {
		String msg;
		
		// If this is an unsupported opertion exception let the user
		// know the operation is coming soon
		if(e instanceof UnsupportedOperationException)
			msg = String.format("%s - Coming soon :)", e.getMessage());
		// Otherwise let them know something went wrong
		else
			msg = "Oops - Something unexpected happenned: " + e.getMessage();
		
		// Show the message in a dialog
		JOptionPane.showMessageDialog(null, msg);
	}

}
