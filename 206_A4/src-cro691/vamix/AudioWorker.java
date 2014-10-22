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

	public AudioWorker(String currentVideoFilePath, String currentAudioFilePath, String outputNameText, int inputMode, 
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
		// Creates a video file containing the selected video file with the selected audio file overlaid on top of its audio
		builder = new ProcessBuilder();
		if(mode==1){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-map", "0:1", "-c:a", "copy", outputNameText);
		}
		else if(mode==2){
			builder = new ProcessBuilder("avconv","-y", "-i", mainGUI.currentSelectedVideoFile.toString(), "-vcodec", "copy", "-an", outputNameText);
		}
		else if (mode==3){
			builder.command("avconv", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
		}
		else if (mode==4){
			//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
			
			builder.command("avconv", "-ss", 
					+0+":"+startMin+":"+startSec,"-i",currentAudioFilePath,"-t",0+":"+durationMin+":"+durationSec
					,"-codec:a aac -strict experimental","copy",outputNameText);
		}
		//Replace
		else if (mode==5){
			
			//File TEMPmp3 = soundOfSilence(Audio.videoLocationSlider.getValue(), currentAudioFilePath);

			//ffmpeg -i video.avi -i audio.mp3 -map 0 -map 1 -codec copy -shortest output_video.avi
			//avconv  -i  $filename -ss $starttime -t $runningtime $outputfile
			builder.command("avconv","-y", "-i", currentAudioFilePath, "-i", currentVideoFilePath, "-c", "copy", outputNameText);
		}
		//Overlay
		else if (mode==6){
//			System.out.print("\n\n\n\n");
//			System.out.print("BEP");
//			System.out.print("\n\n\n\n\n");
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
	
	private File soundOfSilence(int secondsSilence, String audioPath) throws IOException {
		//avconv -i silence.mp4 -vcodec copy output.mp3
		//avconv -i concat:output.mp3\|2.mp3 -codec copy temp.mp4
		//mainGUI.class.getResource("/audioResources/silence.mp3");
		
		// Load the directory as a resource
		URL silence_URL = mainGUI.class.getResource("/audioResources/silence.mp3");
		// Turn the resource into a File object
		
		try {
			
			File silence = new File(silence_URL.toURI());
			System.out.println("\nhey"+silence.toString()+"\n bop");
			builder.command("avconv", "-i", audioPath, "-vcodec","copy", Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
			String line = null;
			// Read linux output from avconv command
			while ((line = stdoutBuffered.readLine()) != null ) {
				System.out.println(line);
			}
			deletePlease=new File(Audio.saveAudioNewFile.toString()+"/TEMP1.mp3");
			System.out.println(deletePlease.toString());
			//Process process = builder.start();
			for(int i=0; i<secondsSilence;i++){
				//avconv -i concat:output.mp3\|2.mp3 -codec copy temp.mp4
				
				builder.command("avconv", "-i", "concat:"+silence.toString()+"\\|"+deletePlease.toString(),"-codec", "copy",
						Audio.saveAudioNewFile.toString()+"/TEMP2.mp3");
				
				//Process process2 = builder.start();
				builder.redirectErrorStream(true);
				Process process2 = builder.start();
				InputStream stdout2 = process2.getInputStream();
				BufferedReader stdoutBuffered2 = new BufferedReader(new InputStreamReader(stdout2));
				String line2 = null;
				// Read linux output from avconv command
				while ((line = stdoutBuffered2.readLine()) != null ) {
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
		
		if(mode==1||mode==2){
		JOptionPane.showMessageDialog(new JPanel(), "Audio Removed!");
		}
//		else if(mode==2){
//			JOptionPane.showMessageDialog(new JPanel(), "Audio Removed!");
//		}
		else if(mode==4){
			
			//avconv -i 1.mp4 -i 2.mp3 -filter_complex amix=inputs=2:duration=first:dropout_transition=3 -strict experimental TEST.mp4
			//avconv -i sintel_trailer-480p.mp4 -i TEST.mp4 -map 0 -map 1:0 -c:v copy -c:a copy -ac 1 TES1T.mp4

			
			//JOptionPane.showMessageDialog(new JPanel(), "The File will automatically set as the current video");
			Audio.outputTEMPFile=new File(outputNameText);
			//Replace
			if(Audio.replaceAudioOptionRadioBtn.isSelected()){
				String nameNoExtension=Audio.saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				String outputName=nameNoExtension+"AUDIOREPLACE.mp4";
				
				AudioWorker workerReplace=new AudioWorker(mainGUI.currentSelectedVideoFile.toString()
						,Audio.outputTEMPFile.toString(), outputName, 5,0,0,0,0);
				
				workerReplace.execute();
			}
			//Overlay
			else{
				String nameNoExtension=Audio.saveAudioNewFile.getAbsolutePath()+"/"+mainGUI.currentSelectedVideoFile.getName();
				nameNoExtension=nameNoExtension.substring(0, nameNoExtension.lastIndexOf('.'));
				String outputName=nameNoExtension+"AUDIOoverlay.mp4";
				
				
				AudioWorker workerOverlay=new AudioWorker(mainGUI.currentSelectedVideoFile.toString()
						, Audio.outputTEMPFile.toString(), outputName, 6,0,0,0,0);
				
				workerOverlay.execute();
			}
			
		}
		else if(mode==5||mode==6){
			//deletePlease.delete();
			Audio.outputTEMPFile.delete();
			Audio.videoLocationSlider.setEnabled(true);
			Audio.extractLengthAudio.setEnabled(true);
			Audio.extractStartSlider.setEnabled(true);
			JOptionPane.showMessageDialog(new JPanel(), "Audio Overlay complete!");
			//Auto set current video
			mainGUI.currentSelectedVideoFile=new File(outputNameText);
			mainGUI.currentVideoDisplay.setText(mainGUI.currentSelectedVideoFile.getName());
		}
		else {
			JOptionPane.showMessageDialog(new JPanel(), "Audio Overlay complete!");
		}
	}

}