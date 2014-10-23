package vamix;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 * 
 * This worker gives the functionality for the audio removal option.
 * It has two modes, one of which outputs the audio to a seperate file as well as the silent video while the other
 * just gives a silent video. Both options set the video to the currently playing panel.
 *
 */

public class StripAudioWorker extends SwingWorker {

	private ProcessBuilder builder;
	private String outputNameText;
	private int mode;
	private Process process;
	private int exit;
	

	public StripAudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode) {
		this.outputNameText = outputNameText;
		this.mode = inputMode;
		
	}

	
	@Override
	protected String doInBackground() throws Exception {
		// Creates a video file containing the selected video file with the selected audio file overlaid on top of its audio
		builder = new ProcessBuilder();
		//This mode outputs both video and audio
		if(mode==1){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameText);
		}
		//While this just outputs a silent audio file
		else if(mode==2){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-vcodec", "copy", "-an", outputNameText);
		}
		
		//General output
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
		
		process.destroy();
		   
		   exit=process.exitValue();		   
		   
		   if(exit==0){
			   //If successful display message and set the video to the currently playing panel
			   JOptionPane.showMessageDialog(new JPanel(), "Audio Removed!");
			   mainGUI.currentSelectedVideoFile=new File(outputNameText);
			   VideoPlayback.currentVideoDisplay.setText(mainGUI.currentSelectedVideoFile.getName());
			}
		   
		   
		   //Error message otherwise
			else {
			JOptionPane.showMessageDialog(new JPanel(), "Process Failed");
			}

		
	}

}
