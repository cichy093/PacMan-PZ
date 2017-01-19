package pz.pacman.editor.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import pz.pacman.editor.constants.AppConstants;
import pz.pacman.editor.entities.Block;
import pz.pacman.editor.mvc.model.MainWindowModel;


public class MapComp extends JComponent {
	private static final long serialVersionUID = -8678115443009583291L;

	private MainWindowModel model;
	
	public MapComp() {
		this.model = null;
	}
	
	public void setModel(MainWindowModel model) {
		this.model = model;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(AppConstants.mapWidth, AppConstants.mapHeight);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if (this.model != null) {
			for (Block x : model.getBlocks()) {
				if (x.isSelected() == true) {
					g2d.setColor(AppConstants.selectedBlock);
				} else {
					g2d.setColor(AppConstants.nonSelectedBlock);
				}
				
				g2d.fill(x.getRect());
				
				g2d.setColor(AppConstants.borderColor);
				g2d.draw(x.getRect());
			}
		}
	}
}