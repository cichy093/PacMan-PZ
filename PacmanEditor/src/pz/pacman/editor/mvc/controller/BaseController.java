package pz.pacman.editor.mvc.controller;


import pz.pacman.editor.mvc.model.BaseModel;
import pz.pacman.editor.mvc.view.BaseView;

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
