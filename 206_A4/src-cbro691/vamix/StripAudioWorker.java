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



public class StripAudioWorker extends SwingWorker {

	private ProcessBuilder builder;
	private String outputNameText;
	private int mode;
	

	public StripAudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode) {
		this.outputNameText = outputNameText;
		this.mode = inputMode;
		
	}

	@Override
	protected String doInBackground() throws Exception {
		// Creates a video file containing the selected video file with the selected audio file overlaid on top of its audio
		builder = new ProcessBuilder();
		if(mode==1){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameText);
		}
		else if(mode==2){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-vcodec", "copy", "-an", outputNameText);
		}
		
		builder.redirectErrorStream(true);
		Process process = builder.start();
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
		
		if(mode==1||mode==2){
		JOptionPane.showMessageDialog(new JPanel(), "Audio Removed!");
		}
//		else if(mode==2){
//			JOptionPane.showMessageDialog(new JPanel(), "Audio Removed!");
//		}
		
	}

}
