package pz.pacman.mvc.manager;

import pz.pacman.engine.interfaces.IManageable;

public class MVCPair<T> {

	private T type;
	private IManageable ref;
	
	public MVCPair(T type, IManageable ref) {
		this.type = type;
		this.ref = ref;
	}

	public T getType() {
		return type;
	}

	public IManageable getRef() {
		return ref;
	}
}
