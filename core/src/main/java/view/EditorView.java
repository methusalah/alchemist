package view;

import app.AppFacade;
import view.acting.ActorDrawer;
import view.mapDrawing.EditorRenderer;

public class EditorView extends View {
	public ActorDrawer actordrawer = new ActorDrawer(AppFacade.getAssetManager());

	// Renderers
	public EditorRenderer editorRend;

	public EditorView() {
		super();
	}
}
