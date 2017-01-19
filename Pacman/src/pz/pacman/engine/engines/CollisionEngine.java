package pz.pacman.engine.engines;

import java.awt.Rectangle;
import java.util.LinkedList;

import pz.pacman.constants.Direction;
import pz.pacman.engine.element.Element;
import pz.pacman.engine.element.dynamic.Ghost;
import pz.pacman.engine.element.dynamic.OvalCharacter;
import pz.pacman.engine.element.passive.Dot;
import pz.pacman.engine.element.passive.RectElement;

public class CollisionEngine {
	
	private static CollisionEngine instance;
	
	private GameDataEngine gameData;
	
	public static CollisionEngine getInstance() {
		if (instance == null) {
			instance = new CollisionEngine();
		}
		
		return instance;
	}
	
	private CollisionEngine() {
		this.gameData = null;
	}
	
	public void initialize(GameDataEngine gameData) {
		this.gameData = gameData;
	}
	
	private Rectangle translateRectangle(Rectangle rect, Direction direction, int step) {
		Rectangle newRect = new Rectangle(rect);
		
		switch (direction) {
			case UP: 	newRect.y -= step; break;
			case DOWN: 	newRect.y += step; break;
			case LEFT:	newRect.x -= step; break;
			case RIGHT:	newRect.x += step; break;
		}
		
		return newRect;
	}
	
	public LinkedList<Direction> getPossibleDirections(Rectangle rect) {	
		LinkedList<Direction> possibleDirections = new LinkedList<Direction>();
		
		for (Direction x : Direction.values()) {
			Rectangle tmpRect = translateRectangle(rect, x, 2);
			if (checkWallsCollision(tmpRect) == false) {
				possibleDirections.add(x);
			}
		}
		
		return possibleDirections;
	}
	
	public boolean checkWallsCollision(Rectangle rect) {
		LinkedList<Element> collection = new LinkedList<Element>();
		collection.addAll(this.gameData.getWalls());
		
		return isCollision(rect, collection);
	}
	
	public static boolean isCollision(Rectangle rect, LinkedList<Element> collection) {
		boolean isCollision = false;
		
		for (Element x : collection) {
			if (rect.intersects(x.getRectangle()) == true) {
				isCollision = true;
				break;
			}
		}
		
		return isCollision;
	}
	
	public static boolean isCollision(OvalCharacter dynamicElem, LinkedList<Element> collection) {
		boolean isCollision = false;
		
		for (Element x : collection) {
			if (dynamicElem.getRectangle().intersects(x.getRectangle()) == true) {
				isCollision = true;
				break;
			}
		}
		
		return isCollision;
	}
	
	public static boolean isCollision(OvalCharacter dynamicElem, RectElement staticElem) {
		return dynamicElem.getGridRectangle().intersects(staticElem.getGridRectangle());
	}
	
	public static boolean isCollision(OvalCharacter dynamicElem1, OvalCharacter dynamicElem2) {
		return dynamicElem1.getRectangle().intersects(dynamicElem2.getRectangle());
	}
	
	public static Element isCollision(Element elem1, LinkedList<? extends Element> collection) {
		Element elem = null;
		
		for (Element x : collection) {
			if (elem1.getRectangle().intersects(x.getRectangle())) {
				elem = x;
				break;
			}
		}
		
		return elem;
	}
	
	public void checkCollisions() {
		Dot collisionDot = null;
		collisionDot = (Dot) isCollision(gameData.getPacman(), gameData.getDots());
		
		if (collisionDot != null) {
			collisionWithDot(collisionDot);
		}
		
		Ghost collisionGhost = null;
		collisionGhost = (Ghost) isCollision(gameData.getPacman(), gameData.getGhosts());
		
		if (collisionGhost != null) {
			collisionWithGhost(collisionGhost);
		}
	}
	
	private void collisionWithDot(Dot collisionDot) {
		this.gameData.collision(collisionDot);
	}
	
	private void collisionWithGhost(Ghost collisionGhost) {
		this.gameData.collision(collisionGhost);
	}
	
}
