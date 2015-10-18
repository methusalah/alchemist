package controller.ECS;

import model.ES.processor.command.PlayerRotationControlProc;
import model.ES.processor.command.PlayerThrustControlProc;
import util.LogUtil;
import view.drawingProcessors.CameraPlacingProc;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.ModelPlacingProc;
import view.drawingProcessors.SpritePlacingProc;
import view.drawingProcessors.SpriteProc;
import view.drawingProcessors.ModelRotationProc;
import view.drawingProcessors.VelocityVisualisationProc;
import view.drawingProcessors.audio.ThrusterAudioProc;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;

public class EntitySystem extends AbstractAppState{
	private final EntityData ed;
	private Thread logicThread;

	public EntitySystem(EntityData ed) {
		this.ed = ed;
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		stateManager.attach(new EntityDataAppState(ed));
		// commands
		stateManager.attach(new PlayerRotationControlProc());
		stateManager.attach(new PlayerThrustControlProc());
//		stateManager.attach(new PlayerOrthogonalThrustControlProc());
		stateManager.attach(new CameraPlacingProc());
		stateManager.attach(new ParticleCasterInPlaneProc());
		
		stateManager.attach(new ThrusterAudioProc());

		stateManager.attach(new ModelProc());
		stateManager.attach(new SpriteProc());
		
		stateManager.attach(new ModelPlacingProc());
		stateManager.attach(new SpritePlacingProc());
		
		stateManager.attach(new ModelRotationProc());
		stateManager.attach(new LightProc());
		stateManager.attach(new VelocityVisualisationProc());

		logicThread = new Thread(new LogicThread(ed));
		logicThread.start();
	}
	
	@Override
	public void cleanup() {
		logicThread.interrupt();
	}
	
}
