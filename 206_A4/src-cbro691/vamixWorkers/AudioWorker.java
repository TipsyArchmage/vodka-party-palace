package vamixWorkers;

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

import vamix.Audio;
import vamix.mainGUI;



/**
 * 
 * This class is the overall worker class for the replace and overlay audio tool.
 * It first extracts the desired audio as specified by the sliders and then executes another
 * worker class to carry out the desired operation. 
 */


public class AudioWorker extends SwingWorker {

	private ProcessBuilder builder;
	private String currentVideoFilePath;
	private String currentAudioFilePath;
	private String outputNameText;
	private int mode;
	private int startMin;
	private int startSec;
	private int durationMin;
	private int durationSec;
	private File deletePlease;
	private int secSilence;

	public AudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode, 
			int startMinutes, int startSeconds, int durationMinutes, int durationSeconds, int secSilence) {
		this.currentVideoFilePath = currentVideoFilePath;
		this.currentAudioFilePath = currentAudioFilePath;
		this.outputNameText = outputNameText;
		this.mode = inputMode;
		this.startMin=startMinutes;
		this.startSec=startSeconds;
		this.durationMin=durationMinutes;
		this.durationSec=durationSeconds;
		this.secSilence=secSilence;
	}

	@Override
	protected String doInBackground() throws Exception {
		// Creates a video file containing the selected video file with the selected audio file overlaid on top of its audio
		builder = new ProcessBuilder();
		
		//Extract audio required first
		if (mode==4){
						
			//Save extract file to a temporary location that will be deleted at the end
			builder.command("avconv", "-ss", 
					+0+":"+startMin+":"+startSec,"-i",currentAudioFilePath,"-t",0+":"+durationMin+":"+durationSec
					,"-codec:a aac -strict experimental","copy",outputNameText);
			
			
		}
		//Process command
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
		
		//Result message 
		if(mode==4){
			
			Audio.outputTEMPFile=new File(outputNameText);
			
			
			//Check if the user wants to replace or overlay audio and executes a worker
			//NOTE: Overlay functionality was taking a long time to implement and the option
			//      no longer exists in the final version. However I've left the the code in
			//      just in case I want to implement it in the future.
			
			//Replace
			if(Audio.replaceAudioOptionRadioBtn.isSelected()){
				//Create title of output file. Original video name combined with audio file name
				String nameNoExtension=Audio.saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				nameNoExtension=nameNoExtension+"+"+Audio.currentSelectedAudioFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				String outputName=nameNoExtension+"AUDIOREPLACE.mp4";
				//Create new OverlayReplaceAudioWorker instance and execute it
				OverlayReplaceAudioWorker workerReplace=new OverlayReplaceAudioWorker(mainGUI.currentSelectedVideoFile.toString()
						,Audio.outputTEMPFile.toString(), outputName, 5,secSilence);
				
				workerReplace.execute();
				
			}
			//Overlay
			else{
				String nameNoExtension=Audio.saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				String outputName=nameNoExtension+"AUDIOoverlay.mp4";
				
				
				OverlayReplaceAudioWorker workerOverlay=new OverlayReplaceAudioWorker(mainGUI.currentSelectedVideoFile.toString()
						, Audio.outputTEMPFile.toString(), outputName, 6,secSilence);
				
				workerOverlay.execute();
			}
			
		}

	}

}