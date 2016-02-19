package util.event;

import controller.Controller;


public class AppStateChangeEvent extends Event {

	private final Class<? extends Controller> AppStateClass;

	public AppStateChangeEvent(Class<? extends Controller> AppStateClass) {
		this.AppStateClass = AppStateClass;
	}

	public Class<? extends Controller> getControllerClass() {
		return AppStateClass;
	}
}
