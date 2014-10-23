package vamix;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;


public class VideoPlayback extends JPanel{

	//static JPanel contentPane;
	
	static JPanel playerPanel;
	
	//protected static JPanel currentVideoPanel;
	
	static JTextField currentVideoDisplay;
	static JButton downloadMenuButton;
	static JButton textToolsMenuButton;
	static JButton audioToolsMenuButton;
	
	
	
	
		
	
	//static JPanel textToolsPanel;
	
	
	private static int playbackRate=1;
	private static int end=0;
	
	//Important Variables
	
	//Variables End
	
	static EmbeddedMediaPlayerComponent mediaPlayerComponent;
	static EmbeddedMediaPlayer video;
	protected static File checkSelectedVideoFile;
	private static JButton playButton;
	private static JButton fastForwardButton;
	private static JButton pauseButton;
	private static JButton rewindButton;
	private static JLabel timeDisplay;
	private static JSlider positionSlider;

	private static JLabel lblCurrentVideoFile;

	private static JButton changeVideoButton;
	/**
	 * @param args
	 */
	public VideoPlayback() {
		
		playerPanel = new JPanel();
		playerPanel.setBackground(SystemColor.info);
		playerPanel.setBounds(3, 24, 420, 286);
		setUpMediaSurface();
		mainGUI.contentPane.add(playerPanel);
		
		
		
		
				
			
		lblCurrentVideoFile = new JLabel("Current Video File");
		lblCurrentVideoFile.setBounds(0, 0, 259, 21);
		lblCurrentVideoFile.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblCurrentVideoFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblCurrentVideoFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		mainGUI.currentVideoPanel.add(lblCurrentVideoFile);
		//change the current video
		changeVideoButton = new JButton("Change");
		
		//Button to change selected video file
		changeVideoButton.addActionListener(new ActionListener() {
			private JFileChooser selectVideoChooser;					
		
			public void actionPerformed(ActionEvent e) {
				//Reset playback stuff to default settings
				playbackRate=1;
				pauseButton.setEnabled(false);
				playButton.setEnabled(true);
				pauseButton.setVisible(false);
				playButton.setVisible(true);						
				video.stop();
				
				//File chooser to get video
				selectVideoChooser=new JFileChooser();				
				selectVideoChooser.setDialogTitle("Select Video to Play");
				selectVideoChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);					
				int exitValue=selectVideoChooser.showOpenDialog(mainGUI.contentPane);											
				
				if (exitValue == JFileChooser.APPROVE_OPTION) {
					String videoName=selectVideoChooser.getSelectedFile().getName();
					checkSelectedVideoFile = selectVideoChooser.getSelectedFile();
					
					try {
						//Check that it is a valid video/ audio file
						int videoLength=0;
						int accept=0;
						
						if(checkVideoAudio()){
							
						while(accept==0){
							mainGUI.currentSelectedVideoFile = selectVideoChooser.getSelectedFile();
							currentVideoDisplay.setText(videoName);
							//Enable all the previously disabled buttons
							mainGUI.enableButtons("all");			
							
							
							
							video.playMedia(mainGUI.currentSelectedVideoFile.toString());
							video.parseMedia();
							videoLength=(int) video.getLength()/1000;
							video.stop();
							if(videoLength==0){
								
							}
							else{
								accept=1;
							}
						}
						
						AudioSliders.videoLocationSlider.setMaximum(videoLength);
						String videoLengthTime=Audio.getLengthTime(videoLength);
					
						AudioSliders.totalVideoLength.setText(videoLengthTime);
						
						
						}
						else{
							JOptionPane.showMessageDialog(mainGUI.contentPane,"Not a video or audio file",
									"Type Error",JOptionPane.ERROR_MESSAGE);   
								    
								    
						}
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					
					
				}
				
			}
		});
		
		changeVideoButton.setBounds(164, 21, 95, 24);
		changeVideoButton.setFocusable(false);
		mainGUI.currentVideoPanel.add(changeVideoButton);
		//Shows the current video title
		currentVideoDisplay = new JTextField();
		currentVideoDisplay.setText("No File Selected");
		currentVideoDisplay.setFocusable(false);
		currentVideoDisplay.setEditable(false);
		currentVideoDisplay.setBounds(0, 23, 165, 20);
		mainGUI.currentVideoPanel.add(currentVideoDisplay);
		currentVideoDisplay.setColumns(10);
		
		//Play
		playButton = new JButton("");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Set speed to default
				playbackRate=1;
				video.setRate(1);
				fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward.png")));
				// Checks if a file has been selected, if not then display an error message
				if (currentVideoDisplay.getText().equals("No File Selected")) {
					JOptionPane.showMessageDialog(mainGUI.contentPane, "Please select a media file to play!");
				}
				//Start the video if it's not already running
				else if (video.getTime() == -1) {
					pauseButton.setEnabled(true);
					playButton.setEnabled(false);
					pauseButton.setVisible(true);
					playButton.setVisible(false);
					video.playMedia(mainGUI.currentSelectedVideoFile.toString());
					
					int videoLength;
					video.parseMedia();
					videoLength = (int)(video.getMediaMeta().getLength()/1000);
					end=0;
					positionSlider.setMaximum(videoLength-5);
					
				}
				
				//Unpause it otherwise
				else{
					pauseButton.setEnabled(true);
					playButton.setEnabled(false);
					pauseButton.setVisible(true);
					playButton.setVisible(false);
					video.pause();
				}
						
			}
				
			
			
		});
		playButton.setBorder(null);
		playButton.setIgnoreRepaint(true);
		playButton.setFocusPainted(false);
		playButton.setFocusable(false);
		playButton.setBackground(Color.BLACK);
		playButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/play button.png")));
		playButton.setBounds(188, 349, 35, 25);
		mainGUI.contentPane.add(playButton);
		
		//Fast forward
		fastForwardButton = new JButton("");
		fastForwardButton.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				if (currentVideoDisplay.getText().equals("No File Selected")) {
					
				}
				else {
					//Up to a limit of X3 speed allow video playback to be increased
					if(playbackRate<3){
					playbackRate++;
					
					video.setRate(playbackRate);
					}
					else{
						playbackRate=1;
						video.setRate(1);
					}
					//Cycle through different icons
					if(playbackRate==1){
						fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward.png")));
					}
					else if(playbackRate==2){
						fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward2x.png")));
					}
					else if(playbackRate==3){
						fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforwardselected.png")));
					}
					
				}
			}
		});
		fastForwardButton.setSelectedIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforwardselected.png")));
		fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward.png")));
		fastForwardButton.setBorder(null);
		fastForwardButton.setBackground(Color.BLACK);
		fastForwardButton.setBounds(235, 349, 35, 25);
		mainGUI.contentPane.add(fastForwardButton);
		
		//Rewind!
		rewindButton = new JButton("");
		rewindButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentVideoDisplay.getText().equals("No File Selected")) {
					
				}
				else {
					end=0;
					video.skip(-5000);
				}
			}
		});
		
		rewindButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/rewind.png")));
		rewindButton.setIgnoreRepaint(true);
		rewindButton.setFocusable(false);
		rewindButton.setFocusPainted(false);
		rewindButton.setBorder(null);
		rewindButton.setBackground(Color.BLACK);
		rewindButton.setBounds(141, 349, 35, 25);
		mainGUI.contentPane.add(rewindButton);
		
		//Pause Video!
		pauseButton = new JButton("");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//reset video playback details
				playbackRate=1;
				video.setRate(1);
				fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward.png")));
				pauseButton.setEnabled(false);
				
				playButton.setEnabled(true);
				pauseButton.setVisible(false);
				playButton.setVisible(true);
				video.pause();
			}
		});
		pauseButton.setEnabled(false);
		pauseButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/pause.png")));
		pauseButton.setIgnoreRepaint(true);
		pauseButton.setFocusable(false);
		pauseButton.setFocusPainted(false);
		pauseButton.setBorder(null);
		pauseButton.setBackground(Color.BLACK);
		pauseButton.setBounds(188, 349, 35, 25);
		mainGUI.contentPane.add(pauseButton);
		
		
		//Add slider for the volume control
		JSlider slider = new JSlider();
		slider.setBackground(Color.BLACK);
		slider.setValue(100);
		slider.addChangeListener(new SliderListener());
		slider.setBounds(326, 354, 87, 16);
		mainGUI.contentPane.add(slider);
		//Add volume control icon
		JPanel volumeIcon = new JPanel();
		volumeIcon.setBackground(Color.BLACK);
		volumeIcon.setBounds(290, 349, 35, 25);
		mainGUI.contentPane.add(volumeIcon);
		volumeIcon.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/volume.png")));
		lblNewLabel_2.setBounds(0, 0, 70, 25);
		volumeIcon.add(lblNewLabel_2);
		
		//Stop video!
		JButton stopButton = new JButton("");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playbackRate=1;
				video.setRate(1);
				pauseButton.setEnabled(false);						
				playButton.setEnabled(true);
				pauseButton.setVisible(false);
				playButton.setVisible(true);
				video.stop();
			}
		});
		stopButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/stop.png")));
		stopButton.setIgnoreRepaint(true);
		stopButton.setFocusable(false);
		stopButton.setFocusPainted(false);
		stopButton.setBorder(null);
		stopButton.setBackground(Color.BLACK);
		stopButton.setBounds(45, 349, 35, 25);
		mainGUI.contentPane.add(stopButton);
		
		
		
		Timer timer = new Timer(100, new ActionListener() {
			
	
		@Override
		// Timer gets media's elapsed time in ms every 100ms, then converts this value to hours,
		// minutes, and seconds, and updates the media's elapsed time label and displays it in HH:MM:SS format
		public void actionPerformed(ActionEvent e) {
			
			//Make sure the right button is in place
			if(video.isPlaying()){
				pauseButton.setEnabled(true);						
				playButton.setEnabled(false);
				pauseButton.setVisible(true);
				playButton.setVisible(false);
			}								
			
			
			long time = video.getTime();
			int hours = (int) time / 3600000;
			int remainder = (int) time - hours * 3600000;
			int mins = remainder / 60000;
			remainder = remainder - mins * 60000;
			int secs = remainder / 1000;
			
			//Set seek bar slider
			positionSlider.setValue((int) video.getTime()/1000);
			
			// Convert from int to String
			String h = String.valueOf(hours);
			String m = String.valueOf(mins);
			String s = String.valueOf(secs);
			// Adds a leading 0 if the hours/minutes/seconds value only contains one digit
			if (h.length() == 1) {
				h = "0" + h;
			}
			if (m.length() == 1) {
				m = "0" + m;
			}
			if (s.length() == 1) {
				s = "0" + s;
			}
			timeDisplay.setText(h + ":" + m + ":" + s);
			
			
			//Make sure the video doesn't stop and disable seeking
					if((video.getTime()>=(video.getLength()-1000))&&end==0){
						video.setTime(0);
						pauseButton.setEnabled(false);
						playButton.setEnabled(true);
						pauseButton.setVisible(false);
						playButton.setVisible(true);
						end=1;
						video.pause();
					}
				}
			});		
		timer.start();

				
				

				

	}
	
	
	/**
	 * Set up media canvas that the video will actually play on
	 */
	
	public static void setUpMediaSurface() {
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		mediaPlayerComponent.setBounds(0, 0, 420, 304);
		mediaPlayerComponent.getVideoSurface().setBounds(0, 0, 420, 305);
		mediaPlayerComponent.getVideoSurface().setBackground(Color.LIGHT_GRAY);
		Canvas canvas = new Canvas();
		canvas.setBounds(0, 0, 0, 0);
		canvas.setVisible(true);
		canvas.setBackground(SystemColor.info);
		// Creates a media player to play media
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		mediaPlayer.setVideoSurface(videoSurface);
				playerPanel.setLayout(null);
		// Adds the canvas to the center of the video panel
		playerPanel.add(canvas);
		video = mediaPlayerComponent.getMediaPlayer();
		
		
		//Get Timer on the video
		// Displays media's elapsed time
				timeDisplay = new JLabel("00:00:00");
				timeDisplay.setForeground(Color.WHITE);
				timeDisplay.setFont(new Font("Dialog", Font.BOLD, 12));
				timeDisplay.setBounds(160, 322, 82, 21);
				mainGUI.contentPane.add(timeDisplay);
				timeDisplay.setHorizontalAlignment(SwingConstants.CENTER);
				timeDisplay.setBackground(Color.BLACK);
		
		//Slider to change video position
		positionSlider = new JSlider();
		positionSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
					//video.pause();
					JSlider source = (JSlider)e.getSource();
				
					int place = (int)source.getValue();
					end=0;
					video.setTime(place*1000);
					
			}
		});
		
		
		positionSlider.setValue(0);
		positionSlider.setBounds(3, 311, 420, 16);
		//positionSlider.addChangeListener(new SliderVideoListener());
		mainGUI.contentPane.add(positionSlider);
		positionSlider.setBackground(Color.BLACK);
		playerPanel.add(mediaPlayerComponent);
		mediaPlayerComponent.setLayout(null);
		mainGUI.contentPane.add(playerPanel);
	}
	
	class SliderListener implements ChangeListener {
		// Change event which sets the volume of the media relative to the position of the volume slider
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			
				int volume = (int)source.getValue();
				video.setVolume(volume);
			
		}
	}
	
	// Checks if the selected file is either a video or audio file
		public static Boolean checkVideoAudio() throws IOException {
			ProcessBuilder builder = new ProcessBuilder("file", checkSelectedVideoFile.toString());
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			while ((line = stdoutBuffered.readLine()) != null ) {
				if (line.contains("Media") || line.contains("audio")||line.contains("Audio")) {
					return true;
				}
			}
			return false;
		}
	
	
	
}
