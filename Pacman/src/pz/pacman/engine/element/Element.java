package pz.pacman.engine.element;

import java.awt.Color;
import java.awt.Rectangle;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.interfaces.IPaintable;

public abstract class Element implements IPaintable {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected Color color;
	
	public Element(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	protected int getWidth() {
		return width;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	protected int getHeight() {
		return height;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(this.x, this.y, this.width, this.height);		
	}
	
	public Rectangle getGridRectangle() {
		return new Rectangle(this.x, this.y, PacmanConstants.gridWidth, PacmanConstants.gridHeight);
	}
}
