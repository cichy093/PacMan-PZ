package pz.pacman.editor.mvc.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import pz.pacman.editor.constants.AppConstants;

public class BaseView {

	protected static JFrame frame;

	public BaseView() {
		if (frame == null) {
			initialize();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(AppConstants.backgroundColor);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		frame.setTitle(AppConstants.appTitle);
		frame.setBounds(100, 100, AppConstants.windowWidth, AppConstants.windowHeight);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JFrame getMainFrame() {
		return frame;
	}
	
	public void repaint() {
		getMainFrame().repaint();
	}
}
