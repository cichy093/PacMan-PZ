package pz.pacman.engine.element.dynamic;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import pz.pacman.constants.Direction;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.engines.AIEngine;
import pz.pacman.engine.engines.CollisionEngine;

public class Ghost extends OvalCharacter {

	private boolean isReversed;
	private AIEngine AI;
	
	public Ghost(int x, int y, int width, int height) {
		super(x, y, width, height, Direction.RIGHT);
		
		this.isReversed = false;
		this.AI = new AIEngine(this);
	}

	public boolean isReversed() {
		return isReversed;
	}

	public void setReversed(boolean isReversed) {
		this.isReversed = isReversed;
	}

	private void paintBody(Graphics graph) {
		graph.setColor((this.isReversed) ? Color.BLUE : this.color);
		graph.fillArc(this.x - PacmanConstants.ghostRadiusWidth, this.y - PacmanConstants.ghostRadiusHeight/2 - 4, 
				2*PacmanConstants.ghostRadiusWidth, 2*PacmanConstants.ghostRadiusHeight, 
				0, PacmanConstants.halfAngle);
		
		final int xPts[] = { 0, 0, 2, 6, 10, 14, 18, 22, 26, 29, 29 };
		final int yPts[] = { 0, 6, 6, 1, 6, 1, 6, 1, 6, 6, 0 };
	    
	    for(int i = 0; i < xPts.length; i++) {
	    	xPts[i] += this.x - PacmanConstants.ghostRadiusWidth + 1;
	    	yPts[i] += this.y + PacmanConstants.ghostRadiusHeight/2 - 3;
	    }

	    graph.fillPolygon(xPts, yPts, xPts.length);
	}
	
	private void paintEyes(Graphics graph) {
		graph.setColor(Color.WHITE);
	    graph.fillArc(this.x - 10, this.y - 10, 8, 8, 0, 2*PacmanConstants.halfAngle);
	    graph.fillArc(this.x + 2, this.y - 10, 8, 8, 0, 2*PacmanConstants.halfAngle);
	    
	    graph.setColor(Color.BLACK);
	    graph.drawArc(this.x - 10, this.y - 10, 8, 8, 0, 2*PacmanConstants.halfAngle);
	    graph.drawArc(this.x + 2, this.y - 10, 8, 8, 0, 2*PacmanConstants.halfAngle);
	    graph.fillArc(this.x - 8, this.y - 8, 4, 4, 0, 2*PacmanConstants.halfAngle);
	    graph.fillArc(this.x + 4, this.y - 8, 4, 4, 0, 2*PacmanConstants.halfAngle);
	}
	
	@Override
	public void paint(Graphics graph) {
		this.paintBody(graph);
		this.paintEyes(graph);
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
		
		this.AI.trigger();
		
		switch(this.direction) {
			case UP: 	this.y -= PacmanConstants.moveableSpeedPerFrame; 	break;
			case DOWN: 	this.y += PacmanConstants.moveableSpeedPerFrame; 	break;
			case LEFT:	this.x -= PacmanConstants.moveableSpeedPerFrame; 	break;
			case RIGHT:	this.x += PacmanConstants.moveableSpeedPerFrame; 	break;
		}
	}

}
