package pz.pacman.editor.engines;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import pz.pacman.editor.entities.Wall;
import pz.pacman.editor.mvc.view.PopupWindow;

public class IOEngine {
	
	public IOEngine() {

	}

	private String[] createCoordinatesArray(String data) {
		return data.substring(0, data.length()).split(", ");
	}

	private boolean addWallToCollection(String[] coordinates, LinkedList<Wall> walls) {
		boolean success = true;

		if (coordinates.length == 4) {
			try {
				walls.add(new Wall(Integer.parseInt(coordinates[0]), Integer
						.parseInt(coordinates[1]), Integer
						.parseInt(coordinates[2]), Integer
						.parseInt(coordinates[3])));
			} catch (NumberFormatException e) {
				Logger.getInstance().logException(e.toString(), "AddWallToCollection method, one of wall's coordinates is not of Integer format");
				success = false;
			}
		} else {
			success = false;
		}

		return success;
	}

	public boolean loadMapFromFile(String path, LinkedList<Wall> walls) {
		boolean loadStatus = true;
		Path filePath = Paths.get(path);

		try {
			BufferedReader reader = Files.newBufferedReader(filePath);
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] coordinatesArray = createCoordinatesArray(line);

				if (!addWallToCollection(coordinatesArray, walls)) {
					PopupWindow.show("Problem", "Data in map's file is corrupted. Cannot load map "+ path + ".");
					reader.close();
					walls.clear();
					return false;
				}
			}

			reader.close();
		} catch (IOException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			PopupWindow.showError("Problem", "I/O problem occurs. Cannot load map " + path + ". File not found.");
			loadStatus = false;
		} catch (NumberFormatException e) {
			PopupWindow.showError("Problem", "Number format problem occurs. Cannot load map. Data is corrupted.");
			loadStatus = false;
		}

		return loadStatus;
	}

	public boolean saveMapToFile(String path, LinkedList<Wall> walls) {
		boolean saveStatus = true;
		String dataToWrite = null;

		Path filePath = Paths.get(path);
		Charset charset = Charset.forName("US-ASCII");

		try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset)) {
			for (Wall x : walls) {
				dataToWrite = x.toString();
				writer.write(dataToWrite, 0, dataToWrite.length());
				writer.newLine();
			}

			writer.close();
		} catch (IOException e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
			saveStatus = false;
			PopupWindow.showError("Problem", "I/O problem occurs. Cannot save map " + path + ".");
		}
		
		return saveStatus;
	}
}
