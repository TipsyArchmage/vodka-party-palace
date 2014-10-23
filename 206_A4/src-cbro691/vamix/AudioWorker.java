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
			//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
			
			//Save extract file to a temporary location
			builder.command("avconv", "-ss", 
					+0+":"+startMin+":"+startSec,"-i",currentAudioFilePath,"-t",0+":"+durationMin+":"+durationSec
					,"-codec:a aac -strict experimental","copy",outputNameText);
			
			
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
		
		//Result message 
		if(mode==4){
			
			//avconv -i 1.mp4 -i 2.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=3 -strict experimental TEST.mp4
			//avconv -i sintel_trailer-480p.mp4 -i TEST.mp4 -map 0 -map 1:0 -c:v copy -c:a copy -ac 1 TES1T.mp4

			
			//JOptionPane.showMessageDialog(new JPanel(), "The File will automatically set as the current video");
			Audio.outputTEMPFile=new File(outputNameText);
			
			
			//Check if the user wants to replace or overlay audio
			
			//Replace
			if(Audio.replaceAudioOptionRadioBtn.isSelected()){
				String nameNoExtension=Audio.saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				String outputName=nameNoExtension+"AUDIOREPLACE.mp4";
				
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
//		else if(mode==5||mode==6){
//			//deletePlease.delete();
//			Audio.outputTEMPFile.delete();
//			AudioSliders.videoLocationSlider.setEnabled(true);
//			AudioSliders.extractLengthAudio.setEnabled(true);
//			AudioSliders.extractStartSlider.setEnabled(true);
//			JOptionPane.showMessageDialog(new JPanel(), "Audio Overlay complete!");
//			//Auto set current video
//			mainGUI.currentSelectedVideoFile=new File(outputNameText);
//			mainGUI.currentVideoDisplay.setText(mainGUI.currentSelectedVideoFile.getName());
//		}
//		else {
//			JOptionPane.showMessageDialog(new JPanel(), "Audio Overlay complete!");
//		}
	}

}