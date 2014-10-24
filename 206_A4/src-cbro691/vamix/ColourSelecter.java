package vamix;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ColourSelecter extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ColourSelecter dialog = new ColourSelecter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ColourSelecter() {
		setTitle("Select the Colour to use for your font.");
		setBounds(100, 100, 428, 171);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		
		//Set of buttons that all show a selectable colour option
		final JButton white = new JButton("White");
		white.addActionListener(new ActionListener() {
			//Set to WHITE
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=white.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		white.setBackground(Color.WHITE);
		white.setFocusable(false);
		contentPanel.add(white, "2, 2");
		
		final JButton pink = new JButton("Pink");
		pink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=pink.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		pink.setBackground(Color.PINK);
		pink.setFocusable(false);
		contentPanel.add(pink, "4, 2");
		
		final JButton blue = new JButton("Blue");
		blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=blue.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		blue.setForeground(Color.WHITE);
		blue.setBackground(Color.BLUE);
		blue.setFocusable(false);
		contentPanel.add(blue, "6, 2");
		
		final JButton orange = new JButton("Orange");
		orange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=orange.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		orange.setBackground(Color.ORANGE);
		orange.setFocusable(false);
		contentPanel.add(orange, "8, 2");
		
		final JButton grey = new JButton("Grey");
		grey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=grey.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		grey.setForeground(Color.WHITE);
		grey.setBackground(Color.GRAY);
		grey.setFocusable(false);
		contentPanel.add(grey, "2, 4");
		
		final JButton yellow = new JButton("Yellow");
		yellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=yellow.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		yellow.setBackground(Color.YELLOW);
		yellow.setFocusable(false);
		contentPanel.add(yellow, "4, 4");
		
		final JButton lightgrey = new JButton("Light Grey");
		lightgrey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=lightgrey.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		lightgrey.setBackground(Color.LIGHT_GRAY);
		lightgrey.setFocusable(false);
		contentPanel.add(lightgrey, "6, 4");
		
		final JButton green = new JButton("Green");
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=green.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		green.setBackground(Color.GREEN);
		green.setFocusable(false);
		contentPanel.add(green, "8, 4");
		
		final JButton black = new JButton("Black");
		black.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=black.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		black.setForeground(Color.WHITE);
		black.setBackground(Color.BLACK);
		black.setFocusable(false);
		contentPanel.add(black, "2, 6");
		
		final JButton magenta = new JButton("Magenta");
		magenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=magenta.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		magenta.setBackground(Color.MAGENTA);
		magenta.setFocusable(false);
		contentPanel.add(magenta, "4, 6");
		
		final JButton red = new JButton("Red");
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=red.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		red.setForeground(Color.WHITE);
		red.setBackground(Color.RED);
		red.setFocusable(false);
		contentPanel.add(red, "6, 6");
		
		final JButton cyan = new JButton("Cyan");
		cyan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Text.fontColour=cyan.getBackground();
				
				//Close window
				Text.colourSelecter.setVisible(false);
			}
		});
		cyan.setBackground(Color.CYAN);
		cyan.setFocusable(false);
		contentPanel.add(cyan, "8, 6, center, default");
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Close window
						Text.colourSelecter.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
