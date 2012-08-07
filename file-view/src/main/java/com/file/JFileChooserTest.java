package com.file;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.file.filter.ExtensionFileFilter;
import com.file.jasper.ReportView;

/**
 * Classe Main responsável pela execussão da interface
 * 
 * @author andrevaladas
 */
public class JFileChooserTest {
	public static void main(final String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		final JFrame frame = new JFrame("JComboBox Test");
		frame.setLayout(new FlowLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				System.exit(0);
			}
		});
		final Toolkit tk = Toolkit.getDefaultToolkit();
		final Dimension screenSize = tk.getScreenSize();
		final int screenHeight = screenSize.height;
		final int screenWidth = screenSize.width;
		frame.setSize(screenWidth / 2, screenHeight / 2);
		frame.setLocation(screenWidth / 2, screenHeight / 4);

		final JButton button = new JButton("Select File");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent ae) {
				final JFileChooser fileChooser = new JFileChooser();
				final FileFilter filter = new ExtensionFileFilter("TXT and JPEG", new String[] { "TXT", "JPEG" });
				fileChooser.setFileFilter(filter);

				final int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					final File selectedFile = fileChooser.getSelectedFile();
					new ReportView(selectedFile);
				}
			}
		});
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
	}
}