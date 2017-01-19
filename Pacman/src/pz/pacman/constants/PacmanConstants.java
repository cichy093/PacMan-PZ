package pz.pacman.constants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class PacmanConstants {
	
	// Timer
	public final static int			mainTimerInterval = 30;
	public final static int			gameTimerInterval = 100;
	public final static int			bonusDotTimerInterval = 2000;
	public final static int 		reversePacmanTimerInterval = 10000;
	
	// Speed
	public final static int			moveableSpeedPerFrame = 2;
	
	// Points
	public final static int			pointPerNormalDot = 10;
	public final static int			pointPerBonusDot = 20;
	public final static int			pointPerGhost = 50;
	
	// Titles
	public final static String		applicationTitle = "Pacman";
	
	// Window
	public final static int			windowWidth = 750;
	public final static int			windowHeight = 790;
	
	// Color Schema
	public final static Color 		backgroundColor = Color.BLACK;
	public final static Color		ingameBackgroundColor = Color.BLACK;
	
	public final static Color 		activeField = new Color(8, 253, 254, 255);
	
	public final static Color 		inactiveFieldInside = new Color(245, 242, 68, 255);
	public final static Color		inactiveFieldOutside = Color.BLACK;
	
	// Font & Spacing
	public final static String		fontName = "Helvetica";
	
	public final static int			fontSize = 50;
	public final static int			spaceBetweenTexts = 75;
	
	public final static int			fontStyle = Font.BOLD;
	
	public final static Font 		defaultFont = new Font(PacmanConstants.fontName, PacmanConstants.fontStyle, PacmanConstants.fontSize);

	// Strokes
	public final static float		activeTextStrokeWidth = 1.2f;
	public final static float		inactiveTextStrokeWidth = 1.1f;

	public final static BasicStroke	activeTextStroke = new BasicStroke(PacmanConstants.activeTextStrokeWidth);
	public final static BasicStroke	inactiveTextStroke = new BasicStroke(PacmanConstants.inactiveTextStrokeWidth);
	
	// Menu Strings
	public final static String[] 	mainMenuTexts = {"Start New Game", "Play Bonus Level", "Load Game", "Top Scores", "Exit"};
	public final static String[] 	ingameMenuTexts = {"Resume", "Save Game", "End Game"};
	public final static String[]	levelCompleteTexts = {"", "", "", "Press ENTER to continue"};
	public final static String[]	gameOverTexts = {"REST IN PEACE", "", "", "", "Press ENTER to continue"};
	public final static String[]	gameCompleteTexts = {"Total Time: ", "Total Points: ", "", "Press ENTER to continue"};
	
	// Top Scores Default Strings
	public final static String		topScoresFilePath = ".\\topscores.xml";
	public final static String[] 	topScoresTexts = {"---", "XX:XX:XX"};
	
	// Menu Strings Positions
	public final static int 		mainMenuCnt = 5;
	public final static int		 	ingameMenuCnt = 3;
	public final static int		 	topScoresCnt = 5;
	public final static int		 	levelCompleteCnt = 4;
	public final static int 		gameOverCnt = 5;
	public final static int 		gameCompleteCnt = 4;
	
	// Pacman
	public final static int	 		fullAngle = 360;
	public final static int 		pacmanRadius = 15;
	public final static int			pacmanEyeOuterRadius = 4;
	public final static int 		pacmanEyeInnerRadius = 2;
	public final static Color		pacmanColor = Color.YELLOW;
	
	// Ghosts
	public final static Color		ghostsColorsArray[] = {Color.ORANGE, Color.CYAN, Color.PINK, Color.RED};
	public final static int 		ghostRadiusWidth = 15;
	public final static int 		ghostRadiusHeight = 25;
	public final static int 		halfAngle = 180;
	
	// Wall
	
	// Dots
	public final static	Color 		dotColor = Color.ORANGE;
	public final static	Color 		dotBonusColor = Color.GREEN;
	public final static int 		dotRadius = 10;
	
	// Grid
	public final static int			gridWidth = 50;
	public final static int			gridHeight = 50;
	public final static int			widthInGrid = 15;
	public final static int			heightInGrid = 15;
	
	// Levels
	public final static int			numberOfBuildInLevels = 3;
	
	// Others
	public final static int			maxDigitsInNickname = 7;
	
	// Score Bar
	public final static int			scoreBarYCoord = 760;
	
	// Random Generator
	public final static int			maxSepInSteps = 100;
}
