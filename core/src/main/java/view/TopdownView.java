package view;

import util.annotation.GuiNodeRef;
import util.annotation.RootNodeRef;
import util.event.EventManager;
import view.mapDrawing.EditorRenderer;
import view.mapDrawing.MapDrawer;

import com.google.inject.Inject;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class TopdownView extends View {

	// Renderers
	public EditorRenderer editorRend;

	@Inject
	public TopdownView(@RootNodeRef Node rn, @GuiNodeRef Node gn, ViewPort vp,  AssetManager am, MapDrawer md) {
		super(rn, gn, vp, am, md);
	}
}
