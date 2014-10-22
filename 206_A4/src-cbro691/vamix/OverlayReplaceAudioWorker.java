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



public class OverlayReplaceAudioWorker extends SwingWorker {

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

	public OverlayReplaceAudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode, 
			int startMinutes, int startSeconds, int durationMinutes, int durationSeconds) {
		this.currentVideoFilePath = currentVideoFilePath;
		this.currentAudioFilePath = currentAudioFilePath;
		this.outputNameText = outputNameText;
		this.mode = inputMode;
		this.startMin=startMinutes;
		this.startSec=startSeconds;
		this.durationMin=durationMinutes;
		this.durationSec=durationSeconds;
	}

	@Override
	protected String doInBackground() throws Exception {
				if (mode==5){
					
					//File TEMPmp3 = soundOfSilence(Audio.videoLocationSlider.getValue(), currentAudioFilePath);

					//ffmpeg -i video.avi -i audio.mp3 -map 0 -map 1 -codec copy -shortest output_video.avi
					//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
					builder.command("avconv","-y", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
				}
				//Overlay
				else if (mode==6){
//					System.out.print("\n\n\n\n");
//					System.out.print("BEP");
//					System.out.print("\n\n\n\n\n");
					//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
					
					builder.command("avconv","-y", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
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

}
