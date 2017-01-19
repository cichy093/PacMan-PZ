package pz.pacman.editor.mvc.model;

import java.awt.Rectangle;
import java.io.File;
import java.util.LinkedList;

import pz.pacman.editor.constants.AppConstants;
import pz.pacman.editor.constants.Validation;
import pz.pacman.editor.engines.IOEngine;
import pz.pacman.editor.engines.Logger;
import pz.pacman.editor.engines.MapConverter;
import pz.pacman.editor.entities.Block;
import pz.pacman.editor.entities.Wall;



public class MainWindowModel implements BaseModel {

	private LinkedList<Block> blocks;
	private String currentMapPath;
	
	public MainWindowModel() {
		this.createBlocks();
		this.currentMapPath = new String();
	}
	
	public LinkedList<Block> getBlocks() {
		return this.blocks;
	}
	
	public String getMapPath() {
		return currentMapPath;
	}
	
	public void setMapPath(String path) {
		this.currentMapPath = path;
	}
	
	private void createBlocks() {
		this.blocks = new LinkedList<Block>();
		
		for (int row = 0; row < AppConstants.widthInGrid; row++) {
			for (int col = 0; col < AppConstants.heightInGrid; col++) {
				this.blocks.add(new Block(AppConstants.widthInGrid * row + col, col*AppConstants.gridWidth, row*AppConstants.gridHeight));
			}
		}
	}
	
	public void saveToFile(File file) {
		String path = file.getAbsolutePath();
		if (path.indexOf(".map", path.lastIndexOf("/")) < 0) {
			path += ".map";
		}
		
		try {
			this.saveToFile(path);
		} catch (Exception e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
		}
	}
	
	public void saveToFile(String path) {
		if (path.indexOf(".map", path.lastIndexOf("/")) < 0) {
			path += ".map";
		}
		
		try {
			LinkedList<Wall> walls = prepareWallsToSaveOperation(path);
			
			IOEngine IO = new IOEngine();
			System.out.println(walls.size());
			IO.saveMapToFile(path, walls);
			
		} catch (Exception e) {
			Logger.getInstance().logException(e.toString(), e.getMessage());
		}
	}
	
	private LinkedList<Wall> prepareWallsToSaveOperation(String path) {
		LinkedList<Wall> walls = new LinkedList<Wall>();
		LinkedList<Integer> usedBlocks = new LinkedList<Integer>();
		for (Block x : this.blocks) {
			if (x.isSelected()) {
				if (!MapConverter.isBlockAlreadyDone(x, usedBlocks)) {
					Wall retWall = MapConverter.blockToWall(x, blocks, usedBlocks);
					if (retWall != null) {
						walls.add(retWall);
					}
				}	
			}
		}
		
		return walls;
	}
	
	public boolean isCollisionWithBlocks(Rectangle rect, boolean isAddWallMode) {
		for (Block x : this.blocks) {
			if (rect.intersects(x.getRect())) {
				x.setSelected(isAddWallMode);
				if (isAddWallMode) {
					Logger.getInstance().logInformation("Added block - id "+ x.getId());
				} else {
					Logger.getInstance().logInformation("Removed block - id "+ x.getId());
				}
				return true;
			}
		}
		
		return false;
	}
	
	public Validation isEnoughFreeBlocks() {
		int cnt = 0;
		
		for (int i = 0; i < AppConstants.widthInGrid * AppConstants.heightInGrid; i++) {
			if (!this.blocks.get(i).isSelected()) {
				cnt++;
			}
		}
		
		return (cnt < AppConstants.minimumNumberOfFreeBlocks) ? Validation.NOT_ENOUGH_SPACE : Validation.VALID;
	}
	
	public Validation isForbiddenSquare() {
		boolean isValid = true;
		
		for (int row = 0; row < (AppConstants.widthInGrid - 1); row += 2) {
			for (int col = 0; col < (AppConstants.heightInGrid - 2); col++) {
				if (!isSquereValid(this.blocks.get(row * AppConstants.widthInGrid + col))) {
					isValid = false;
					break;
				}
			}
		}
		
		return (isValid == false) ? Validation.SQUARE_2X2 : Validation.VALID;
	}
	
	private boolean isSquereValid(Block x) {
		boolean isSelectedInside = false;
		
		for (int id : this.getNeighborsAndMeIds(x)) {
			if (this.blocks.get(id).isSelected()) {
				isSelectedInside = true;
				break;
			}
		}
		
		return isSelectedInside;
	}
	
	private int[] getNeighborsAndMeIds(Block x) {
		int[] ids = new int[4];
		
		ids[0] = x.getId();
		ids[1] = x.getId() + 1;
		ids[2] = x.getId() + 15;
		ids[3] = x.getId() + 16;
		
		return ids;
	}

	public void clear() {
		Logger.getInstance().logInformation("Clearing selected blocks");
		for (Block x : this.blocks) {
			x.setSelected(false);
		}
	}

	public void loadMapFromFile(File selectedFile) {
		Logger.getInstance().logInformation("loadMapFromFile method - begin");
		
		IOEngine IO = new IOEngine();
		LinkedList<Wall> walls = new LinkedList<Wall>();
		Logger.getInstance().logInformation("Converting walls to blocks");
		if (IO.loadMapFromFile(selectedFile.getAbsolutePath(), walls)) {
			for (Wall x : walls) {
				this.convertWallToBlocks(x);
			}
		}
		
		Logger.getInstance().logInformation("loadMapFromFile method - end");
	}
	
	private void convertWallToBlocks(Wall x) {
		MapConverter.wallToBlocks(x, this.blocks);
	}
}
