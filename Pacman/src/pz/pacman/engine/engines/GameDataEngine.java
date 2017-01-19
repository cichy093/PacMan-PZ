package pz.pacman.engine.engines;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom2.JDOMException;

import pz.pacman.constants.EngineEventType;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.constants.SchedulerEventType;
import pz.pacman.engine.element.Element;
import pz.pacman.engine.element.dynamic.Ghost;
import pz.pacman.engine.element.dynamic.Pacman;
import pz.pacman.engine.element.passive.Dot;
import pz.pacman.engine.element.passive.Wall;
import pz.pacman.engine.entities.SaveData;
import pz.pacman.engine.entities.TopScorerData;
import pz.pacman.engine.events.EngineEvent;
import pz.pacman.engine.events.SchedulerEvent;
import pz.pacman.engine.interfaces.IMoveable;
import pz.pacman.engine.interfaces.IPaintable;
import pz.pacman.engine.listeners.EngineListener;
import pz.pacman.engine.listeners.SchedulerListener;
import pz.pacman.engine.singleton.Engine;
import pz.pacman.engine.singleton.Logger;
import pz.pacman.engine.xml.TopScoresCreator;
import pz.pacman.engine.xml.TopScoresParser;
import pz.pacman.mvc.view.PopupWindow;

public class GameDataEngine {
	
	private EngineListener engineListener;
	private SchedulerListener schedulerListener;
	
	private LinkedList<TopScorerData> topScorers;
	
	private Pacman pacman;
	private LinkedList<Ghost> ghosts;
	private LinkedList<Wall> walls;
	private LinkedList<Dot> dots;
	
	private int time;
	private int totalTime;
	private int totalPoints;
	private int points;
	private int lifes;
	
	private int level; 
	
	private float bonusDotProbability;

	public GameDataEngine() {
		this.engineListener = null;
		this.schedulerListener = null;
		this.topScorers = new LinkedList<TopScorerData>();
		
		this.pacman = null;

		this.ghosts = new LinkedList<Ghost>();
		this.walls = new LinkedList<Wall>();
		this.dots = new LinkedList<Dot>();

		this.time = 0;
		this.totalTime = 0;
		this.totalPoints = 0;
		this.points = 0;
		this.lifes = 0;
		this.level = 1;
		this.bonusDotProbability = 0.0f;
		
		this.loadTopScorers();
	}
	
	public void addListener(EngineListener listener) {
		this.engineListener = listener;
	}
	
	public void addListener(SchedulerListener listener) {
		this.schedulerListener = listener;
	}
	
	private void notifyListener(EngineEventType type) {
		EngineEvent e = new EngineEvent(type);
		this.engineListener.OnEvent(e);
	}
	
	private void notifyListener(SchedulerEventType type) {
		SchedulerEvent e = new SchedulerEvent(type);
		this.schedulerListener.OnEvent(e);
	}

	private void clearResources() {
		ghosts.clear();
		walls.clear();
		dots.clear();
	}

	private void resetData() {
		clearResources();

		pacman = new Pacman(0, 0, PacmanConstants.pacmanRadius * 2, PacmanConstants.pacmanRadius * 2);

		this.time = 0;
		this.totalTime = 0;
		this.totalPoints = 0;
		this.points = 0;
		this.lifes = 3;
		this.level = 1;
		this.bonusDotProbability = 0.0f;
	}

	private void fillTopScorersWithDefault() {
		for (int i = 0; i < PacmanConstants.topScoresCnt; i++) {
			topScorers.add(new TopScorerData("---", 0, 0));
		}
	}
	
	private void loadTopScorers() {
		TopScoresParser parser = new TopScoresParser(PacmanConstants.topScoresFilePath);
		
		try {
			parser.parse();
			this.topScorers = parser.getTopScorers();
		} catch (JDOMException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data from topscores.xml file.\nData is corrupted or file doesn't exist.");
		} catch (IOException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data from topscores.xml file.\nFile doesn't exist.");
		} catch (NumberFormatException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data from topscores.xml file.\nData is corrupted. Wrong data format.");
		}
		
		if (this.topScorers.size() == 0) {
			this.fillTopScorersWithDefault();
		}
	}
	
