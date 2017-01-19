package pz.pacman.mvc.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PopupWindow {
	
	protected JDialog dialog;
	
	public PopupWindow() {
		this.dialog = null;
	}
	
	public static void show(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static int showConfirm(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	}
	
	public static String inputDialog(JFrame frame, String title, String message) {
		 return JOptionPane.showInputDialog(frame, message, title, JOptionPane.QUESTION_MESSAGE);
	}
}
