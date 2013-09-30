package semGen.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import semGen.SemGen;
import semGen.models.ui.ModelFileChooser;
import ui.FlyoutComponent;
import ui.FlyoutUtility;

public class AddButtonFlyout extends FlyoutComponent {
	private static final String Title = "File Location";
	
	// Used to choose model files
	private JFileChooser _fileChooser;
	
	public AddButtonFlyout(){
		super(Title);

		JPanel contentPanel = this.getContentPanel();
		
		// Stack the buttons
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		// Create the file chooser
		_fileChooser = new ModelFileChooser();
		
		// Add button for selecting local files
		JButton btnLocal = FlyoutUtility.createFlyoutButtonForBoxLayoutPanel("Local");		
		btnLocal.addActionListener(new ActionListener() {
			
			/*
			 * Open the local file chooser when this button is clicked
			 * 
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				AddButtonFlyout.this.openLocalFileChooser();
				AddButtonFlyout.this.setVisible(false);
			}
		});
		contentPanel.add(btnLocal);
		
		// Add button for selecting network models
		JButton btnNetwork = FlyoutUtility.createFlyoutButtonForBoxLayoutPanel("Network");		
		btnNetwork.addActionListener(new ActionListener() {
			
			/*
			 * Open the network file chooser when this button is clicked
			 * 
			 * (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				AddButtonFlyout.this.openNetworkModelChooser();
				AddButtonFlyout.this.setVisible(false);
			}
		});
		contentPanel.add(btnNetwork);
	}
	
	/*
	 * Opens file chooser.
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	private void openLocalFileChooser() {
		int returnVal = _fileChooser.showOpenDialog(ComposerJFrame.getInstance().getContentPane());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = _fileChooser.getSelectedFile();
            
            // When a file is chosen add it to SemGen
            SemGen.getInstance().addModelFromFile(file);
        }
	}
	
	/*
	 * Opens network model chooser for user to choose a model
	 * from the network repository
	 */
	private void openNetworkModelChooser(){
		ComposerJFrame.getInstance().chooseNetowrkModel();
	}
}
