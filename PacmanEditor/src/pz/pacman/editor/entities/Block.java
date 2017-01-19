package pz.pacman.editor.entities;

import java.awt.Rectangle;

import pz.pacman.editor.constants.AppConstants;

public class Block {
	
	private int id;
	private int x;
	private int y;
	private boolean isSelected;
	
	public Block(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.isSelected = false;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public Rectangle getRect() {
		return new Rectangle(this.x, this.y, AppConstants.gridWidth, AppConstants.gridHeight);
	}
}
