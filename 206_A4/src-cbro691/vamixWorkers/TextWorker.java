package vamixWorkers;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import vamix.Text;
import vamix.mainGUI;

/**
 * 
 * Create a worker class that actually displays the text over the video.
 * It saves the new file to the specified file location and does not overwrite.
 *
 */


public class TextWorker extends SwingWorker {
	
	private ProcessBuilder builder;
	private String subText;
	private Color subColor;
	private Font subFont;
	private int subDuration;
	private int subSize;
	private String videoFile;
	private String outputFile;
	private Process process;
	private int exit=1;

	public TextWorker(int sizeInput, Font fontInput, Color fontColour, String inputText, String out) {
		this.subText=inputText;
		this.subColor=fontColour;
		this.subFont=fontInput;
		this.subSize=sizeInput;
		this.videoFile=mainGUI.currentSelectedVideoFile.toString();
		this.outputFile=out;
	}

	
	// Adds subtitles (with specified attributes) to the selected video file
	protected String doInBackground() throws Exception {
		
		// Creates a video file containing the selected video file with the subtitles overlaid
		//Display the text in the screen centre.
		builder = new ProcessBuilder();
		
		builder.command("avconv","-y", "-i", videoFile, "-vf", "drawtext=\"fontfile="+subFont.toString()+
				": text='"+subText + "':x=150:y=100: fontsize=" + subSize + ":fontcolor="+Text.fontColour+"\"", outputFile);
		
		System.out.print(builder.command());
		
		builder.redirectErrorStream(true);
		process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		// Read linux output from avconv command
		while ((line = stdoutBuffered.readLine()) != null ) {
			System.out.println(line);
		}
		return null;
	}

	protected void done(){
		//Display message depending whether it was successful or not.
		if(exit==0){
			JOptionPane.showMessageDialog(new JPanel(), "Subtitle overlay complete!");
		}
		else{
			JOptionPane.showMessageDialog(new JPanel(), "An error has been encountered");
		}
	}
	
}
