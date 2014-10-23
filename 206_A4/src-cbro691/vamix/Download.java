package vamix;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JRadioButton;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Download extends JPanel {

	
	private JLabel lblDownload;
	private JLabel lblEnterUrlOf;	
	private JLabel openSourceCheckLabel;
	private JCheckBox chckbxThisIsOpen;
	private JLabel lblWhereDoYou;
	private JTextField txtNoLocationSelected;
	private JButton changeSaveDownloadButton;
	static JButton downloadButton;
	private JProgressBar progressBar;	
	
	//Important Variables
	private File saveDownloadFile;
	private JTextField enterURLTextField;
	protected DownloadWorker downloadWorker;
	static JCheckBox checkBox;
	
	/**
	 * This class creates the download GUI panel as well as providing download functionality.
	 * The actual download is handled by the DownloadWorker class but this manages everything else.
	 */
	public Download() {
		
		lblDownload = new JLabel("Download");
		lblDownload.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblDownload.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDownload.setHorizontalAlignment(SwingConstants.CENTER);
		lblDownload.setBounds(0, 0, 259, 28);
		mainGUI.downloadPanel.add(lblDownload);
		//Instructions
		lblEnterUrlOf = new JLabel("<html>Enter URL of file you want to download:</html>");
		lblEnterUrlOf.setBounds(10, 39, 249, 28);
		mainGUI.downloadPanel.add(lblEnterUrlOf);
		//Text field for entering URL
		enterURLTextField = new JTextField();
		
		enterURLTextField.setBounds(10, 67, 237, 20);
		mainGUI.downloadPanel.add(enterURLTextField);
		enterURLTextField.setColumns(10);
		
		openSourceCheckLabel = new JLabel("<html>Is this an open source video or<br> audio file?</html>\r\n");
		openSourceCheckLabel.setBounds(10, 104, 224, 28);
		mainGUI.downloadPanel.add(openSourceCheckLabel);
		//Tick box to confirm if file is open source
		chckbxThisIsOpen = new JCheckBox("This is open source");
		chckbxThisIsOpen.setFocusable(false);
		chckbxThisIsOpen.setBounds(30, 135, 177, 23);
		mainGUI.downloadPanel.add(chckbxThisIsOpen);
		
		lblWhereDoYou = new JLabel("Where do you want to save?");
		lblWhereDoYou.setBounds(10, 170, 224, 14);
		mainGUI.downloadPanel.add(lblWhereDoYou);
		//Button to change the save location of the download
		changeSaveDownloadButton = new JButton("Change");
		changeSaveDownloadButton.addActionListener(new ActionListener() {
			private JFileChooser saveDownload;		

			public void actionPerformed(ActionEvent e) {
				//Create Directory chooser for saving text file
				saveDownload=new JFileChooser();				
				saveDownload.setDialogTitle("Select Directory to download to");
				saveDownload.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
				saveDownload.setAcceptAllFileFilterUsed(false);
				int exitValue=saveDownload.showOpenDialog(mainGUI.contentPane);
				
				if (exitValue == JFileChooser.APPROVE_OPTION) {
					txtNoLocationSelected.setText(saveDownload.getSelectedFile().getName());
					saveDownloadFile=saveDownload.getSelectedFile();
				}
			}
		});
		changeSaveDownloadButton.setFocusable(false);
		changeSaveDownloadButton.setBounds(164, 190, 95, 24);
		mainGUI.downloadPanel.add(changeSaveDownloadButton);
		//Save location selected
		txtNoLocationSelected = new JTextField();
		txtNoLocationSelected.setOpaque(false);
		txtNoLocationSelected.setFocusable(false);
		txtNoLocationSelected.setEditable(false);
		txtNoLocationSelected.setText("No location selected");
		txtNoLocationSelected.setBounds(10, 192, 167, 20);
		mainGUI.downloadPanel.add(txtNoLocationSelected);
		txtNoLocationSelected.setColumns(10);
		
		
		//DOWNLOAD button
		downloadButton = new JButton("Download!");
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Check if button is download or cancel
				if(downloadButton.getText().equals("Download!")){
				//Check if everything is selected
				if(chckbxThisIsOpen.isSelected()&&!txtNoLocationSelected.getText().equals("No location selected")&&!enterURLTextField.getText().equals("")){
					//Download the file
					downloadWorker=new DownloadWorker(enterURLTextField.getText(),saveDownloadFile.getAbsolutePath());
					PropertyChangeListener listener = new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent event) {
							if ("progress".equals(event.getPropertyName())) {
								progressBar.setValue( (Integer)event.getNewValue() );
							}
						}
					};
					
					String url = enterURLTextField.getText();
					String[] parts = url.split("/");
					File file = new File(saveDownloadFile.getAbsoluteFile()+"/"+parts[parts.length - 1]);
					
					// 
					if(file.exists()) {
						Object[] options = {"Resume download", "Override", "Cancel"};
						int response = JOptionPane.showOptionDialog(mainGUI.contentPane, "File already exists!", "ERROR", 0, 0, null, options, 0);
						switch (response) {
						case 0:
							// Allow 'cancel download' button to be pressed once download is resumed
							//cancel.setEnabled(true);
							break;
							// Delete mp3 file if user wants to override
						case 1:
							//cancel.setEnabled(true);
							file.delete();
							break;
						case 2:
							return;
						}
					}
					
					downloadWorker.addPropertyChangeListener(listener);
					downloadButton.setText("Cancel");
					downloadWorker.execute();
				}
					//Otherwise print error
					else{
						if(!chckbxThisIsOpen.isSelected()){
						JOptionPane.showMessageDialog(mainGUI.contentPane, "You must confirm the file is open source","Please confirm open source",JOptionPane.ERROR_MESSAGE);
						}
						else if(enterURLTextField.getText().equals("")){
						JOptionPane.showMessageDialog(mainGUI.contentPane, "You must specify a file to download","Please enter URL",JOptionPane.ERROR_MESSAGE);	
						}
						else if(txtNoLocationSelected.getText().equals("No location selected")){
							JOptionPane.showMessageDialog(mainGUI.contentPane, "You must specify where to save the download","Select download save path",JOptionPane.ERROR_MESSAGE);	
							}
					}
				}
				else{
					downloadButton.setText("Download!");
					downloadWorker.cancel(true);
				}
			}
		});
		downloadButton.setFocusable(false);
		downloadButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		downloadButton.setBounds(48, 279, 147, 44);
		mainGUI.downloadPanel.add(downloadButton);
		//Progress bar for download
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setAutoscrolls(true);
		progressBar.setBounds(10, 334, 224, 27);
		mainGUI.downloadPanel.add(progressBar);
		
		
		
		JLabel lblDoYouWant = new JLabel("<html>Select this option to immediately play the file after downloading.</html>");
		lblDoYouWant.setBounds(10, 224, 224, 43);
		mainGUI.downloadPanel.add(lblDoYouWant);
		//Checkbox to see whether to immediatly play file
		checkBox = new JCheckBox("");
		checkBox.setFocusable(false);
		checkBox.setBounds(116, 251, 32, 20);
		mainGUI.downloadPanel.add(checkBox);

	}

}
