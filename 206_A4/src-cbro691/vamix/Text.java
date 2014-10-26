package vamix;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Color;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import java.awt.Rectangle;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import vamixHelpers.ColourSelecter;
import vamixHelpers.FontSelecter;
import vamixHelpers.AudioSliders.SliderListener1;
import vamixWorkers.TextWorker;



import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 
 * This class deals with creating the panel containing the text adding tools.
 * It provides the user with inputs such as font size, style, and colour.
 * It also calls the Worker class that deals with the actual functionality.
 *
 */

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
	public final static FontSelecter fontSelecter=new FontSelecter();
	public final static ColourSelecter colourSelecter=new ColourSelecter();
	
	//Important Variables
	private File saveTextFile;
	private JTextArea userMessageInput;
	static JSlider textPlacementSlider;
	public static Font fontInput;
	public static int sizeInput=12;
	public static Color fontColour;
	
	
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
		
		lblEnterTextMessage = new JLabel("<html>Enter Text Message</html>");
		lblEnterTextMessage.setBounds(10, 38, 234, 14);
		mainGUI.textToolsPanel.add(lblEnterTextMessage);
		
		//Text area to capture user input
		userMessageInput = new JTextArea();
		userMessageInput.setLineWrap(true);
		userMessageInput.setBorder(new LineBorder(new Color(0, 0, 0)));
		userMessageInput.setWrapStyleWord(true);
		userMessageInput.setBounds(20, 63, 214, 73);
		mainGUI.textToolsPanel.add(userMessageInput);
		
		//Make sure the user can't input more characters than the limit
		userMessageInput.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(userMessageInput.getDocument().getLength()>100||userMessageInput.getLineCount()>5){
					changeError();
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(userMessageInput.getDocument().getLength()>100||userMessageInput.getLineCount()>5){
					changeError();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if(userMessageInput.getDocument().getLength()>100||userMessageInput.getLineCount()>5){
					changeError();       		
				}
			}
		});
		
		
		//Button to let the user adjust font and size
		JButton fontButton = new JButton("Font and Size");
		fontButton.setFocusable(false);
		fontButton.setBounds(0, 148, 133, 23);
		mainGUI.textToolsPanel.add(fontButton);
		
		fontButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				//Make the fontSelecter visable so the user can select the font
				//Font selecter open source utility created by:
				//************************************************************
				// * Copyright 2004-2005,2007-2008 Masahiko SAWAI All Rights Reserved. 
				//************************************************************
				fontSelecter.setVisible(true);
				
			}
		});
		
		
		
		//Button to let the user adjust colour
		JButton colourButton = new JButton("Colour");
		colourButton.setFocusable(false);
		colourButton.setBounds(131, 148, 128, 23);
		mainGUI.textToolsPanel.add(colourButton);
		
		colourButton.addActionListener(new ActionListener() {			

			public void actionPerformed(ActionEvent arg0) {
				//Make the colour selecter window visible
				colourSelecter.setVisible(true);
				
			}
		});	
		
		JLabel lblWhenDoYou = new JLabel("<html>When do you want the text to appear in the video?</html>");
		lblWhenDoYou.setBounds(10, 179, 224, 36);
		mainGUI.textToolsPanel.add(lblWhenDoYou);
		
		//Slider to let the user select where to place the text
		textPlacementSlider = new JSlider();
		textPlacementSlider.setAutoscrolls(true);
		textPlacementSlider.setOpaque(false);
		
		textPlacementSlider.setValue(0);
		textPlacementSlider.addChangeListener(new SliderListener1());
		textPlacementSlider.setBounds(19, 210, 200, 26);
		mainGUI.textToolsPanel.add(textPlacementSlider);
		
		//Label showing the video length
		textStartTimeLabel = new JLabel("00:00");
		textStartTimeLabel.setBounds(105, 231, 46, 14);
		mainGUI.textToolsPanel.add(textStartTimeLabel);
		
		lblHowLongShould = new JLabel("<html>How long should the text display for?</html>");
		lblHowLongShould.setBounds(10, 245, 224, 34);
		mainGUI.textToolsPanel.add(lblHowLongShould);
		
		//Slider to choose how long the text displays for
		textDisplayTimeSlider = new JSlider();
		textDisplayTimeSlider.setValue(0);
		textDisplayTimeSlider.addChangeListener(new SliderListener2());
		textDisplayTimeSlider.setMaximum(textPlacementSlider.getMaximum());
		textDisplayTimeSlider.setOpaque(false);
		textDisplayTimeSlider.setAutoscrolls(true);
		textDisplayTimeSlider.setBounds(19, 274, 200, 26);
		mainGUI.textToolsPanel.add(textDisplayTimeSlider);
		
		//Label showing selection of second slider
		textDisplayTimeLabel = new JLabel("00:00");
		textDisplayTimeLabel.setBounds(105, 294, 46, 14);
		mainGUI.textToolsPanel.add(textDisplayTimeLabel);
		
		//Button to remove all text
		removeTextButton = new JButton("Remove All");
		removeTextButton.setFocusable(false);
		removeTextButton.setBounds(10, 338, 123, 23);
		mainGUI.textToolsPanel.add(removeTextButton);
		
		//Button to save changes
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
					
					
					
					//Create worker to add text to video
					TextWorker worker=new TextWorker(sizeInput, fontInput, fontColour,userMessageInput.getText(), 
							saveTextFile.toString()+"/TextOverlay.mp4");
					worker.execute();
					
					
				}
			}
		});
		saveTextButton.setFocusable(false);
		saveTextButton.setBounds(146, 338, 98, 23);
		mainGUI.textToolsPanel.add(saveTextButton);

	}
	
	//Error if the user exceds the character limit
	private void changeError() {
		Runnable changeText = new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(new JPanel(), "Too many characters, limit is 100");
			}
		};       
		SwingUtilities.invokeLater(changeText);
	}
	
	
	//Slider listener class for the first slider which also adjusts the length of the second slider
	//This is too make sure text can't be added for invalid times.
	class SliderListener1 implements ChangeListener {
			
			
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				
				int currentLength = (int)source.getValue();
				String time=Audio.getLengthTime(currentLength);
				textStartTimeLabel.setText(time);
				textDisplayTimeSlider.setMaximum(source.getMaximum()-source.getValue());
	
			}
		}
	
	//Basic slider listener to set time label on the second slider
	class SliderListener2 implements ChangeListener {
		//Listener for second slider, it also updates the third slider
		public void stateChanged(ChangeEvent e) {			
			
			JSlider source = (JSlider)e.getSource();
			
				int currentLength = (int)source.getValue();
				String time=Audio.getLengthTime(currentLength);
				textDisplayTimeLabel.setText(time);
			
		}
		
	}

}
