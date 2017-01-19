package pz.pacman.mvc.controller;

import pz.pacman.mvc.model.BaseModel;
import pz.pacman.mvc.view.BaseView;

public abstract class BaseController<V extends BaseView, M extends BaseModel> {

	private V view;
	private M model;

	public BaseController(V view, M model) {
		this.view = view;
		this.model = model;
	}

	public V getView() {
		return view;
	}

	public M getModel() {
		return model;
	}
}
