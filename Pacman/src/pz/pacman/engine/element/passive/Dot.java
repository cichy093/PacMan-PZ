package pz.pacman.engine.element.passive;

import java.awt.Graphics;

import pz.pacman.constants.PacmanConstants;

public class Dot extends OvalElement {

	boolean isBonus;
	
	public Dot(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		this.color = PacmanConstants.dotColor;
		this.isBonus = false;
	}

	public boolean isBonus() {
		return isBonus;
	}

	public void setBonus(boolean isBonus) {
		this.isBonus = isBonus;
	}

	@Override
	public void paint(Graphics graph) {
		graph.setColor((this.isBonus) ? PacmanConstants.dotBonusColor : color);
		graph.fillArc(this.x - this.width/2, this.y - this.height/2, this.width, this.height, 0, PacmanConstants.fullAngle);
	}

}
