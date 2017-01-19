package pz.pacman.editor.engines;

import java.awt.Rectangle;
import java.util.LinkedList;

import pz.pacman.editor.constants.AppConstants;
import pz.pacman.editor.entities.Block;
import pz.pacman.editor.entities.Wall;

public class MapConverter {
	
	public static void wallToBlocks(Wall x, LinkedList<Block> blocks) {
		Rectangle wallRect = x.getRectangle();

		int col = (int) (wallRect.getX() / AppConstants.orgGridWidth);
		int row = (int) (wallRect.getY() / AppConstants.orgGridHeight);
		
		if (isSquare(wallRect)) {
			blocks.get(row * AppConstants.heightInGrid + col).setSelected(true);
		} 
		else {
			if (isHorizontal(wallRect)) {
				for (int i = 0; i < (int) (wallRect.getWidth() / AppConstants.orgGridWidth); i++) {
					blocks.get(row * AppConstants.heightInGrid + (col + i)).setSelected(true);
				}
			} 
			else {
				for (int i = 0; i < (int) (wallRect.getHeight() / AppConstants.orgGridHeight); i++) {
					blocks.get((row + i) * AppConstants.heightInGrid + col).setSelected(true);
				}
			}
		}
	}
	
	public static Wall blockToWall(Block x, LinkedList<Block> blocks, LinkedList<Integer> usedBlocks) {
		Wall retWall = null;
		
		int blocksCnt = 0;
			
		int col = (int) (x.getX() / AppConstants.gridWidth);
		int row = (int) (x.getY() / AppConstants.gridHeight);
			
		if (isWallHorizontal(x, blocks)) {
			for (int i = 0; i < AppConstants.widthInGrid - getColFromId(x.getId()); i++) {
				if (blocks.get(x.getId() + i).isSelected()) {
					blocksCnt++;
					usedBlocks.add(x.getId() + i);
				} 
				else {
					break;
				}
			}
				
			retWall = new Wall(col * AppConstants.orgGridWidth, row * AppConstants.orgGridHeight, blocksCnt * AppConstants.orgGridWidth, AppConstants.orgGridHeight);
		} 
		else if (isWallVertical(x, blocks)) {
			for (int i = 0; i < (AppConstants.heightInGrid - getRowFromId(x.getId())); i++) {
				if (blocks.get(x.getId() + (i * AppConstants.heightInGrid)).isSelected()) {
					blocksCnt++;
					usedBlocks.add(x.getId() + (i * AppConstants.heightInGrid));
				} 
				else {
					break;
				}
			}
			
			retWall = new Wall(col * AppConstants.orgGridWidth, row * AppConstants.orgGridHeight, AppConstants.orgGridWidth, blocksCnt * AppConstants.orgGridHeight);
		} 
		else {
			usedBlocks.add(x.getId());
			retWall = new Wall(col * AppConstants.orgGridWidth, row * AppConstants.orgGridHeight, AppConstants.orgGridWidth, AppConstants.orgGridHeight);
		}
	
		return retWall;
	}
	
	public static boolean isWallHorizontal(Block x, LinkedList<Block> blocks) {
		boolean isHorizontal = true;
		
		if (getColFromId(x.getId()) == 14) {
			isHorizontal = false;
		} 
		else {
			if (blocks.get(x.getId() + 1).isSelected()) {
				isHorizontal = true;
			} 
			else {
				isHorizontal = false;
			}
		}
		
		return isHorizontal;
	}
	
	public static boolean isWallVertical(Block x, LinkedList<Block> blocks) {
		boolean isVertical = true;
		
		if (getRowFromId(x.getId()) == 14) {
			isVertical = false;
		} 
		else {
			if (blocks.get(x.getId() + AppConstants.heightInGrid).isSelected()) {
				isVertical = true;
			} 
			else {
				isVertical = false;
			}
		}
		
		return isVertical;
	}
	
	public static int getColFromId(int id) {
		return id % AppConstants.widthInGrid;
	}
	
	public static int getRowFromId(int id) {
		return (int) (id / AppConstants.heightInGrid);
	}
	
	public static boolean isBlockAlreadyDone(Block x, LinkedList<Integer> usedBlocks) {
		boolean isDone = false;
		
		for (Integer i : usedBlocks) {
			if (x.getId() == i) {
				isDone = true;
				break;
			}
		}
		
		return isDone;
	}
	
	public static boolean isSquare(Rectangle x) {
		return (x.getWidth() == x.getHeight()) ? true : false;
	}
	
	public static boolean isHorizontal(Rectangle x) {
		return (x.getWidth() > x.getHeight()) ? true : false;
	}
}