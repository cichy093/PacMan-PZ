package pz.pacman.engine.engines;

import java.util.HashMap;
import java.util.Random;

import pz.pacman.constants.Direction;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.element.dynamic.OvalCharacter;

public class AIEngine {
	
	private int separatorInSteps;
	private int stepsDone;
	private Random randGen;
	private HashMap<Direction, Integer> dirs;
	private OvalCharacter parent;
	
	public AIEngine(OvalCharacter parent) {
		this.randGen = new Random();
		this.separatorInSteps = randGen.nextInt(PacmanConstants.maxSepInSteps);
		this.stepsDone = 0;
		this.parent = parent;
		
		this.dirs = new HashMap<Direction, Integer>();
		dirs.put(Direction.UP, 0);
		dirs.put(Direction.DOWN, 0);
		dirs.put(Direction.LEFT, 0);
		dirs.put(Direction.RIGHT, 0);
	}
	
	public void trigger() {
		this.stepsDone += PacmanConstants.moveableSpeedPerFrame;
		
		if (stepsDone >= separatorInSteps) {
			this.shuffle();
			this.stepsDone = 0;
			parent.setRequiredDirection(this.shuffleDirection(parent.getDirection()));
		}
	}
	
	protected void shuffle() {
		separatorInSteps = randGen.nextInt(PacmanConstants.maxSepInSteps);
	}
	
	protected Direction shuffleDirection(Direction currentDirection) {
		Direction[] oppositeOrientationDirs = Direction.getOppositeAxis(currentDirection);
		
		for (Direction x : oppositeOrientationDirs) {
			this.dirs.put(x, 35);
		}
		
		this.dirs.put(currentDirection, 20);
		this.dirs.put(Direction.getOpposite(currentDirection), 10);
		
		return oppositeOrientationDirs[randGen.nextInt(oppositeOrientationDirs.length)];
	}
}
