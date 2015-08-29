package view;

import app.AppFacade;
import view.acting.ActorDrawer;


public class TopdownView extends View {

	public ActorDrawer actordrawer = new ActorDrawer(AppFacade.getAssetManager());
	
	public TopdownView() {
		super();
	}
}
