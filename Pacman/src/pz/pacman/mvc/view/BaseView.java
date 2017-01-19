package pz.pacman.mvc.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import pz.pacman.constants.PacmanConstants;

public class BaseView {

	private static JFrame frame;

	public BaseView() {
		if (frame == null) {
			initialize();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.getContentPane().setBackground(PacmanConstants.backgroundColor);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		frame.setTitle(PacmanConstants.applicationTitle);
		frame.setBounds(100, 100, PacmanConstants.windowWidth, PacmanConstants.windowHeight);
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
