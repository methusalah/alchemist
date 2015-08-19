package util.event;

import controller.AppState;


public class AppStateChangeEvent extends Event {

	private final Class<? extends AppState> AppStateClass;

	public AppStateChangeEvent(Class<? extends AppState> AppStateClass) {
		this.AppStateClass = AppStateClass;
	}

	public Class<? extends AppState> getControllerClass() {
		return AppStateClass;
	}
}
