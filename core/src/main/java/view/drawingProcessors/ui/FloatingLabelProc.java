package view.drawingProcessors.ui;

import java.util.HashMap;
import java.util.Map;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.control.BillboardControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.component.assets.FloatingLabel;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import view.math.TranslateUtil;

public class FloatingLabelProc extends Processor {
	private BitmapFont font;
	
	private Map<EntityId, BitmapText> bitmaps = new HashMap<>();

	public FloatingLabelProc() {
		font = AppFacade.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
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
		AppFacade.getApp().getGuiNode().attachChild(bt);
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		BitmapText bt = bitmaps.get(e.getId());
		if(bt == null)
			return;
		
		PlanarStance stance = e.get(PlanarStance.class);

		// translation
		bt.setLocalTranslation(AppFacade.getCamera().getScreenCoordinates(TranslateUtil.toVector3f(stance.coord.get3D(stance.elevation))));
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		BitmapText bt = bitmaps.get(e.getId());
		//AppFacade.getMainSceneNode().detachChild(bt);
		AppFacade.getApp().getGuiNode().detachChild(bt);

		bitmaps.remove(e.getId());
	}
}
