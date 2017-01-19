package pz.pacman.engine.engines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom2.JDOMException;

import pz.pacman.engine.element.passive.Wall;
import pz.pacman.engine.entities.SaveData;
import pz.pacman.engine.singleton.Logger;
import pz.pacman.engine.xml.SaveCreator;
import pz.pacman.engine.xml.SaveParser;
import pz.pacman.mvc.view.PopupWindow;

public class IOEngine {

	public IOEngine() {

	}
	
	private String getLevelFilename(int levelNumber) {
		String filename = "/levels/level-"+ levelNumber +".txt";
		return filename;
	}
	
	private String[] createCoordinatesArray(String data) {
		return data.substring(0, data.length()).split(", ");
	}
	
	private boolean addWallToCollection(String[] coordinates, LinkedList<Wall> walls) {
		boolean success = true;
		
		if (coordinates.length == 4) {
			try {
				walls.add(new Wall(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]), Integer.parseInt(coordinates[2]), Integer.parseInt(coordinates[3])));
			} catch (NumberFormatException e) {
				Logger.getInstance().logException(e.toString(), e.getMessage());
				return false;
			}
		} else {
			success = false;
		}
		
		return success;
	}
	
	public boolean loadLevelFromFile(int levelNumber, LinkedList<Wall> walls) {
		boolean loadStatus = true;
		Logger.getInstance().logInformation("Loading level from file "+ getLevelFilename(levelNumber));
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(getLevelFilename(levelNumber))));
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] coordinatesArray = createCoordinatesArray(line);
				
				if (!addWallToCollection(coordinatesArray, walls) == true) {
					PopupWindow.show("Problem", "Data in level's file is corrupted. Cannot load level "+ levelNumber +".");
					reader.close();
					return false;
				}
			}
			
			reader.close();
		} catch (IOException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.show("Problem", "IOException occurs. Cannot load level "+ levelNumber +". File not found.");
			loadStatus = false;
		} catch (NumberFormatException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.show("Problem", "NumberFormatException occurs. Cannot load level "+ levelNumber +". Data is corrupted.");
			loadStatus = false;
		}
		
		return loadStatus;
	}
	
	public boolean loadBonusMap(String path, LinkedList<Wall> walls) {
		boolean loadStatus = true;
		Logger.getInstance().logInformation("Loading map from file "+ path);
		Path filePath = Paths.get(path);

		try {
			BufferedReader reader = Files.newBufferedReader(filePath);
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] coordinatesArray = createCoordinatesArray(line);
				
				if (!addWallToCollection(coordinatesArray, walls) == true) {
					PopupWindow.show("Problem", "Data in level's file is corrupted. Cannot load map "+ path +".");
					reader.close();
					return false;
				}
			}
			
			reader.close();
		} catch (IOException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.show("Problem", "IOException occurs. Cannot load map "+ path +". File not found.");
			loadStatus = false;
		} catch (NumberFormatException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.show("Problem", "NumberFormatException occurs. Cannot load map "+ path +". Data is corrupted.");
			loadStatus = false;
		}
		
		return loadStatus;
	}

	public void saveGameData(String path, SaveData data) {
		SaveCreator builder = new SaveCreator(path, data);
		Logger.getInstance().logInformation("Saving game data to XML file "+ path);
		
		try {
			builder.build();
		} catch (ParserConfigurationException | TransformerException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.show("Unexpected behavior", "Cannot save game state file.\nData won't be save!");
		}
	}
	
	public SaveData loadGameData(String path) {
		SaveData data = null;
		SaveParser parser = new SaveParser(path);
		Logger.getInstance().logInformation("Loading game data from XML file "+ path);
		
		try {
			parser.parse();
			data = parser.getSaveData();
		} catch (JDOMException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data save file.\nData is corrupted or file doesn't exist.");
		} catch (IOException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data save file.\nFile doesn't exist.");
		} catch (NumberFormatException e) {
			PopupWindow.show("Unexpected behavior", "Cannot load data save file.\nData is corrupted. Wrong data format.");
		} catch (Exception e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
		}
		
		return data;
	}
}
