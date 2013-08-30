import javax.swing.JFileChooser;

/*
 * File chooser for SemGen models
 */
public class ModelFileChooser extends JFileChooser {
	public ModelFileChooser(){
		this.setMultiSelectionEnabled(false);
		
		// Only accept files of specific types
		this.setAcceptAllFileFilterUsed(false);
		
		// Show only supported files
		this.addChoosableFileFilter(new ModelFileFilter());
	}
}
