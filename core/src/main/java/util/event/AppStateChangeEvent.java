package util.event;

import controller.AppState;


public class AppStateChangeEvent extends Event {

	private final Class<AppState> AppStateClass;

	public AppStateChangeEvent(Class<AppState> AppStateClass) {
		this.AppStateClass = AppStateClass;
	}

	public Class<AppState> getControllerClass() {
		return AppStateClass;
	}
}
