package vamix;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AudioSliders {

	private static JLabel initialTimeLabel;
	static JLabel endTimeAudioLabel;
	static JSlider extractStartSlider;
	private static JLabel lblSelectTheStart;
	private static JLabel totalLength;
	static JSlider extractLengthAudio;
	private static JLabel initialLength;
	private static JLabel lblSelectTheLength;
	private static JLabel initalVideoLength;
	private static JLabel videoLocationTitle;
	static JLabel totalVideoLength;
	static JSlider videoLocationSlider;

	/**
	 * @param args
	 */
	public AudioSliders() {
		//Label showing where selection was BAR 1
				initialTimeLabel = new JLabel("00:00");
				initialTimeLabel.setBounds(94, 165, 46, 14);
				mainGUI.audioPanel.add(initialTimeLabel);
				//Length of selected audio track BAR 1
				endTimeAudioLabel = new JLabel("00:00");
				endTimeAudioLabel.setBounds(220, 153, 46, 14);
				mainGUI.audioPanel.add(endTimeAudioLabel);
				
				//Slider to select start of audio track
				extractStartSlider = new JSlider();
				extractStartSlider.setBorder(null);
				extractStartSlider.setOpaque(false);
				extractStartSlider.setValue(0);
				extractStartSlider.addChangeListener(new SliderListener1());
				extractStartSlider.setSnapToTicks(true);
				extractStartSlider.setPaintTicks(true);
				extractStartSlider.setFocusable(false);
				extractStartSlider.setBounds(10, 153, 214, 26);
				
				mainGUI.audioPanel.add(extractStartSlider);
				
				lblSelectTheStart = new JLabel("<html>From which point in the Audio Track to you want to take Audio?</html>");
				lblSelectTheStart.setFont(new Font("Dialog", Font.BOLD, 11));
				lblSelectTheStart.setBounds(10, 123, 224, 27);
				mainGUI.audioPanel.add(lblSelectTheStart);
				//Length of selected audio track-Selection already made BAR 2
				totalLength = new JLabel("00:00");
				totalLength.setBounds(220, 220, 46, 14);
				mainGUI.audioPanel.add(totalLength);
				//Where current selection is BAR 2
				initialLength = new JLabel("00:00");
				initialLength.setBounds(94, 232, 46, 14);
				mainGUI.audioPanel.add(initialLength);
				//SLider to select amount of audio track to use
				extractLengthAudio = new JSlider();
				extractLengthAudio.setBorder(null);
				extractLengthAudio.setValue(0);
				extractLengthAudio.addChangeListener(new SliderListener2());
				extractLengthAudio.setSnapToTicks(true);
				extractLengthAudio.setPaintTicks(true);
				extractLengthAudio.setOpaque(false);
				extractLengthAudio.setFocusable(false);
				extractLengthAudio.setBounds(10, 220, 214, 26);
				mainGUI.audioPanel.add(extractLengthAudio);
				
				lblSelectTheLength = new JLabel("<html>How Much of the Audio Track Do You Want To Use?</html>");
				lblSelectTheLength.setBounds(10, 190, 224, 27);
				mainGUI.audioPanel.add(lblSelectTheLength);
				
				videoLocationTitle = new JLabel("<html>When Should the Audio Track start playing in the Video?</html>");
				videoLocationTitle.setBounds(10, 255, 224, 27);
				mainGUI.audioPanel.add(videoLocationTitle);
				//Current selection BAR 3
				initalVideoLength = new JLabel("00:00");
				initalVideoLength.setBounds(94, 297, 46, 14);
				mainGUI.audioPanel.add(initalVideoLength);
				//Total video length of file selected
				totalVideoLength = new JLabel("00:00");
				totalVideoLength.setBounds(220, 285, 46, 14);
				mainGUI.audioPanel.add(totalVideoLength);
				
				//Slider to choose placement in video
				videoLocationSlider = new JSlider();
				videoLocationSlider.setValue(0);
				videoLocationSlider.setSnapToTicks(true);
				videoLocationSlider.setPaintTicks(true);
				videoLocationSlider.addChangeListener(new SliderListener3());
				videoLocationSlider.setOpaque(false);
				videoLocationSlider.setFocusable(false);
				videoLocationSlider.setBorder(null);
				videoLocationSlider.setBounds(10, 285, 214, 26);
				mainGUI.audioPanel.add(videoLocationSlider);
				
				

	}
	
	class SliderListener1 implements ChangeListener {
		
		public void stateChanged(ChangeEvent e) {
			int startSliderGap=extractStartSlider.getMaximum()-extractStartSlider.getValue();
			int videoSliderGap=videoLocationSlider.getMaximum()-videoLocationSlider.getValue();
			if(startSliderGap>=videoSliderGap){
				extractLengthAudio.setMaximum(videoLocationSlider.getMaximum()-videoLocationSlider.getValue());
				
				String audioLengthTime=Audio.getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			else{
				extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-extractStartSlider.getValue());
				
				String audioLengthTime=Audio.getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			
			
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
			int currentLength = (int)source.getValue();
			String time=Audio.getLengthTime(currentLength);
			initialTimeLabel.setText(time);
//			//extractLengthAudio.setValue(0);
//			extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-currentLength);
//			
//			String audioLengthTime=getLengthTime(extractLengthAudio.getMaximum());
//			//System.out.print(audioLengthTime);
//			
//			totalLength.setText(audioLengthTime);
			
			//}
		}
	}
	
	class SliderListener2 implements ChangeListener {
		
		public void stateChanged(ChangeEvent e) {
			
			
			
			
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
				int currentLength = (int)source.getValue();
				String time=Audio.getLengthTime(currentLength);
				initialLength.setText(time);
				
				
			//}
		}
		
	}
	
	class SliderListener3 implements ChangeListener {
		
		public void stateChanged(ChangeEvent e) {
			int startSliderGap=extractStartSlider.getMaximum()-extractStartSlider.getValue();
			int videoSliderGap=videoLocationSlider.getMaximum()-videoLocationSlider.getValue();
			if(startSliderGap>=videoSliderGap){
				extractLengthAudio.setMaximum(videoLocationSlider.getMaximum()-videoLocationSlider.getValue());
				
				String audioLengthTime=Audio.getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			else{
				extractLengthAudio.setMaximum(extractStartSlider.getMaximum()-extractStartSlider.getValue());
				
				String audioLengthTime=Audio.getLengthTime(extractLengthAudio.getMaximum());
				//System.out.print(audioLengthTime);
				
				totalLength.setText(audioLengthTime);
			}
			JSlider source = (JSlider)e.getSource();
			//if (!source.getValueIsAdjusting()) {
			int currentLength = (int)source.getValue();
			String time=Audio.getLengthTime(currentLength);
			initalVideoLength.setText(time);
				
			//}
		}
	}

}
