package pz.pacman.mvc.manager;

import java.util.LinkedList;

import pz.pacman.engine.interfaces.IManageable;
import pz.pacman.engine.singleton.Logger;

public class MVCManager {

	private static MVCManager instance;
	
	@SuppressWarnings("rawtypes")
	private LinkedList<MVCPair> controllers;
	
	public static MVCManager getInstance() {
		if (instance == null) {
			Logger.getInstance().logInformation("Creating MVCManager singleton object");
			instance = new MVCManager();
		}
		
		return instance;
	}
	
	@SuppressWarnings("rawtypes")
	public void initialize() {
		this.controllers = new LinkedList<MVCPair>();
	}
	
	@SuppressWarnings("rawtypes")
	public <T> void freeze(T type, IManageable ref, boolean removePrev) {
		Logger.getInstance().logInformation("Freezing "+ type +" in MVCManager");
		boolean isInList = false;
		
		for (MVCPair x : controllers) {
			if (x.getType() == type) {
				if (removePrev) {
					controllers.remove(x);
					Logger.getInstance().logWarning("Removing "+ x.getType() +" object from MVCManager");
				}
				else {
					isInList = true;
				}
				
				break;
			}
		}

		if (!isInList) {
			controllers.add(new MVCPair<T>(type, ref));
			Logger.getInstance().logInformation(type +" object freezed successfully");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public <T> boolean restore(T type) {
		Logger.getInstance().logInformation("Try to restore "+ type +" object from MVCManager");
		boolean isInList = false;
		MVCPair elem = null;
		
		for (MVCPair x : controllers) {
			if (x.getType() == type) {
				Logger.getInstance().logInformation("Searched object found: "+ x.getType());
				elem = x;
				isInList = true;
				break;
			}
		}
		
		if (isInList) {
			Logger.getInstance().logInformation("Restoring Controller (View & Model included) of type "+ type);
			elem.getRef().restore();
			return true;
		} else {
			Logger.getInstance().logWarning("Cannot restore "+ type +" object from MVCManager - couldn't find in collection of frozen controllers");
		}
		
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	public <T> boolean destroy(T type) {
		Logger.getInstance().logInformation("Try to destroy "+ type +" object in MVCManager");
		boolean isInList = false;
		MVCPair elem = null;
		
		for (MVCPair x : controllers) {
			if (x.getType() == type) {
				Logger.getInstance().logInformation("Found required object in MVCManager");
				elem = x;
				isInList = true;
				break;
			}
		}
		
		if (isInList) {
			Logger.getInstance().logWarning("Removing object of type "+ type +" from MVCManager collection");
			controllers.remove(elem);
			return true;
		}
		
		return false;
	}
}
