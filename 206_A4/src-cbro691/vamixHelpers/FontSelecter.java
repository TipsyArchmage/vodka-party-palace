package vamixHelpers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import vamix.Text;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * This class only deals with the font and size selection pop up windows. This is made visable
 * through the text class and allows the user to select a font and size.
 * The FontSelecter object itself is not my own work and is open source code, copyright as follows
 * //************************************************************
 * Copyright 2004-2005,2007-2008 Masahiko SAWAI All Rights Reserved. 
   ************************************************************/
 

public class FontSelecter extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFontChooser fontChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			FontSelecter dialog = new FontSelecter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FontSelecter() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			//Create new font chooser instance
			fontChooser = new JFontChooser();
			contentPanel.add(fontChooser);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						//Set variables in Text class
						Text.fontInput=fontChooser.getSelectedFont();
						Text.sizeInput=fontChooser.getSelectedFontSize();
						//Close window
						Text.fontSelecter.setVisible(false);
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//Close window
						Text.fontSelecter.setVisible(false);
						
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
