package vamix;

import javax.swing.JPanel;
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

public class Text extends JPanel {

	
	
	static JPanel downloadPanel;
	
	
	
	static JButton downloadButton;	
	static JPanel audioPanel;
	private JLabel textToolsTitle;
	private JLabel lblEnterTextMessage;
	private JLabel textStartTimeLabel;
	private JLabel lblHowLongShould;
	private JSlider textDisplayTimeSlider;
	private JLabel textDisplayTimeLabel;
	private JButton removeTextButton;
	private JButton saveTextButton;
	private ButtonGroup buttonOptionGroup=new ButtonGroup();
	
	//Important Variables
	private File saveDownloadFile;
	private File currentSelectedVideoFile;
	private File saveTextFile;
	private File currentSelectedAudioFile;
	private File saveAudioNewFile;
	private JTextField enterURLTextField;
	protected DownloadWorker downloadWorker;
	
	public Text() {
		//Title
				textToolsTitle = new JLabel("Text Tools");
				textToolsTitle.setHorizontalAlignment(SwingConstants.CENTER);
				textToolsTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
				textToolsTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
				textToolsTitle.setBounds(0, 0, 259, 27);
				mainGUI.textToolsPanel.add(textToolsTitle);
				
				//Button Group to contain radio buttons
				buttonOptionGroup = new ButtonGroup();
				
				lblEnterTextMessage = new JLabel("Enter Text Message ");
				lblEnterTextMessage.setBounds(10, 38, 234, 14);
				mainGUI.textToolsPanel.add(lblEnterTextMessage);
				
				JTextArea userMessageInput = new JTextArea();
				userMessageInput.setLineWrap(true);
				userMessageInput.setBorder(new LineBorder(new Color(0, 0, 0)));
				userMessageInput.setWrapStyleWord(true);
				userMessageInput.setBounds(20, 63, 214, 73);
				mainGUI.textToolsPanel.add(userMessageInput);
				
				JButton fontButton = new JButton("Font and Size");
				fontButton.setFocusable(false);
				fontButton.setBounds(0, 148, 133, 23);
				mainGUI.textToolsPanel.add(fontButton);
				
				JButton colourButton = new JButton("Colour");
				colourButton.setFocusable(false);
				colourButton.setBounds(131, 148, 128, 23);
				mainGUI.textToolsPanel.add(colourButton);
				
				JLabel lblWhenDoYou = new JLabel("<html>When do you want the text to appear?</html>");
				lblWhenDoYou.setBounds(10, 179, 224, 36);
				mainGUI.textToolsPanel.add(lblWhenDoYou);
				
				JSlider textPlacementSlider = new JSlider();
				textPlacementSlider.setAutoscrolls(true);
				textPlacementSlider.setOpaque(false);
				textPlacementSlider.setValue(0);
				textPlacementSlider.setBounds(19, 210, 200, 26);
				mainGUI.textToolsPanel.add(textPlacementSlider);
				
				textStartTimeLabel = new JLabel("00:00");
				textStartTimeLabel.setBounds(105, 231, 46, 14);
				mainGUI.textToolsPanel.add(textStartTimeLabel);
				
				lblHowLongShould = new JLabel("<html>How long should the text display for?</html>");
				lblHowLongShould.setBounds(10, 245, 224, 34);
				mainGUI.textToolsPanel.add(lblHowLongShould);
				
				textDisplayTimeSlider = new JSlider();
				textDisplayTimeSlider.setValue(0);
				textDisplayTimeSlider.setOpaque(false);
				textDisplayTimeSlider.setAutoscrolls(true);
				textDisplayTimeSlider.setBounds(19, 274, 200, 26);
				mainGUI.textToolsPanel.add(textDisplayTimeSlider);
				
				textDisplayTimeLabel = new JLabel("00:00");
				textDisplayTimeLabel.setBounds(105, 294, 46, 14);
				mainGUI.textToolsPanel.add(textDisplayTimeLabel);
				
				removeTextButton = new JButton("Remove All");
				removeTextButton.setFocusable(false);
				removeTextButton.setBounds(10, 338, 123, 23);
				mainGUI.textToolsPanel.add(removeTextButton);
				
				saveTextButton = new JButton("Save");
				saveTextButton.addActionListener(new ActionListener() {
					private JFileChooser saveNewText;

					public void actionPerformed(ActionEvent arg0) {
						//Create Directory chooser for saving text file
						saveNewText=new JFileChooser();				
						saveNewText.setDialogTitle("Select Directory to save to");
						saveNewText.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
						saveNewText.setAcceptAllFileFilterUsed(false);
						int exitValue=saveNewText.showOpenDialog(mainGUI.contentPane);
						
						if (exitValue == JFileChooser.APPROVE_OPTION) {
							saveTextFile = saveNewText.getSelectedFile();
						}
					}
				});
				saveTextButton.setFocusable(false);
				saveTextButton.setBounds(146, 338, 98, 23);
				mainGUI.textToolsPanel.add(saveTextButton);

	}

}
