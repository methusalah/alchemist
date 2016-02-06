package view.drawingProcessors;

import view.math.TranslateUtil;
import app.AppFacade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;

import controller.ECS.Processor;
import model.ES.component.motion.ChasingCamera;

public class CameraPlacingProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(ChasingCamera.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ChasingCamera cc = e.get(ChasingCamera.class);
		Camera cam = AppFacade.getApp().getCamera();
		
		cam.setLocation(TranslateUtil.toVector3f(cc.pos));
		cam.lookAt(TranslateUtil.toVector3f(cc.target), Vector3f.UNIT_Y);
	}
}
