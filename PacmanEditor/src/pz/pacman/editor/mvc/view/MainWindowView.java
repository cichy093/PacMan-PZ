package pz.pacman.editor.mvc.view;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import pz.pacman.editor.components.MapComp;
import pz.pacman.editor.constants.AppConstants;
import pz.pacman.editor.mvc.model.MainWindowModel;

public class MainWindowView extends BaseView {

	private JPanel panel;
	
	private JMenu mnFile;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmSave;
	private JMenuItem mntmExit;
	
	private JRadioButton radioAddWall;
	private JRadioButton radioDeleteWall;
	
	private JButton btnVerifyMap;
	private JButton btnClearMap;
	
	private MapComp component;

	public JMenuItem getMntmNew() {
		return mntmNew;
	}

	public JMenuItem getMntmOpen() {
		return mntmOpen;
	}

	public JMenuItem getMntmSaveAs() {
		return mntmSaveAs;
	}

	public JMenuItem getMntmSave() {
		return mntmSave;
	}

	public JMenuItem getMntmExit() {
		return mntmExit;
	}
	
	public JRadioButton getRadioAddWall() {
		return radioAddWall;
	}
	
	public JRadioButton getRadioDeleteWall() {
		return radioDeleteWall;
	}
	
	public MapComp getComponent() {
		return component;
	}

	public JButton getBtnVerifyMap() {
		return btnVerifyMap;
	}

	public JButton getBtnClearMap() {
		return btnClearMap;
	}

	public MainWindowView() {
		initialize();
	}
	
	public void setCompModel(MainWindowModel model) {
		this.component.setModel(model);
	}

	private void initialize() {
		this.panel = new JPanel();

		getMainFrame().setContentPane(panel);
		panel.setBounds(0, 0, 469, 545);
		panel.setLayout(null);
		
		this.addMenuBar();
		
		this.component = new MapComp();
		component.setBorder(new LineBorder(new Color(0, 0, 0)));
		component.setBounds(10, 84, 450, 450);
		panel.add(component);
		
		this.addToolboxPanel();
		this.addOptionsPanel();
	}
	
	protected void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnFile = new JMenu(AppConstants.menuFile);
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem(AppConstants.menuNew);
		mntmNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(mntmNew);

		mntmOpen = new JMenuItem(AppConstants.menuOpen);
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(mntmOpen);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		mntmSaveAs = new JMenuItem(AppConstants.menuSaveAs);
		mnFile.add(mntmSaveAs);
		
		mntmSave = new JMenuItem(AppConstants.menuSave);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(mntmSave);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		mntmExit = new JMenuItem(AppConstants.menuExit);
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnFile.add(mntmExit);
	}
	
	protected void addToolboxPanel() {
		JPanel toolboxPanel = new JPanel();
		toolboxPanel.setBorder(new TitledBorder(null, AppConstants.mainToolbox, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		toolboxPanel.setBounds(10, 11, 221, 62);
		panel.add(toolboxPanel);
		toolboxPanel.setLayout(null);
		
		ButtonGroup bg1 = new ButtonGroup( );
		radioAddWall = new JRadioButton(AppConstants.mainAddWall);
		radioAddWall.setSelected(true);
		radioAddWall.setBounds(20, 23, 80, 23);
		toolboxPanel.add(radioAddWall);
		
		radioDeleteWall = new JRadioButton(AppConstants.mainDeleteWall);
		radioDeleteWall.setBounds(105, 23, 90, 23);
		toolboxPanel.add(radioDeleteWall);
		
		bg1.add(radioAddWall);
		bg1.add(radioDeleteWall);
	}
	
	protected void addOptionsPanel() {
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(new TitledBorder(null, AppConstants.mainOptions, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		optionsPanel.setBounds(241, 11, 221, 62);
		panel.add(optionsPanel);
		optionsPanel.setLayout(null);
		
		btnVerifyMap = new JButton(AppConstants.mainVerify);
		btnVerifyMap.setBounds(10, 24, 89, 23);
		optionsPanel.add(btnVerifyMap);
		
		btnClearMap = new JButton(AppConstants.mainClear);
		btnClearMap.setBounds(120, 24, 89, 23);
		optionsPanel.add(btnClearMap);
	}
	
	public JPanel getMainPanel() {
		return this.panel;
	}

	public int showConfirmOnExit() {
		return PopupWindow.showConfirm("Confirm decision", "Do you want to quit?");
	}
	
	public File showOpenFileChooser() {
		File selectedFile = null;
		
		JFileChooser fileChooser = new JFileChooser(".\\");
		FileNameExtensionFilter saveFilter = new FileNameExtensionFilter("Pacman map file (*.map)", "map");
		fileChooser.addChoosableFileFilter(saveFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int chooserState = fileChooser.showOpenDialog(getMainFrame());
		if (chooserState == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		
		return selectedFile;
	}
	
	public File showSaveFileChooser() {
		File selectedFile = null;
		
		JFileChooser fileChooser = new JFileChooser(".\\");
		FileNameExtensionFilter saveFilter = new FileNameExtensionFilter("Pacman map file (*.map)", "map");
		fileChooser.addChoosableFileFilter(saveFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		int chooserState = fileChooser.showSaveDialog(getMainFrame());
		if (chooserState == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
		}
		
		return selectedFile;
	}
}