	private void updateTopScorersFile() {
		TopScoresCreator builder = new TopScoresCreator(PacmanConstants.topScoresFilePath, this.topScorers);
		
		try {
			builder.build();
		} catch (ParserConfigurationException | TransformerException e) {
			PopupWindow.show("Unexpected behavior", "Cannot save data to topscores.xml file.\nData won't be save!");
		}
	}
	
	private void loadLevel(int level) {
		IOEngine IO = new IOEngine();
		IO.loadLevelFromFile(level, walls);
	}

	private void addBoundaryWalls() {
		Rectangle[] boundaryWalls = new Rectangle[4];
		
		boundaryWalls[0] = new Rectangle(-50, -50, 850, 50);
		boundaryWalls[1] = new Rectangle(-50, -50, 50, 850);
		boundaryWalls[2] = new Rectangle(750, -50, 50, 850);
		boundaryWalls[3] = new Rectangle(-50, 750, 850, 50);
		
		for (Rectangle x : boundaryWalls) {
			this.walls.add(new Wall(x));
		}
	}

	private Point calcDotXY(int col, int row) {
		return new Point(col*PacmanConstants.gridWidth + PacmanConstants.gridWidth/2, row*PacmanConstants.gridHeight + PacmanConstants.gridHeight/2);
	}
	
	private void createDotsGrid() {
		for (int y = 0; y < PacmanConstants.heightInGrid; y++) {
			for (int x = 0; x < PacmanConstants.widthInGrid; x++) {
				
				Rectangle dotRect = new Rectangle(x*PacmanConstants.gridWidth, y*PacmanConstants.gridHeight, PacmanConstants.gridWidth, PacmanConstants.gridHeight);
				
				boolean isCollision = false;
				
				for (Wall w : walls) {
					if (w.getRectangle().intersects(dotRect)) {
						isCollision = true;
						break;						
					}
				}
				
				if (isCollision == false) {
					Point XYCoords = calcDotXY(x, y);
					dots.add(new Dot((int)XYCoords.getX(), (int)XYCoords.getY(), PacmanConstants.dotRadius, PacmanConstants.dotRadius));
				}
			}
		}
	}
	
	private void createGhosts() {
		for (int i = 0; i < PacmanConstants.ghostsColorsArray.length; i++) {
			Ghost ghost = new Ghost(0, 0, 2*PacmanConstants.ghostRadiusWidth, 2*PacmanConstants.ghostRadiusWidth);
			ghost.setColor(PacmanConstants.ghostsColorsArray[i]);
			ghosts.add(ghost);
		}
	}

	private void spawnGhosts() {
		Random randomGenerator = new Random();
		Dot chosenDot = null;
		
		for (Ghost x : ghosts) {
			chosenDot = dots.get(randomGenerator.nextInt(dots.size() - 1) + 1);
			x.setX(chosenDot.getX());
			x.setY(chosenDot.getY());
		}
	}
	
	private void spawnElement(Element x) {
		Random randomGenerator = new Random();
		Dot chosenDot = dots.get(randomGenerator.nextInt(dots.size()));
		
		x.setX(chosenDot.getX());
		x.setY(chosenDot.getY());
	}
	
	private void spawnPacman() {
		Random randomGenerator = new Random();
		Dot chosenDot = dots.get(randomGenerator.nextInt(dots.size() - 1) + 1);
		pacman.setX(chosenDot.getX());
		pacman.setY(chosenDot.getY());
		dots.remove(chosenDot);
	}
	
	private void spawnMovableElements() {
		spawnGhosts();
		spawnPacman();
	}
	
