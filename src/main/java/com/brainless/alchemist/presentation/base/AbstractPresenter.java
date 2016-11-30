package com.brainless.alchemist.presentation.base;

public class AbstractPresenter<T extends Viewer> {
	protected final T viewer;
	
	public AbstractPresenter(T viewer) {
		this.viewer = viewer;
	}

}
