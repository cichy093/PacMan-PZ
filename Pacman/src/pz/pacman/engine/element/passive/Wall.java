package pz.pacman.engine.element.passive;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall extends RectElement {

	public Wall(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Wall(Rectangle rect) {
		super(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void paint(Graphics graph) {
		graph.setColor(this.color);
		graph.fillRect(this.x, this.y, this.width, this.height);		
	}

}
