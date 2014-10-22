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

public class Audio extends JPanel{

	
	
	static JPanel downloadPanel;	
	static JButton downloadButton;
	
	private JLabel lblAudioTools;
	private JButton removeAudioButton;
	private JLabel lblSelectAudioTo;
	private JTextField currentAudioTrack;
	private JButton changeAudioTrackButton;
	static JSlider extractStartSlider;
	private JLabel lblSelectTheStart;
	private JLabel initialTimeLabel;
	static JLabel endTimeAudioLabel;
	private JLabel totalLength;
	private JLabel initialLength;
	static JSlider extractLengthAudio;
	private JLabel lblSelectTheLength;
	private JLabel videoLocationTitle;
	private JLabel initalVideoLength;
	static JLabel totalVideoLength;
	static JSlider videoLocationSlider;
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
					
					
				
					Object[] options = {"Remove Audio", "Remove Audio and Save it to a seperate File", "Cancel"};
					int n = JOptionPane.showOptionDialog(mainGUI.contentPane, "Do you want to remove the audio and discard it or save it to " +
							"a new audio file? The original file will not be altered", 
							"Please select an option",  JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[2]);
					
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
		//Button to change selected audio
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
					
					currentSelectedAudioFile = selectAudioChooser.getSelectedFile();
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
							mainGUI.playerPanel.setLayout(null);
					// Adds the canvas to the center of the video panel
							mainGUI.playerPanel.add(canvas);
					audio = mainGUI.mediaPlayerComponent.getMediaPlayer();
					mainGUI.playerPanel.add(mainGUI.mediaPlayerComponent);
					mainGUI.mediaPlayerComponent.setLayout(null);
					mainGUI.contentPane.add(mainGUI.playerPanel);
					
					
					
