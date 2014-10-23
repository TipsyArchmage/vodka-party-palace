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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import javax.swing.SwingWorker;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * 
 *This class manages all the GUI tools and functionality of the audio manipulation
 *section. This includes being able to call the two different worker classes that actually
 *carry out the functionality. It also calls the audio slider class which actually manages the 
 *different sliders.
 *
 */

public class Audio extends JPanel{	
	
	static JPanel downloadPanel;	
	static JButton downloadButton;
	
	private JLabel lblAudioTools;
	private JButton removeAudioButton;
	private JLabel lblSelectAudioTo;
	private JTextField currentAudioTrack;
	private JButton changeAudioTrackButton;
	
	public static File outputTEMPFile;
	private JButton btnNewButton;
	private ButtonGroup buttonOptionGroup=new ButtonGroup();
	
	//Important Variables
	private File saveDownloadFile;
	//private File currentSelectedVideoFile;
	private File saveTextFile;
	private File currentSelectedAudioFile;
	static File saveAudioNewFile;
	private EmbeddedMediaPlayer audio;
	private JTextField enterURLTextField;
	protected DownloadWorker downloadWorker;
	protected String outputType;
	private ProcessBuilder builder;
	protected JFileChooser saveRemove;
	protected File saveRemoveFile;
	//private String outputNameTextVideo;
	private String outputNameTextAudio;
	protected int exitValue;
	private String outputNameTextVideo;
	private AudioWorker worker;
	static JRadioButton replaceAudioOptionRadioBtn;
	/**
	 * @param args
	 */
	public Audio() {
		//Title of section
		lblAudioTools = new JLabel("Audio Tools\r\n");
		lblAudioTools.setBounds(0, 0, 259, 27);
		lblAudioTools.setHorizontalAlignment(SwingConstants.CENTER);
		lblAudioTools.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAudioTools.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainGUI.audioPanel.add(lblAudioTools);
		
		//Button to strip all audio
		removeAudioButton = new JButton("Remove All Existing Audio");
		removeAudioButton.setFocusable(false);
		removeAudioButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		removeAudioButton.setBounds(0, 38, 259, 27);
		removeAudioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				//Ask the user if they want to remove all the audio or remove it and save it
				Object[] options = {"Remove Audio", "Remove Audio and Save it to a seperate File", "Cancel"};
				int n = JOptionPane.showOptionDialog(mainGUI.contentPane, "Do you want to remove the audio and discard it or save it to " +
						"a new audio file? The original file will not be altered", 
						"Please select an option",  JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[2]);
				
				//Perform an action depending on this
				switch (n) {
				case 0:
					// Creates a video file with no audio
					outputType = "video";
					try {
						if (!checkAudio()) {
							JOptionPane.showMessageDialog(mainGUI.contentPane, "Selected audio overlay file is not an audio file!", "Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(new JPanel(), "Error!");
					}
					
					//Ask where you want to save to
					//Create Directory chooser for saving text file
					saveRemove=new JFileChooser();				
					saveRemove.setDialogTitle("Select Directory to save to");
					saveRemove.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
					saveRemove.setAcceptAllFileFilterUsed(false);
					exitValue=saveRemove.showOpenDialog(mainGUI.contentPane);
					
					if (exitValue == JFileChooser.APPROVE_OPTION) {
						
						saveRemoveFile=saveRemove.getSelectedFile();
					}
					
					stripAudio(outputType);
					break;
				case 1:
					// Creates a video file with no audio and a seperate audio file
					outputType = "video.audio";
					try {
						// Check if selected file contains video
						if (!checkVideo()) {
							JOptionPane.showMessageDialog(mainGUI.contentPane, "Selected video file does not contain video!", "Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (HeadlessException e1) {
						JOptionPane.showMessageDialog(new JPanel(), "Error!");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(new JPanel(), "Error!");
					}
					
					//Ask where you want to save to
					//Create Directory chooser for saving text file
					saveRemove=new JFileChooser();				
					saveRemove.setDialogTitle("Select Directory to save to");
					saveRemove.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
					saveRemove.setAcceptAllFileFilterUsed(false);
					exitValue=saveRemove.showOpenDialog(mainGUI.contentPane);
					
					if (exitValue == JFileChooser.APPROVE_OPTION) {
						
						saveRemoveFile=saveRemove.getSelectedFile();
					}
					
					stripAudio(outputType);
					break;
				case 2:
					//Cancels the option
					return;
				}
				
			}
		});
		mainGUI.audioPanel.add(removeAudioButton);
		
		
		//Label for audio track selection
		lblSelectAudioTo = new JLabel("Select Audio Track to Use:");
		lblSelectAudioTo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSelectAudioTo.setBounds(0, 76, 224, 14);
		mainGUI.audioPanel.add(lblSelectAudioTo);
		//Text field to show currently selected audio
		currentAudioTrack = new JTextField();
		currentAudioTrack.setFocusable(false);
		currentAudioTrack.setEditable(false);
		currentAudioTrack.setColumns(10);
		currentAudioTrack.setBounds(0, 92, 165, 20);
		mainGUI.audioPanel.add(currentAudioTrack);
		
		
		//Button to change selected audio to use
		changeAudioTrackButton = new JButton("Change");
		changeAudioTrackButton.addActionListener(new ActionListener() {
			private JFileChooser selectAudioChooser;
			public void actionPerformed(ActionEvent e) {
				//Select the audio file
				selectAudioChooser=new JFileChooser();				
				selectAudioChooser.setDialogTitle("Select Audio File to Use");
				selectAudioChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);					
				int exitValue=selectAudioChooser.showOpenDialog(mainGUI.contentPane);
								
				if (exitValue == JFileChooser.APPROVE_OPTION) {					
				int accept=0;
				int audioLength=0;
				currentSelectedAudioFile = selectAudioChooser.getSelectedFile();
				//Check audio is actually audio
				try {
					if(checkAudioOverlay()){
						while(accept==0){								
							
							//Set up a playback component so the selected audio can actually be parsed
							currentAudioTrack.setText(selectAudioChooser.getSelectedFile().getName());
							
							mainGUI.mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
							mainGUI.mediaPlayerComponent.setBounds(0, 0, 420, 304);
							mainGUI.mediaPlayerComponent.getVideoSurface().setBounds(0, 0, 420, 305);
							mainGUI.mediaPlayerComponent.getVideoSurface().setBackground(SystemColor.info);
							Canvas canvas = new Canvas();
							canvas.setBounds(0, 0, 0, 0);
							canvas.setVisible(true);
							canvas.setBackground(SystemColor.info);
							// Creates a media player to play media
							MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
							CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
							EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
							mediaPlayer.setVideoSurface(videoSurface);
							VideoPlayback.playerPanel.setLayout(null);
							// Adds the canvas to the center of the video panel
							VideoPlayback.playerPanel.add(canvas);
							audio = mainGUI.mediaPlayerComponent.getMediaPlayer();
							VideoPlayback.playerPanel.add(mainGUI.mediaPlayerComponent);
							mainGUI.mediaPlayerComponent.setLayout(null);
							
							//Get the length of the audio
							audio.playMedia(currentSelectedAudioFile.toString());
							audio.parseMedia();
							audioLength=(int) audio.getLength()/1000;
							audio.stop();
							if(audioLength==0){
								//Occasionally the audio isn't parsed properly
								//This loops until it is
							}
							else{
								accept=1;
							}
						}	
					}
					else{
						JOptionPane.showMessageDialog(mainGUI.contentPane,"Not an audio file",
								"Type Error",JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
					
					
					//Set slider lengths to default values
					AudioSliders.extractStartSlider.setValue(0);
					AudioSliders.extractLengthAudio.setValue(0);
					AudioSliders.extractStartSlider.setMaximum(audioLength);
					String audioLengthTime=getLengthTime(audioLength);
					//System.out.print(audioLengthTime);
					AudioSliders.endTimeAudioLabel.setText(audioLengthTime);
					//totalLength.setText(audioLengthTime);
					
				}
			}
		});
		
		
		changeAudioTrackButton.setFocusable(false);
		changeAudioTrackButton.setBounds(164, 90, 95, 24);
		mainGUI.audioPanel.add(changeAudioTrackButton);
		//Initialise sliders
		AudioSliders sliders=new AudioSliders();

		//Option to overlay audio
		JRadioButton overlayOptionRadioBtn = new JRadioButton("Overlay Existing Audio");
		overlayOptionRadioBtn.setFont(new Font("Dialog", Font.BOLD, 11));
		overlayOptionRadioBtn.setOpaque(false);
		overlayOptionRadioBtn.setSelected(true);
		overlayOptionRadioBtn.setFocusable(false);
		overlayOptionRadioBtn.setBounds(0, 320, 182, 23);
		
		//NOTE: Does not function. Has been removed from GUI until further implementation
		//mainGUI.audioPanel.add(overlayOptionRadioBtn);

		//Option to replace audio
		 replaceAudioOptionRadioBtn = new JRadioButton("Replace Existing Audio");
		replaceAudioOptionRadioBtn.setFont(new Font("Dialog", Font.BOLD, 11));
		replaceAudioOptionRadioBtn.setOpaque(false);
		replaceAudioOptionRadioBtn.setFocusable(false);
		replaceAudioOptionRadioBtn.setBounds(0, 335, 195, 23);
		replaceAudioOptionRadioBtn.setSelected(true);
		mainGUI.audioPanel.add(replaceAudioOptionRadioBtn);
		
		buttonOptionGroup.add(replaceAudioOptionRadioBtn);
		//buttonOptionGroup.add(overlayOptionRadioBtn);
		
		//Button to start process
		btnNewButton = new JButton("Go!");
		btnNewButton.addActionListener(new ActionListener() {
			private JFileChooser saveNewAudio;			
			
			public void actionPerformed(ActionEvent e) {
				
				//Disable sliders so the values can't be changed while processing
				AudioSliders.videoLocationSlider.setEnabled(false);
				AudioSliders.extractLengthAudio.setEnabled(false);
				AudioSliders.extractStartSlider.setEnabled(false);
				
				//Create Directory chooser for saving audio file
				saveNewAudio=new JFileChooser();				
				saveNewAudio.setDialogTitle("Select Directory to save to");
				saveNewAudio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
				saveNewAudio.setAcceptAllFileFilterUsed(false);
				int exitValue=saveNewAudio.showOpenDialog(mainGUI.contentPane);
				
				if (exitValue == JFileChooser.APPROVE_OPTION) {
					saveAudioNewFile = saveNewAudio.getSelectedFile();
					
					//Create temporary file name that will be deleted at the end
					int rand=(int)(Math.random()*1000); 
					//Temporary File Location
					String outputTEMPTextAudio=saveAudioNewFile+"/TEMPORARY_FILE"+rand+".mp4";
					
					int totalTime=AudioSliders.extractStartSlider.getValue();
					//Trim to specified length in minutes and seconds
					int startMinutes=totalTime/60;
					int startSeconds=totalTime-startMinutes*60;
					//Trim this as well
					int trimTime=AudioSliders.extractLengthAudio.getValue();
					int durMinutes=trimTime/60;
					int durSeconds=trimTime-durMinutes;
					//OLD FUNCTIONALITY
					//There currently exists no way to add silence to the start of audio
					//This was my planned workaround for adding audio later in the video
					//but the processing costs were huge
					int secSilence=AudioSliders.videoLocationSlider.getValue();
					
					//Execute the main audio worker class, this worker class will call either
					//Replace or overlay as nessasary
					
					AudioWorker worker=new AudioWorker(null, currentSelectedAudioFile.toString()
							, outputTEMPTextAudio, 4,startMinutes, startSeconds,durMinutes,durSeconds, secSilence);
					
					worker.execute();
					

				}
				else{
					AudioSliders.videoLocationSlider.setEnabled(true);
					AudioSliders.extractLengthAudio.setEnabled(true);
					AudioSliders.extractStartSlider.setEnabled(true);
				}
				
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(180, 322, 79, 45);
		mainGUI.audioPanel.add(btnNewButton);

	}
	
	//Method to get the length in the right format
	protected static String getLengthTime(int audioLength) {
		
		int minutes=audioLength/60;
		int seconds=audioLength-(minutes*60);
		String m = String.valueOf(minutes);
		String s = String.valueOf(seconds);
		// Adds a leading 0 if the hours/minutes/seconds value only contains one digit
		if (m.length() == 1) {
			m = "0" + m;
		}
		if (s.length() == 1) {
			s = "0" + s;
		}
		return (m + ":" + s);
		
	}

	//Method to completly strip audio
	public void stripAudio(String outputType) {
		// Remove file extension from output file if one exists, and change to .mp4		
		
		String nameNoExtension=saveRemoveFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
		nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
		
	    outputNameTextVideo = nameNoExtension+" Output No Audio.mp4";
		
		outputNameTextAudio = nameNoExtension+" Output Only Audio.mp4";
		
		//Enter inputs for the worker String currentVideoFilePath, String currentAudioFilePath, String outputNameText, mode select
		//1-> Silent Video and audio file
		//2-> Silent Video
		

		try {
			if (outputType.equals("video.audio")) {
				// Creates an audio file containing the audio stripped from the selected video file
				StripAudioWorker worker=new StripAudioWorker(mainGUI.currentSelectedVideoFile.toString(), null, outputNameTextAudio, 1);
				builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameTextAudio);
				worker.execute();
				
				
			}
			else if (outputType.equals("video")||outputType.equals("video.audio")) {
				// Creates a video file containing the video selected with its audio stripped
				StripAudioWorker worker=new StripAudioWorker(mainGUI.currentSelectedVideoFile.toString(), null, outputNameTextVideo, 2);
				builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-vcodec", "copy", "-an", outputNameTextVideo);
				worker.execute();
				
			}
			else {
				try{
					
				}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(new JPanel(), "Error!");
				}
			}



		}
		catch (Exception e1) {
			if(outputType.equals("audio")||(outputType.equals("video"))){
				JOptionPane.showMessageDialog(new JPanel(), "Error!");
			}
		}





	}

	//Set of three methods that exist to check if the files are what they say they are
	//This helps prevent errors
	
	// Checks if the selected video file contains audio
	public boolean checkAudio() throws IOException {
		ProcessBuilder builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString());
		builder.redirectErrorStream(true);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		while ((line = stdoutBuffered.readLine()) != null ) {
			System.out.println(line);
			if (line.contains("Audio")||line.contains("audio")) {
				return true;
			}
		}
		return false;
	}

	// Checks if the selected file is a video file
	public boolean checkVideo() throws IOException {
		ProcessBuilder builder = new ProcessBuilder("file", mainGUI.currentSelectedVideoFile.toString());
		builder.redirectErrorStream(true);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		while ((line = stdoutBuffered.readLine()) != null ) {
			System.out.println(line);
			if (line.contains("Media")||line.contains("video")) {
				return true;
			}
		}
		return false;
	}

	// Checks if selected audio file contains audio
	public boolean checkAudioOverlay() throws IOException {
		ProcessBuilder builder = new ProcessBuilder("file", currentSelectedAudioFile.toString());
		builder.redirectErrorStream(true);
		Process process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		while ((line = stdoutBuffered.readLine()) != null ) {
			System.out.println(line);
			if (line.contains("Audio")||line.contains("audio")) {
				return true;
			}
		}
		return false;
	}

	


}
