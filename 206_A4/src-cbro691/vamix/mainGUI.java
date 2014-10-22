package vamix;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
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
import java.awt.Image;
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
import javax.swing.Timer;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JTextArea;
import javax.swing.JFileChooser;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class mainGUI extends JFrame {

	
	static JPanel contentPane;
	private JMenuBar menuBar;
	private JButton helpButton;
	private JLabel titleLabel;
	static JPanel playerPanel;
	private JPanel menuOptionsPanel;
	private JPanel currentVideoPanel;
	private JPanel initialPanel;
	private JLabel lblCurrentVideoFile;
	private JButton changeVideoButton;
	static JTextField currentVideoDisplay;
	static JButton downloadMenuButton;
	static JButton textToolsMenuButton;
	static JButton audioToolsMenuButton;
	static JPanel downloadPanel;
	private JLabel lblDownload;
	private JLabel lblEnterUrlOf;
	
	private JLabel openSourceCheckLabel;
	private JCheckBox chckbxThisIsOpen;
	private JLabel lblWhereDoYou;
	private JTextField txtNoLocationSelected;
	private JButton changeSaveDownloadButton;
	static JButton downloadButton;
	private JProgressBar progressBar;
	static JPanel audioPanel;
	private JLabel lblAudioTools;
	private JButton removeAudioButton;
	private JLabel lblSelectAudioTo;
	private JTextField currentAudioTrack;
	private JButton changeAudioTrackButton;
	private JSlider extractStartSlider;
	private JLabel lblSelectTheStart;
	private JLabel initialTimeLabel;
	private JLabel endTimeAudioLabel;
	private JLabel totalLength;
	private JLabel initialLength;
	private JSlider extractLengthAudio;
	private JLabel lblSelectTheLength;
	private JLabel videoLocationTitle;
	private JLabel initalVideoLength;
	private JLabel totalVideoLength;
	private JSlider videoLocationSlider;
	private JButton btnNewButton;
	static JPanel textToolsPanel;
	private JLabel textToolsTitle;
	private JLabel lblEnterTextMessage;
	private JLabel textStartTimeLabel;
	private JLabel lblHowLongShould;
	private JSlider textDisplayTimeSlider;
	private JLabel textDisplayTimeLabel;
	private JButton removeTextButton;
	private JButton saveTextButton;
	private ButtonGroup buttonOptionGroup=new ButtonGroup();
	private int playbackRate=1;
	private static int end=0;
	
	//Important Variables
	private File saveDownloadFile;
	static File currentSelectedVideoFile;
	private File saveTextFile;
	private File currentSelectedAudioFile;
	private File saveAudioNewFile;
	private JTextField enterURLTextField;
	//Variables End
	protected DownloadWorker downloadWorker;
	static EmbeddedMediaPlayerComponent mediaPlayerComponent;
	static EmbeddedMediaPlayer video;
	protected static File checkSelectedVideoFile;
	private JButton playButton;
	private JButton fastForwardButton;
	private JButton pauseButton;
	private JButton rewindButton;
	private static JLabel timeDisplay;
	private static JSlider positionSlider;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGUI frame = new mainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 698, 487);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//Set the vamix icon
		Image i;
		try {
			i = ImageIO.read(getClass().getResource("/visualResources/vamix taskbar.png"));
			setIconImage(i);
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		
		/**
		 * Broad GUI Elements
		 */
		//Menu bar with help button and title
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 698, 21);
		contentPane.add(menuBar);
		//Help Button
		helpButton = new JButton("Help");
		helpButton.setFocusable(false);
		menuBar.add(helpButton);
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		menuBar.add(horizontalStrut);
		//Title
		titleLabel = new JLabel("VAMIX");
		menuBar.add(titleLabel);
		//Panel for the video player
		playerPanel = new JPanel();
		playerPanel.setBackground(SystemColor.info);
		playerPanel.setBounds(3, 24, 420, 286);
		setUpMediaSurface();
		//contentPane.add(playerPanel);
		
		/**
		 * INITIAL PANEL
		 */	
		
		//Initial panel to show into text and logo
		initialPanel = new JPanel();
		initialPanel.setBounds(427, 74, 259, 372);
		contentPane.add(initialPanel);
		initialPanel.setLayout(null);
		
		JLabel lblVamix = new JLabel("<html><center>VAMIX<br>by Cameron Brown</html>");
		lblVamix.setFont(new Font("Dialog", Font.BOLD, 20));
		lblVamix.setBounds(38, 204, 178, 72);
		initialPanel.add(lblVamix);
		
		JPanel logoPanel = new JPanel();
		logoPanel.setBounds(22, 12, 225, 192);
		
				
		logoPanel.setLayout(new BorderLayout(0, 0));
		initialPanel.add(logoPanel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/vamix logo.png")));
		logoPanel.add(lblNewLabel, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("<html><center>To get started select an existing video file or download a new one!</html>");
		lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(38, 288, 178, 72);
		initialPanel.add(lblNewLabel_1);
		
		/**
		 * DOWNLOAD PANEL
		 */
		downloadPanel = new JPanel();
		downloadPanel.setBounds(427, 74, 259, 372);
		contentPane.add(downloadPanel);
		downloadPanel.setLayout(null);
		downloadPanel.setVisible(false);
		
		final Download download = new Download();

		/**
		 * AUDIO PANEL
		 */
		audioPanel = new JPanel();
		audioPanel.setBounds(427, 74, 259, 372);
		contentPane.add(audioPanel);
		audioPanel.setLayout(null);
		audioPanel.setVisible(false);
		
		final Audio audio = new Audio();
		
		/**
		 * TEXT TOOLS PANEL
		 */
		//Panel for text tools
		textToolsPanel = new JPanel();
		textToolsPanel.setBounds(427, 74, 259, 372);
		contentPane.add(textToolsPanel);
		textToolsPanel.setLayout(null);
		textToolsPanel.setVisible(false);
		
		Text text = new Text();
		
		/**
		 * BUTTON NAVIGATION
		 */
		menuOptionsPanel = new JPanel();
		menuOptionsPanel.setBounds(3, 379, 420, 67);
		contentPane.add(menuOptionsPanel);
		menuOptionsPanel.setLayout(null);
		//Button for downloading section
		downloadMenuButton = new JButton("<html><center>Download<br>New File</html>");
		
		downloadMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				audioPanel.setVisible(false);
				downloadPanel.setVisible(true);
				initialPanel.setVisible(false);
				textToolsPanel.setVisible(false);
			}
		});
		downloadMenuButton.setBounds(0, 0, 147, 67);
		downloadMenuButton.setFocusable(false);
		downloadMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		menuOptionsPanel.add(downloadMenuButton);
		
		//Button for Text tools
		textToolsMenuButton = new JButton("<html><center>Add Text<br>to the Video</html>");
		textToolsMenuButton.setEnabled(false);
		textToolsMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textToolsPanel.setVisible(true);
				audioPanel.setVisible(false);
				downloadPanel.setVisible(false);
				initialPanel.setVisible(false);
			}
		});
		textToolsMenuButton.setBounds(251, 0, 169, 67);
		textToolsMenuButton.setFocusable(false);
		textToolsMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		menuOptionsPanel.add(textToolsMenuButton);
		//Button for Audio tools
		audioToolsMenuButton = new JButton("<html><center>Audio<br>Tools</html>");
		audioToolsMenuButton.setEnabled(false);
		audioToolsMenuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				audioPanel.setVisible(true);
				downloadPanel.setVisible(false);
				initialPanel.setVisible(false);
				textToolsPanel.setVisible(false);
			}
		});
		audioToolsMenuButton.setBounds(147, 0, 104, 67);
		audioToolsMenuButton.setFocusable(false);
		audioToolsMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		menuOptionsPanel.add(audioToolsMenuButton);
		
		
		/**
		 * Section that deals with the current selected video file
		 * and changing it
		 */		
		//Panel that shows the current selected video
		currentVideoPanel = new JPanel();
		currentVideoPanel.setBounds(427, 24, 259, 45);
		contentPane.add(currentVideoPanel);
		currentVideoPanel.setLayout(null);
		//Label for section
		lblCurrentVideoFile = new JLabel("Current Video File");
		lblCurrentVideoFile.setBounds(0, 0, 259, 21);
		lblCurrentVideoFile.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblCurrentVideoFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblCurrentVideoFile.setFont(new Font("Tahoma", Font.BOLD, 14));
		currentVideoPanel.add(lblCurrentVideoFile);
		//change the current video
		changeVideoButton = new JButton("Change");
		changeVideoButton.addActionListener(new ActionListener() {
			private JFileChooser selectVideoChooser;
			

			public void actionPerformed(ActionEvent e) {
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
				int exitValue=selectVideoChooser.showOpenDialog(contentPane);
									
				
				if (exitValue == JFileChooser.APPROVE_OPTION) {
					String videoName=selectVideoChooser.getSelectedFile().getName();
					checkSelectedVideoFile = selectVideoChooser.getSelectedFile();
					
					try {
						if(checkVideoAudio()){
						currentSelectedVideoFile = selectVideoChooser.getSelectedFile();
						currentVideoDisplay.setText(videoName);
						audioToolsMenuButton.setEnabled(true);
						textToolsMenuButton.setEnabled(true);
						downloadMenuButton.setEnabled(true);
						
						
						
						System.out.print(currentSelectedVideoFile.toString());
						video.playMedia(currentSelectedVideoFile.toString());
						video.parseMedia();
						int videoLength=(int) video.getLength()/1000;
						video.stop();
						Audio.videoLocationSlider.setMaximum(videoLength);
						String videoLengthTime=Audio.getLengthTime(videoLength);
					
						Audio.totalVideoLength.setText(videoLengthTime);
						
						
						}
						else{
							JOptionPane.showMessageDialog(contentPane,"Not a video or audio file",
									"Type Error",JOptionPane.ERROR_MESSAGE);   
								    
								    
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				
			}
		});
		
		changeVideoButton.setBounds(164, 21, 95, 24);
		changeVideoButton.setFocusable(false);
		currentVideoPanel.add(changeVideoButton);
		//Shows the current video title
		currentVideoDisplay = new JTextField();
		currentVideoDisplay.setText("No File Selected");
		currentVideoDisplay.setFocusable(false);
		currentVideoDisplay.setEditable(false);
		currentVideoDisplay.setBounds(0, 23, 165, 20);
		currentVideoPanel.add(currentVideoDisplay);
		currentVideoDisplay.setColumns(10);
		
		//Play
		playButton = new JButton("");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playbackRate=1;
				video.setRate(1);
				fastForwardButton.setIcon(new ImageIcon(mainGUI.class.getResource("/visualResources/fastforward.png")));
				// Checks if a file has been selected, if not then display an error message
				if (currentVideoDisplay.getText().equals("No File Selected")) {
					JOptionPane.showMessageDialog(contentPane, "Please select a media file to play!");
				}
				else if (video.getTime() == -1) {
					pauseButton.setEnabled(true);
					playButton.setEnabled(false);
					pauseButton.setVisible(true);
					playButton.setVisible(false);
					video.playMedia(currentSelectedVideoFile.toString());
					
					int videoLength;
					video.parseMedia();
					videoLength = (int)(video.getMediaMeta().getLength()/1000);
					end=0;
					positionSlider.setMaximum(videoLength-5);
					
				}
				
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
		contentPane.add(playButton);
		
		//Fast forward
		fastForwardButton = new JButton("");
		fastForwardButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				if (currentVideoDisplay.getText().equals("No File Selected")) {
					
				}
				else {
					if(playbackRate<3){
					playbackRate++;
					
					video.setRate(playbackRate);
					}
					else{
						playbackRate=1;
						video.setRate(1);
					}
					
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
		contentPane.add(fastForwardButton);
		
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
		contentPane.add(rewindButton);
		
		//Pause Video!
		pauseButton = new JButton("");
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		contentPane.add(pauseButton);
		
		JSlider slider = new JSlider();
		slider.setBackground(Color.BLACK);
		slider.setValue(100);
		slider.addChangeListener(new SliderListener());
		slider.setBounds(326, 354, 87, 16);
		contentPane.add(slider);
		
		JPanel volumeIcon = new JPanel();
		volumeIcon.setBackground(Color.BLACK);
		volumeIcon.setBounds(290, 349, 35, 25);
		contentPane.add(volumeIcon);
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
		contentPane.add(stopButton);

				Timer timer = new Timer(100, new ActionListener() {
					

					@Override
					// Timer gets media's elapsed time in ms every 100ms, then converts this value to hours,
					// minutes, and seconds, and updates the media's elapsed time label and displays it in HH:MM:SS format
					public void actionPerformed(ActionEvent e) {
						long time = video.getTime();
						int hours = (int) time / 3600000;
						int remainder = (int) time - hours * 3600000;
						int mins = remainder / 60000;
						remainder = remainder - mins * 60000;
						int secs = remainder / 1000;
						
						//Set slider
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
						
						if((video.getTime()>=(video.getLength()-1000))&&end==0){
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
	
	// Sets up a canvas video surface, ready for media to be played on
	
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
				contentPane.add(timeDisplay);
				timeDisplay.setHorizontalAlignment(SwingConstants.CENTER);
				timeDisplay.setBackground(Color.BLACK);
		
		//Slider to change video position
		positionSlider = new JSlider();
		positionSlider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
					//video.pause();
					JSlider source = (JSlider)e.getSource();
				//if (!source.getValueIsAdjusting()) {
					int place = (int)source.getValue();
					end=0;
					video.setTime(place*1000);
					//video.pause();
				//}
			}
		});
		
		
		positionSlider.setValue(0);
		positionSlider.setBounds(3, 311, 420, 16);
		//positionSlider.addChangeListener(new SliderVideoListener());
		contentPane.add(positionSlider);
		positionSlider.setBackground(Color.BLACK);
		playerPanel.add(mediaPlayerComponent);
		mediaPlayerComponent.setLayout(null);
		contentPane.add(playerPanel);
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
			if (line.contains("Media") || line.contains("Audio")) {
				return true;
			}
		}
		return false;
	}
	
	class SliderListener implements ChangeListener {
		// Change event which sets the volume of the media relative to the position of the volume slider
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
				int volume = (int)source.getValue();
				video.setVolume(volume);
			//}
		}
	}
}
