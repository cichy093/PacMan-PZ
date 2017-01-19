package pz.pacman.constants;

public enum Direction {
	LEFT, RIGHT, UP, DOWN;
	
	public static Direction getOpposite(Direction dir) {
		Direction opposite = dir;
		
		switch (dir) {
			case LEFT:	opposite = RIGHT;	break;
			case RIGHT: opposite = LEFT;	break;
			case UP:	opposite = DOWN;	break;
			case DOWN:	opposite = UP;		break;
		}
		
		return opposite;
	}
	
	public static Direction[] getOppositeAxis(Direction dir) {
		Direction[] opAxis = new Direction[2];
		
		switch (dir) {
			case LEFT:
			case RIGHT:
				opAxis[0] = UP;
				opAxis[1] = DOWN;
			break;
			
			case UP:
			case DOWN:
				opAxis[0] = LEFT;
				opAxis[1] = RIGHT;
			break;
		}
		
		return opAxis;
	}
}