	public void createNewGame() {
		resetData();
		loadLevel(getLevel());
		addBoundaryWalls();
		createDotsGrid();
		createGhosts();
		spawnMovableElements();
	}
	
	public void createNextLevel() {
		clearResources();
		pacman = new Pacman(0, 0, PacmanConstants.pacmanRadius * 2, PacmanConstants.pacmanRadius * 2);
		
		loadLevel(getLevel());
		addBoundaryWalls();
		createDotsGrid();
		createGhosts();
		spawnMovableElements();
	}
	
	public int getTime() {
		return time;
	}
	
	public void addTime() {
		this.time += PacmanConstants.gameTimerInterval;
		this.totalTime += PacmanConstants.gameTimerInterval;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int points) {
		this.totalPoints = points;
	}
	
	public void addTotalPoints(int points) {
		this.totalPoints += points;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}
	
	public void decLifes() {
		this.lifes--;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void incLevel() {
		this.level++;
	}
	
	public Pacman getPacman() {
		return pacman;
	}

	public LinkedList<Ghost> getGhosts() {
		return ghosts;
	}

	public LinkedList<Wall> getWalls() {
		return walls;
	}

	public LinkedList<Dot> getDots() {
		return dots;
	}

	public LinkedList<IPaintable> getPaintableElements() {
		LinkedList<IPaintable> paintableElements = new LinkedList<IPaintable>();
		paintableElements.addAll(walls);
		paintableElements.addAll(dots);
		paintableElements.addAll(ghosts);
		paintableElements.add(this.pacman);
		
		return paintableElements;
	}

	public LinkedList<IMoveable> getMoveableElements() {
		LinkedList<IMoveable> moveableElements = new LinkedList<IMoveable>();
		moveableElements.addAll(ghosts);
		moveableElements.add(pacman);
		
		return moveableElements;
	}
	
	public LinkedList<TopScorerData> getTopScores() {
		return this.topScorers;
	}
	
	private void collision(Dot x) {
		boolean isBonus = x.isBonus();
		this.addPoints(isBonus ? PacmanConstants.pointPerBonusDot : PacmanConstants.pointPerNormalDot);
		this.addTotalPoints(isBonus ? PacmanConstants.pointPerBonusDot : PacmanConstants.pointPerNormalDot);
		this.dots.remove(x);

		if (dots.size() > 0) {
			if (isBonus) {
				this.pacman.setReverse(true);
				for (Ghost y : ghosts)
					y.setReversed(true);
				
				this.notifyListener(SchedulerEventType.START_REVERSE_MODE);
			}
		} else {
			if (this.level > 0) {
				this.incLevel();
				this.notifyListener((this.isLevelCorrect()) ? EngineEventType.LEVEL_COMPLETE : EngineEventType.GAME_COMPLETE);				
			} else {
				this.notifyListener(EngineEventType.GAME_COMPLETE);
			}
		}
	}

	private boolean isLevelCorrect() {
		return (this.level > PacmanConstants.numberOfBuildInLevels) ? false : true;
	}

	private void collision(Ghost x) {
		boolean isReversed = this.pacman.isReverse();
		
		if (isReversed) {
			this.addPoints(PacmanConstants.pointPerGhost);
			this.addTotalPoints(PacmanConstants.pointPerGhost);
			this.spawnElement(x);
		} 
		else {
			this.decLifes();
			
			if (this.getLifes() > 0)
				this.spawnElement(this.pacman);
			else
				this.notifyListener(EngineEventType.GAME_OVER);
		}
	}
	
	public void collision(Object collisionObject) {
		if(collisionObject instanceof Dot) {		
			collision((Dot) collisionObject);
		} 
		else if (collisionObject instanceof Ghost) {
			collision((Ghost) collisionObject);
		}
	}

	public void removeBonusDot() {
		for (Dot x : dots) {		
			if (x.isBonus()) {
				x.setBonus(false);
				break;
			}
		}
	}
	
	public void revertGameMode() {
		if (this.pacman.isReverse()) {
			for (Ghost x : ghosts)
				x.setReversed(false);
			this.pacman.setReverse(false);
		}
	}

	public void destroyData() {
		this.clearResources();		
		pacman = null;
	}

	public boolean isTopScore() {
		boolean isTopScore = false;
		
		for (TopScorerData x : this.topScorers) {
			if ((x.getPoints() < this.totalPoints) || (x.getPoints() == this.getTotalPoints() && x.getTime() < this.totalTime)) {
				isTopScore = true;
				break;
			}
		}
		
		return isTopScore;
	}
	
	public void addTopScore(String nickname) {
		boolean isTopScore = false;
		TopScorerData elemToSwap = null;
		
		for (TopScorerData x : this.topScorers) {
			if ((x.getPoints() < this.totalPoints) || (x.getPoints() == this.getTotalPoints() && x.getTime() < this.totalTime)) {
				isTopScore = true;
				elemToSwap = x;
				break;
			}
		}
		
		if (isTopScore) {
			this.topScorers.add(this.topScorers.indexOf(elemToSwap), new TopScorerData(nickname, this.getTotalPoints(), this.getTotalTime()));
			this.topScorers.removeLast();
			this.updateTopScorersFile();
		}
	}

	private boolean isBonusDotActive() {
		boolean isBonusDot = false;
		for (Dot x : this.dots) {
			if (x.isBonus()) {
				isBonusDot = true;
				break;
			}
		}
		return isBonusDot;
	}
	
	public void shuffleDot() {
		boolean isBonusDot = this.isBonusDotActive();
		
		if (isBonusDot == false) {
			this.bonusDotProbability += 0.001;
			Random gen = new Random();
			if (this.bonusDotProbability >= gen.nextFloat()) {
				this.dots.get(gen.nextInt(this.dots.size())).setBonus(true);
			}
		}
	}

	public int getDotsLeft() {
		return this.dots.size();
	}
	
	public void saveGameState(String path) {
		Logger.getInstance().logInformation("Saving preparing present game state "+ path);
		
		SaveData data = new SaveData(this.level, this.time, this.points, this.totalTime, this.totalPoints, this.lifes, this.pacman, this.ghosts, this.dots);
		IOEngine IO = new IOEngine();
		IO.saveGameData(path, data);
	}
	
	public void loadGameState(String path) {
		Logger.getInstance().logInformation("Load game state from file "+ path);
		this.resetData();
		
		IOEngine IO = new IOEngine();
		SaveData data = IO.loadGameData(path);
		this.mergeStates(data);
		
		this.loadLevel(this.level);
		this.addBoundaryWalls();
		Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.RESUME_SAVED_GAME));
	}
	
	protected void mergeStates(SaveData data) {
		Logger.getInstance().logInformation("Merging game states - present and saved");
		
		this.time = data.getTime();
		this.totalTime = data.getTotalTime();
		this.totalPoints = data.getTotalPoints();
		this.lifes = data.getLifes();
		this.points = data.getPoints();
		this.level = data.getLevel();
		this.pacman  = data.getPacman();
		this.dots.addAll(data.getDots());
		this.ghosts.addAll(data.getGhosts());
	}

	public void loadBonusMap(String path) {
		Logger.getInstance().logInformation("Load bonus map from file "+ path);
		this.resetData();
		
		IOEngine IO = new IOEngine();
		if (IO.loadBonusMap(path, walls)) {
			this.level = 0;
			this.addBoundaryWalls();
			this.createDotsGrid();
			this.createGhosts();
			this.spawnMovableElements();
			
			Engine.getInstance().OnEvent(new EngineEvent(EngineEventType.RESUME_SAVED_GAME));
		}
		else {
			PopupWindow.show("Problem occured", "Bonus map won't be load. Some kind of serious problem occured.");
		}
	}
}