package semGen.models.ui;
import javax.swing.JFileChooser;

/*
 * File chooser for SemGen models
 */
public class ModelFileChooser extends JFileChooser {
	public ModelFileChooser(){
		// Set the default path the to user's dir
		super(System.getProperty("user.dir"));
		
		this.setMultiSelectionEnabled(false);
		
		// Only accept files of specific types
		this.setAcceptAllFileFilterUsed(false);
		
		// Show only supported files
		this.addChoosableFileFilter(new ModelFileFilter());
	}
}
