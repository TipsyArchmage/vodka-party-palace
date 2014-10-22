package vamix;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;



public class DownloadWorker extends SwingWorker<String, String> {

	private String url;
	private String downloadLocation;
	
	//private Process process;
	private ProcessBuilder builder;
	
	
	private int exit=-1;
	private Process process;
	
	public DownloadWorker(String url, String download) {
		this.url = url;
		this.downloadLocation= download;
	}
	
	

	// Calls wget command on the input url on a process executing on a background thread
	@Override
	protected String doInBackground() throws Exception {
		File fileDir = new File(downloadLocation);	
		builder = new ProcessBuilder();
		builder.directory(fileDir);
		builder.command("wget", "-c", url);
		
		
		builder.redirectErrorStream(true);
		process = builder.start();
		InputStream stdout = process.getInputStream();
		BufferedReader stdoutBuffered = new BufferedReader(new InputStreamReader(stdout));
		String line = null;
		// Read linux output from wget command
		while ((line = stdoutBuffered.readLine()) != null ) {
			if (isCancelled()) {
				process.destroy();
				
			}
			System.out.println(line);
			if (line.contains("% ")) {
				String[] parts = line.split("%");
				int progress = Integer.parseInt(parts[0].substring(parts[0].length() - 3, parts[0].length()).trim());
				setProgress(progress);
			}
		}
		
	   
	   
	   
		
	   		
		return null;
	}
	

	protected void done() {
	  
	   
		//if(exit==-1){
	   if (isCancelled()) {
		JOptionPane.showMessageDialog(new JPanel(), "Download cancelled");
		}
	   
	   else{
	   process.destroy();
	   
	   exit=process.exitValue();
	   //System.out.println(exit);
	   }
	   
	   if(exit==0){
			JOptionPane.showMessageDialog(new JPanel(), "Download complete!");
			if(Download.checkBox.isSelected()){
				
				File name=new File(url);
				String finalName=name.getName();
				mainGUI.checkSelectedVideoFile=new File(downloadLocation+"/"+finalName);

				try {
					if(VideoPlayback.checkVideoAudio()){
						mainGUI.currentSelectedVideoFile = mainGUI.checkSelectedVideoFile;
						mainGUI.currentVideoDisplay.setText(finalName);
						mainGUI.enableButtons("all");
					}
					else{
						JOptionPane.showMessageDialog(mainGUI.contentPane,"Not a video or audio file",
								"Type Error",JOptionPane.ERROR_MESSAGE);   
							    
							    
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	   
	   
	   //Different outputs for different error values
		else if(exit==1){
			JOptionPane.showMessageDialog(new JPanel(), "Error!");
		}
		else if(exit==2){
			JOptionPane.showMessageDialog(new JPanel(), "Parse Error");
		}
		else if(exit==3){
			JOptionPane.showMessageDialog(new JPanel(), "File I/O error");
		}
		else if(exit==4){
			JOptionPane.showMessageDialog(new JPanel(), "Network Failure");
		}
		else if(exit==5){
			JOptionPane.showMessageDialog(new JPanel(), "SSL Verification Error");
		}
		else if(exit==6){
			JOptionPane.showMessageDialog(new JPanel(), "Username/ password authentication failure");
		}
		else if(exit==7){
			JOptionPane.showMessageDialog(new JPanel(), "Protocol errors");
		}
		else if(exit==8){
			JOptionPane.showMessageDialog(new JPanel(), "Server issued an error response");
		}
		int progress=0;
		setProgress(progress);
		Download.downloadButton.setText("Download!");
		
	}

	
	
}