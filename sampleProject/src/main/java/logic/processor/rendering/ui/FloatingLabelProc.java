package logic.processor.rendering.ui;

import java.util.HashMap;
import java.util.Map;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.assets.FloatingLabel;
import component.motion.PlanarStance;

public class FloatingLabelProc extends BaseProcessor {
	private BitmapFont font;
	
	private Map<EntityId, BitmapText> bitmaps = new HashMap<>();

	public FloatingLabelProc() {
		font = RendererPlatform.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
	}
	
	@Override
	protected void registerSets() {
		registerDefault(FloatingLabel.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		FloatingLabel label = e.get(FloatingLabel.class);
		
		BitmapText bt = new BitmapText(font);
		bt.setText(label.getLabel());
		bt.setColor(TranslateUtil.toColorRGBA(label.getColor()));
		bt.setSize((float)label.getSize());
		//bt.addControl(new BillboardControl());
		
		bitmaps.put(e.getId(), bt);
		//AppFacade.getMainSceneNode().attachChild(bt);
		RendererPlatform.getApp().getGuiNode().attachChild(bt);
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		BitmapText bt = bitmaps.get(e.getId());
		if(bt == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);

		// translation
		bt.setLocalTranslation(RendererPlatform.getCamera().getScreenCoordinates(TranslateUtil.toVector3f(stance.coord.get3D(stance.elevation))));
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		BitmapText bt = bitmaps.get(e.getId());
		//AppFacade.getMainSceneNode().detachChild(bt);
		RendererPlatform.getApp().getGuiNode().detachChild(bt);

		bitmaps.remove(e.getId());
	}
}
