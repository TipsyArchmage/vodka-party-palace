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

import java.awt.Color;
import java.awt.Image;
import java.awt.Font;

import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class mainGUI extends JFrame {

	
	static JPanel contentPane;
	private JMenuBar menuBar;
	private JButton helpButton;
	private JLabel titleLabel;
	//static JPanel VideoPlayback;
	private JPanel menuOptionsPanel;
	protected static JPanel currentVideoPanel;
	private static JPanel initialPanel;
	//static JTextField currentVideoDisplay;
	static JButton downloadMenuButton;
	static JButton textToolsMenuButton;
	static JButton audioToolsMenuButton;
	static JPanel downloadPanel;
	static JButton downloadButton;
	static JPanel audioPanel;
	static JPanel textToolsPanel;
		
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
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		
		
		//Initialise the video playback GUI features
		currentVideoPanel = new JPanel();
		currentVideoPanel.setBounds(427, 24, 259, 45);
		contentPane.add(currentVideoPanel);
		currentVideoPanel.setLayout(null);
		
		
		final VideoPlayback videoGUI= new VideoPlayback();
		
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
//		playerPanel = new JPanel();
//		playerPanel.setBackground(SystemColor.info);
//		playerPanel.setBounds(3, 24, 420, 286);
		//setUpMediaSurface();
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
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				enableButtons("download");
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
			@Override
			public void actionPerformed(ActionEvent e) {
				
				enableButtons("textTools");

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
			@Override
			public void actionPerformed(ActionEvent e) {
				
				enableButtons("audioTools");

			}
		});
		audioToolsMenuButton.setBounds(147, 0, 104, 67);
		audioToolsMenuButton.setFocusable(false);
		audioToolsMenuButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		menuOptionsPanel.add(audioToolsMenuButton);
		
		
				
		
	}
	
		
	//Method to enable so enable and switch between menu options
	public static void enableButtons(String input) {
		 
	        
	        switch (input) {
	            case "download":  audioPanel.setVisible(false);
						 		  downloadPanel.setVisible(true);
						 		  initialPanel.setVisible(false);
						 		  textToolsPanel.setVisible(false);
						 		  break;
	            case "textTools": textToolsPanel.setVisible(true);
								  audioPanel.setVisible(false);
								  downloadPanel.setVisible(false);
								  initialPanel.setVisible(false); 
								  break;	
	            case "audioTools":audioPanel.setVisible(true);
								  downloadPanel.setVisible(false);
								  initialPanel.setVisible(false);
								  textToolsPanel.setVisible(false);
								  break;
	            case "all":       audioToolsMenuButton.setEnabled(true);
								  textToolsMenuButton.setEnabled(true);
								  downloadMenuButton.setEnabled(true);
								  break;
								  
				
	        }
		
	 
	}
	
}
