package view;

import util.event.EventManager;
import view.mapDrawing.EditorRenderer;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

public class EditorView extends View {

	// Renderers
	public EditorRenderer editorRend;

	public EditorView(Node rootNode, Node gui, PhysicsSpace physicsSpace, AssetManager am, ViewPort vp) {
		super(rootNode, gui, physicsSpace, am, vp);
		editorRend = new EditorRenderer(this);
	}

	@Override
	public void reset() {
		super.reset();
		
		if(editorRend != null)
			rootNode.detachChild(editorRend.mainNode);
			EventManager.unregister(editorRend);

		editorRend = new EditorRenderer(this);
		rootNode.attachChild(editorRend.mainNode);
	}

}
