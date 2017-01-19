package pz.pacman.engine.element.dynamic;

import java.awt.Rectangle;

import pz.pacman.constants.Direction;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.element.Element;
import pz.pacman.engine.interfaces.IMoveable;

public abstract class OvalCharacter extends Element implements IMoveable {

	protected Direction direction;
	protected Direction requiredDirection;
	
	public OvalCharacter(int x, int y, int width, int height, Direction direction) {
		super(x, y, width, height);
		
		this.direction = direction;
		this.requiredDirection = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getRequiredDirection() {
		return requiredDirection;
	}

	public void setRequiredDirection(Direction requiredDirection) {
		this.requiredDirection = requiredDirection;
	}
	
	@Override
	public Rectangle getGridRectangle() {
		return new Rectangle(this.x - PacmanConstants.gridWidth/2, this.y - PacmanConstants.gridHeight/2, PacmanConstants.gridWidth, PacmanConstants.gridHeight);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(this.x - this.width/2, this.y - this.height/2, this.width, this.height);
	}
}
