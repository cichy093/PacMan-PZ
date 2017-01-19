package pz.pacman.engine.entities;

import java.util.LinkedList;

import pz.pacman.engine.element.dynamic.Ghost;
import pz.pacman.engine.element.dynamic.Pacman;
import pz.pacman.engine.element.passive.Dot;

public class SaveData {
	
	private int level;
	private int time;
	private int points;
	private int totalTime;
	private int totalPoints;
	private int lifes;
	
	private Pacman pacman;
	private LinkedList<Ghost> ghosts;
	private LinkedList<Dot> dots;
	
	public SaveData(int level, int time, int points, int totalTime, int totalPoints, int lifes, Pacman pacman, LinkedList<Ghost> ghosts, LinkedList<Dot> dots) {
		this.level = level;
		this.time = time;
		this.points = points;
		this.totalTime = totalTime;
		this.totalPoints = totalPoints;
		this.lifes = lifes;
		this.pacman = pacman;
		this.ghosts = ghosts;
		this.dots = dots;
	}
	
	public SaveData() {
		this.level = 1;
		this.time = 0;
		this.points = 0;
		this.lifes = 3;
		
		this.pacman = null;
		this.ghosts = new LinkedList<Ghost>();
		this.dots = new LinkedList<Dot>();
	}

	public Pacman getPacman() {
		return pacman;
	}

	public LinkedList<Ghost> getGhosts() {
		return ghosts;
	}

	public LinkedList<Dot> getDots() {
		return dots;
	}

	public int getTime() {
		return time;
	}

	public int getPoints() {
		return points;
	}
	
	public int getTotalTime() {
		return totalTime;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public int getLifes() {
		return lifes;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setTotalTime(int time) {
		this.totalTime = time;
	}

	public void setTotalPoints(int points) {
		this.totalPoints = points;
	}
	
	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}

	public void addGhost(Ghost x) {
		this.ghosts.add(x);
	}

	public void addDot(Dot x) {
		this.dots.add(x);
	}
}
