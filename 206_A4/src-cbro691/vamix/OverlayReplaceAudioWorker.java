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
//	private int startMin;
//	private int startSec;
//	private int durationMin;
//	private int durationSec;
	private int startVideo;
	private File deletePlease;
	private File deletePlease2;

	public OverlayReplaceAudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode, 
			int startTimeVideo) {
		this.currentVideoFilePath = currentVideoFilePath;
		this.currentAudioFilePath = currentAudioFilePath;
		this.outputNameText = outputNameText;
		this.mode = inputMode;
		this.startVideo=startTimeVideo;
//		this.startMin=startMinutes;
//		this.startSec=startSeconds;
//		this.durationMin=durationMinutes;
//		this.durationSec=durationSeconds;
	}

	@Override
	protected String doInBackground() throws Exception {
		builder = new ProcessBuilder();
		//First add the correct number of seconds of silence to the start of the extracted track
		//File TEMPmp3 = soundOfSilence(startVideo, currentAudioFilePath);
		
		//Replace		
		if (mode==5){
			//ffmpeg -i video.avi -i audio.mp3 -map 0 -map 1 -codec copy -shortest output_video.avi
			//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
			
			builder.command("avconv","-y", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
			
		}
		//Overlay
		else if (mode==6){
			
			//First call StripAudioWorker and remove the audio from the video
			//Then merge this with the extracted track to a new temporary file
			//Then overlay as before
			

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
	
	//Method that was intended to allow the user to start the audio later in the video but didn't work out
	//Largely because it slowed down the processing time HUGELY
	private File soundOfSilence(int secondsSilence, String audioPath) throws IOException {
		//avconv -i silence.mp4 -vcodec copy output.mp3
		//avconv -i concat:output.mp3\|2.mp3 -codec copy temp.mp3
		//mainGUI.class.getResource("/audioResources/silence.mp3");
		
		// Load the directory as a resource
		URL silence_URL = mainGUI.class.getResource("/audioResources/silence.mp3");
		
		
		try {
			// Turn the resource into a File object
			File silence = new File(silence_URL.toURI());
			
			//Convert audio track to mp3 file
			builder.command("avconv","-y", "-i", audioPath, "-vcodec","copy", Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
			builder.command("avconv","-y", "-i", audioPath, "-vcodec","copy", Audio.saveAudioNewFile.toString()+"/TEMP2.mp3");
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			// Read linux output from avconv command
			while ((line = stdoutBuffered.readLine()) != null ) {
				System.out.println(line);
			}
			
			//Create 2 new temp files
			deletePlease=new File(Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
			deletePlease2=new File(Audio.saveAudioNewFile.toString()+"/TEMP2.mp3");
//			System.out.println(deletePlease.toString());
//			System.out.println(deletePlease2.toString());
			//Process process = builder.start();
			
			for(int i=0; i<secondsSilence;i++){
				ProcessBuilder builder2;
				builder2 = new ProcessBuilder();
				deletePlease=new File(Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
				deletePlease2=new File(Audio.saveAudioNewFile.toString()+"/TEMP2.mp3");
				//avconv -i concat:output.mp3\|2.mp3 -codec copy temp.mp4
				
				builder2.command("avconv","-y","-i","\"concat:"+silence.toString()+"|"+
						deletePlease.toString()+"\"","-acodec","copy",deletePlease2.toString());
						

				System.out.print(builder2.command());
				//Process process2 = builder.start();
				builder2.redirectErrorStream(true);
				Process process2 = builder2.start();
				InputStream stdout2 = process2.getInputStream();
				BufferedReader stdoutBuffered2 = new BufferedReader(new InputStreamReader(stdout2));
				String line2 = null;
				
				// Read linux output from avconv command
				while ((line2 = stdoutBuffered2.readLine()) != null ) {
					System.out.println(line2);
				}
				
				
				
				
				
				
				
				
				
				
				
				System.out.print(builder2.command());
				//Make temp 1 the same as temp 2
				builder2.command("avconv","-y" ,"-i", deletePlease2.toString(), "-vcodec","copy", Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
				
				
				System.out.print(builder2.command());
				//Process process2 = builder.start();
				builder2.redirectErrorStream(true);
				process2 = builder2.start();
				stdout2 = process2.getInputStream();
				stdoutBuffered2 = new BufferedReader(new InputStreamReader(stdout2));
				line2 = null;
				
				// Read linux output from avconv command
				while ((line2 = stdoutBuffered2.readLine()) != null ) {
					System.out.println(line2);
				}
			}
			
			
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//File silence=new File(mainGUI.class.getResource("/audioResources/silence.mp3"))
		
		
		
		
		return deletePlease;
	}

	protected void done(){
		
		//Result message 
		
		
			//deletePlease.delete();
			//deletePlease2.delete();
			Audio.outputTEMPFile.delete();
			AudioSliders.videoLocationSlider.setEnabled(true);
			AudioSliders.extractLengthAudio.setEnabled(true);
			AudioSliders.extractStartSlider.setEnabled(true);
			JOptionPane.showMessageDialog(new JPanel(), "Audio Overlay complete!");
			//Auto set current video
			mainGUI.currentSelectedVideoFile=new File(outputNameText);
			VideoPlayback.currentVideoDisplay.setText(mainGUI.currentSelectedVideoFile.getName());
		
	}
	
}