					audio.playMedia(currentSelectedAudioFile.toString());
					audio.parseMedia();
					int audioLength=(int) audio.getLength()/1000;
					audio.stop();
					
//					//Change play button actions for the preview function
//					mainGUI.video=audio;
//					mainGUI.setUpMediaSurface();
					extractStartSlider.setValue(0);
					extractLengthAudio.setValue(0);
					extractStartSlider.setMaximum(audioLength);
					String audioLengthTime=getLengthTime(audioLength);
					//System.out.print(audioLengthTime);
					endTimeAudioLabel.setText(audioLengthTime);
					//totalLength.setText(audioLengthTime);
					
				}
			}
		});
		changeAudioTrackButton.setFocusable(false);
		changeAudioTrackButton.setBounds(164, 90, 95, 24);
		mainGUI.audioPanel.add(changeAudioTrackButton);
		//Label showing where selection was BAR 1
		initialTimeLabel = new JLabel("00:00");
		initialTimeLabel.setBounds(94, 165, 46, 14);
		mainGUI.audioPanel.add(initialTimeLabel);
		//Length of selected audio track BAR 1
		endTimeAudioLabel = new JLabel("00:00");
		endTimeAudioLabel.setBounds(220, 153, 46, 14);
		mainGUI.audioPanel.add(endTimeAudioLabel);
		
		//Slider to select start of audio track
		extractStartSlider = new JSlider();
		extractStartSlider.setBorder(null);
		extractStartSlider.setOpaque(false);
		extractStartSlider.setValue(0);
		extractStartSlider.addChangeListener(new SliderListener1());
		extractStartSlider.setSnapToTicks(true);
		extractStartSlider.setPaintTicks(true);
		extractStartSlider.setFocusable(false);
		extractStartSlider.setBounds(10, 153, 214, 26);
		
		mainGUI.audioPanel.add(extractStartSlider);
		
		lblSelectTheStart = new JLabel("<html>From which point in the Audio Track to you want to take Audio?</html>");
		lblSelectTheStart.setFont(new Font("Dialog", Font.BOLD, 11));
		lblSelectTheStart.setBounds(10, 123, 224, 27);
		mainGUI.audioPanel.add(lblSelectTheStart);
		//Length of selected audio track-Selection already made BAR 2
		totalLength = new JLabel("00:00");
		totalLength.setBounds(220, 220, 46, 14);
		mainGUI.audioPanel.add(totalLength);
		//Where current selection is BAR 2
		initialLength = new JLabel("00:00");
		initialLength.setBounds(94, 232, 46, 14);
		mainGUI.audioPanel.add(initialLength);
		//SLider to select amount of audio track to use
		extractLengthAudio = new JSlider();
		extractLengthAudio.setBorder(null);
		extractLengthAudio.setValue(0);
		extractLengthAudio.addChangeListener(new SliderListener2());
		extractLengthAudio.setSnapToTicks(true);
		extractLengthAudio.setPaintTicks(true);
		extractLengthAudio.setOpaque(false);
		extractLengthAudio.setFocusable(false);
		extractLengthAudio.setBounds(10, 220, 214, 26);
		mainGUI.audioPanel.add(extractLengthAudio);
		
		lblSelectTheLength = new JLabel("<html>How Much of the Audio Track Do You Want To Use?</html>");
		lblSelectTheLength.setBounds(10, 190, 224, 27);
		mainGUI.audioPanel.add(lblSelectTheLength);
		
		videoLocationTitle = new JLabel("<html>When Should the Audio Track start playing in the Video?</html>");
		videoLocationTitle.setBounds(10, 255, 224, 27);
		mainGUI.audioPanel.add(videoLocationTitle);
		//Current selection BAR 3
		initalVideoLength = new JLabel("00:00");
		initalVideoLength.setBounds(94, 297, 46, 14);
		mainGUI.audioPanel.add(initalVideoLength);
		//Total video length of file selected
		totalVideoLength = new JLabel("00:00");
		totalVideoLength.setBounds(220, 285, 46, 14);
		mainGUI.audioPanel.add(totalVideoLength);
		
		//Slider to choose placement in video
		videoLocationSlider = new JSlider();
		videoLocationSlider.setValue(0);
		videoLocationSlider.setSnapToTicks(true);
		videoLocationSlider.setPaintTicks(true);
		videoLocationSlider.addChangeListener(new SliderListener3());
		videoLocationSlider.setOpaque(false);
		videoLocationSlider.setFocusable(false);
		videoLocationSlider.setBorder(null);
		videoLocationSlider.setBounds(10, 285, 214, 26);
		mainGUI.audioPanel.add(videoLocationSlider);
		
		//Option to overlay audio
		JRadioButton overlayOptionRadioBtn = new JRadioButton("Overlay Existing Audio");
		overlayOptionRadioBtn.setFont(new Font("Dialog", Font.BOLD, 11));
		overlayOptionRadioBtn.setOpaque(false);
		overlayOptionRadioBtn.setSelected(true);
		overlayOptionRadioBtn.setFocusable(false);
		overlayOptionRadioBtn.setBounds(0, 320, 182, 23);
		
		mainGUI.audioPanel.add(overlayOptionRadioBtn);

		//Option to replace audio
		 replaceAudioOptionRadioBtn = new JRadioButton("Replace Existing Audio");
		replaceAudioOptionRadioBtn.setFont(new Font("Dialog", Font.BOLD, 11));
		replaceAudioOptionRadioBtn.setOpaque(false);
		replaceAudioOptionRadioBtn.setFocusable(false);
		replaceAudioOptionRadioBtn.setBounds(0, 342, 195, 23);
		mainGUI.audioPanel.add(replaceAudioOptionRadioBtn);
		
		buttonOptionGroup.add(replaceAudioOptionRadioBtn);
		buttonOptionGroup.add(overlayOptionRadioBtn);
		
		btnNewButton = new JButton("Go!");
		btnNewButton.addActionListener(new ActionListener() {
			private JFileChooser saveNewAudio;
			
			
			
			public void actionPerformed(ActionEvent e) {
				
				//Disable sliders
				videoLocationSlider.setEnabled(false);
				extractLengthAudio.setEnabled(false);
				extractStartSlider.setEnabled(false);
				
				//Create Directory chooser for saving audio file
				saveNewAudio=new JFileChooser();				
				saveNewAudio.setDialogTitle("Select Directory to save to");
				saveNewAudio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
				saveNewAudio.setAcceptAllFileFilterUsed(false);
				int exitValue=saveNewAudio.showOpenDialog(mainGUI.contentPane);
				
				if (exitValue == JFileChooser.APPROVE_OPTION) {
					saveAudioNewFile = saveNewAudio.getSelectedFile();
					
					int rand=(int)(Math.random()*1000); 
					//Temporary File Location
					String outputTEMPTextAudio=saveAudioNewFile+"/TEMPORARY_FILE"+rand+".mp4";
					
					int totalTime=extractStartSlider.getValue();
					//Trim to specified length
					int startMinutes=totalTime/60;
					int startSeconds=totalTime-startMinutes*60;
					
					int trimTime=extractLengthAudio.getValue();
					int durMinutes=trimTime/60;
					int durSeconds=trimTime-durMinutes;
					
					AudioWorker worker=new AudioWorker(null, currentSelectedAudioFile.toString()
							, outputTEMPTextAudio, 4,startMinutes, startSeconds,durMinutes,durSeconds);
					//builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameTextAudio);
					worker.execute();
					
					//builder.command("avconv", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
					//Check whether to overlay or replace audio
					//Replace
		//			if(replaceAudioOptionRadioBtn.isSelected()){
//						String nameNoExtension=saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
//						nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
//						String outputName=nameNoExtension+"AUDIOREPLACE.mp4";
//						AudioWorker workerReplace=new AudioWorker(mainGUI.currentSelectedVideoFile.toString()
//								,outputTEMPFile.toString(), outputName, 5,0,0,0,0);
//						//builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameTextAudio);
//						workerReplace.execute();
//					}
//					//Overlay
//					else{
//						AudioWorker workerOverlay=new AudioWorker(mainGUI.currentSelectedVideoFile.toString()
//								, outputTEMPFile.toString(), outputTEMPTextAudio, 6,0,0,0,0);
//						//builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameTextAudio);
//						workerOverlay.execute();
//					}
				}
				else{
						videoLocationSlider.setEnabled(true);
						extractLengthAudio.setEnabled(true);
						extractStartSlider.setEnabled(true);
				}
				
			}
		});
		btnNewButton.setFocusable(false);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(180, 322, 79, 45);
		mainGUI.audioPanel.add(btnNewButton);

	}
	
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

	public void stripAudio(String outputType) {
		// Remove file extension from output file if one exists, and change to .mp4
		
		
		//Variable is saveAudioNewFile
		
//		String outputNameText=outputPath+"/"+mainGUI.currentSelectedVideoFile.toString();
//		String[] words = outputNameText.split("\\.");
//		outputPathNoExtension = words[0];
		String nameNoExtension=saveRemoveFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
		nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
		
	    outputNameTextVideo = nameNoExtension+" Output No Audio.mp4";
		
		outputNameTextAudio = nameNoExtension+" Output Only Audio.mp4";
		
		//Enter inputs for the worker String currentVideoFilePath, String currentAudioFilePath, String outputNameText, mode select
		//1-> Silent Video and audio file
		//2-> Silent Video
		//3-> Overlay audio to video

		try {
			if (outputType.equals("video.audio")) {
				// Creates an audio file containing the audio stripped from the selected video file
				AudioWorker worker=new AudioWorker(mainGUI.currentSelectedVideoFile.toString(), null, outputNameTextAudio, 1,0,0,0,0);
				builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameTextAudio);
				worker.execute();
				
				
			}
			if (outputType.equals("video")||outputType.equals("video.audio")) {
				// Creates a video file containing the video selected with its audio stripped
				AudioWorker worker=new AudioWorker(mainGUI.currentSelectedVideoFile.toString(), null, outputNameTextVideo, 2,0,0,0,0);
				builder = new ProcessBuilder("avconv", "-i", mainGUI.currentSelectedVideoFile.toString(), "-vcodec", "copy", "-an", outputNameTextVideo);
				worker.execute();
				
			}
			else {
				try{
					// Creates a swing worker to overlay audio on a background thread, as this is a time-consuming task
					//AudioWorker worker=new AudioWorker(mainGUI.currentSelectedVideoFile.toString(), currentSelectedAudioFile.toString(), outputNameText);
					// Creates a video file containing the selected video file with the selected audio file overlaid on top of its audio
					//builder = new ProcessBuilder("avconv", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
					//worker.execute();
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
			if (line.contains("Audio")) {
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
			if (line.contains("Media")) {
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
			if (line.contains("Audio")) {
				return true;
			}
		}
		return false;
	}

	
	class SliderListener1 implements ChangeListener {
		// Change event which sets the volume of the media relative to the position of the volume slider
		public void stateChanged(ChangeEvent e) {
			int startSliderGap=extractStartSlider.getMaximum()-extractStartSlider.getValue();
			int videoSliderGap=videoLocationSlider.getMaximum()-videoLocationSlider.getValue();
			if(startSliderGap>=videoSliderGap){
				extractLengthAudio.setMaximum(videoLocationSlider.getMaximum()-videoLocationSlider.getValue());
				
				String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			else{
				extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-extractStartSlider.getValue());
				
				String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			
			
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
			int currentLength = (int)source.getValue();
			String time=getLengthTime(currentLength);
			initialTimeLabel.setText(time);
//			//extractLengthAudio.setValue(0);
//			extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-currentLength);
//			
//			String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
//			//System.out.print(audioLengthTime);
//			
//			totalLength.setText(audioLengthTime);
			
			//}
		}
	}
	
	class SliderListener2 implements ChangeListener {
		// Change event which sets the volume of the media relative to the position of the volume slider
		public void stateChanged(ChangeEvent e) {
			
			
			
			
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
				int currentLength = (int)source.getValue();
				String time=getLengthTime(currentLength);
				initialLength.setText(time);
				
				
			//}
		}
		
	}
	
	class SliderListener3 implements ChangeListener {
		// Change event which sets the volume of the media relative to the position of the volume slider
		public void stateChanged(ChangeEvent e) {
			int startSliderGap=extractStartSlider.getMaximum()-extractStartSlider.getValue();
			int videoSliderGap=videoLocationSlider.getMaximum()-videoLocationSlider.getValue();
			if(startSliderGap>=videoSliderGap){
				extractLengthAudio.setMaximum(videoLocationSlider.getMaximum()-videoLocationSlider.getValue());
				
				String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			else{
				extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-extractStartSlider.getValue());
				
				String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
			int currentLength = (int)source.getValue();
			String time=getLengthTime(currentLength);
			initalVideoLength.setText(time);
				
			//}
		}
	}

}