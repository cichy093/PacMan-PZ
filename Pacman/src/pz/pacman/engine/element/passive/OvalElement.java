package pz.pacman.engine.element.passive;

import java.awt.Rectangle;

import pz.pacman.engine.element.Element;

public abstract class OvalElement extends Element {

	public OvalElement(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle getRectangle() {
		return new Rectangle(this.x - this.width/2, this.y - this.height/2, this.width, this.height);
	}
}
