package pz.pacman.engine.element.dynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import pz.pacman.constants.Direction;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.engines.CollisionEngine;

public class Pacman extends OvalCharacter {

	private boolean isReverse;
	
	public Pacman(int x, int y, int width, int height) {
		super(x, y, width, height, Direction.RIGHT);
		this.color = PacmanConstants.pacmanColor;
		this.setReverse(false);
	}

	private int getAngle() {
		int angle = 0;
		
		switch (this.direction) {
			case RIGHT:	angle = 0;		break;
			case LEFT:	angle = 180;	break;
			case UP:	angle = 90;		break;
			case DOWN:	angle = 270;	break;
		}
		
		return angle;
	}
	
	private int getEyePositionX() {
		int pos = 0;
		
		switch (this.direction) {
			case RIGHT:
			case LEFT:	
				pos = this.x;	
			break;
			
			case UP:
			case DOWN:	
				pos = this.x + (PacmanConstants.pacmanRadius/2);	
			break;
		}
		
		return pos;
	}
	
	private int getEyePositionY() {
		int pos = 0;
		
		switch (this.direction) {
			case RIGHT:
			case LEFT:	
				pos = this.y - (PacmanConstants.pacmanRadius/2);	
			break;
			
			case UP:
			case DOWN:	
				pos = this.y;	
			break;
		}
		
		return pos;
	}
	
	private int getBlackEyePositionX() {
		int pos = 0;
		
		switch (this.direction) {
			case RIGHT:
				pos = getEyePositionX();
			break;
			
			case LEFT:	
				pos = getEyePositionX() - PacmanConstants.pacmanEyeOuterRadius;	
			break;
			
			case UP:			
			case DOWN:	
				pos = getEyePositionX() - PacmanConstants.pacmanEyeOuterRadius/2;	
			break;
		}
		
		return pos;
	}
	
	private int getBlackEyePositionY() {
		int pos = 0;
		
		switch (this.direction) {
		case RIGHT:		
		case LEFT:	
			pos = getEyePositionY() - PacmanConstants.pacmanEyeInnerRadius;
		break;
		
		case UP:	
			pos = getEyePositionY() - PacmanConstants.pacmanEyeOuterRadius;
			
		case DOWN:	
			pos = getEyePositionY();	
		break;
	}
		
		return pos;
	}
	
	private void paintBody(Graphics graph, int angle) {
		graph.setColor(this.color);
		graph.fillArc(this.x - PacmanConstants.pacmanRadius, this.y - PacmanConstants.pacmanRadius, 
				2*PacmanConstants.pacmanRadius, 2*PacmanConstants.pacmanRadius, 
				20 + angle, 320);
		
		graph.setColor(Color.BLACK);
		graph.drawArc(this.x - PacmanConstants.pacmanRadius, this.y - PacmanConstants.pacmanRadius, 
				2*PacmanConstants.pacmanRadius, 2*PacmanConstants.pacmanRadius, 
				20 + angle, 320);
	}
	
	private void paintEyes(Graphics graph, int eyePositionX, int eyePositionY, int blackEyePositionX, int blackEyePositionY) {
		graph.setColor(Color.WHITE);
		graph.fillArc(eyePositionX - PacmanConstants.pacmanEyeOuterRadius, eyePositionY - PacmanConstants.pacmanEyeOuterRadius, 
				2*PacmanConstants.pacmanEyeOuterRadius, 2*PacmanConstants.pacmanEyeOuterRadius, 
				0, PacmanConstants.fullAngle);
		
		graph.setColor(Color.BLACK);
		graph.drawArc(eyePositionX - PacmanConstants.pacmanEyeOuterRadius, eyePositionY - PacmanConstants.pacmanEyeOuterRadius,
				2*PacmanConstants.pacmanEyeOuterRadius, 2*PacmanConstants.pacmanEyeOuterRadius, 
				0, PacmanConstants.fullAngle);
		
		graph.fillArc(blackEyePositionX, blackEyePositionY, 
				2*PacmanConstants.pacmanEyeInnerRadius, 2*PacmanConstants.pacmanEyeInnerRadius, 
				0, PacmanConstants.fullAngle);
	}
	
	@Override
	public void paint(Graphics graph) {	
		int angle = getAngle();
		int eyePositionX = getEyePositionX();
		int eyePositionY = getEyePositionY();
		int blackEyePositionX = getBlackEyePositionX();
		int blackEyePositionY = getBlackEyePositionY();
		
		this.paintBody(graph, angle);
		this.paintEyes(graph, eyePositionX, eyePositionY, blackEyePositionX, blackEyePositionY);
	}

	@Override
	public void move() {
		LinkedList<Direction> directions = CollisionEngine.getInstance().getPossibleDirections(this.getGridRectangle());
		
		boolean isRequiredDirectionAllowed = false;
		boolean isDirectionAllowed = false;
		
		for (Direction x : directions) {
			isDirectionAllowed = (x.equals(this.direction)) ? true : isDirectionAllowed;
			isRequiredDirectionAllowed = (x.equals(this.requiredDirection)) ? true : isRequiredDirectionAllowed;
		}
		
		if (isRequiredDirectionAllowed) {
			this.direction = this.requiredDirection;
		} 
		else if (!isRequiredDirectionAllowed && !isDirectionAllowed){
			if (directions.size() > 1) {
				this.direction = directions.get(new Random().nextInt(directions.size()));
			} else {
				this.direction = directions.get(0);
			}
			
			this.requiredDirection = this.direction;
		}
		
		switch(this.direction) {
			case UP: 	this.y -= PacmanConstants.moveableSpeedPerFrame; 	break;
			case DOWN: 	this.y += PacmanConstants.moveableSpeedPerFrame; 	break;
			case LEFT:	this.x -= PacmanConstants.moveableSpeedPerFrame; 	break;
			case RIGHT:	this.x += PacmanConstants.moveableSpeedPerFrame; 	break;
		};
	}

	public boolean isReverse() {
		return isReverse;
	}

	public void setReverse(boolean isReverse) {
		this.isReverse = isReverse;
	}

}
