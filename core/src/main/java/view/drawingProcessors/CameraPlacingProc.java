package view.drawingProcessors;

import view.math.TranslateUtil;
import model.ES.component.camera.ChasingCamera;
import app.AppFacade;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.simsilica.es.Entity;

import controller.ECS.Processor;

public class CameraPlacingProc extends Processor {

	@Override
	protected void registerSets() {
		register(ChasingCamera.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		ChasingCamera cc = e.get(ChasingCamera.class);
		Camera cam = AppFacade.getApp().getCamera();
		
		cam.setLocation(TranslateUtil.toVector3f(cc.pos));
		cam.lookAt(TranslateUtil.toVector3f(cc.target), Vector3f.UNIT_Y);
	}
}
