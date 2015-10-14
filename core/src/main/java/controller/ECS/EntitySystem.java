package controller.ECS;

import model.ES.processor.command.PlayerRotationControlProc;
import model.ES.processor.command.PlayerThrustControlProc;
import view.drawingProcessors.CameraPlacingProc;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.PlacingModelProc;
import view.drawingProcessors.VelocityVisualisationProc;

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
		stateManager.attach(new ModelProc());
		stateManager.attach(new PlacingModelProc());
		stateManager.attach(new LightProc());
		stateManager.attach(new VelocityVisualisationProc());

		logicThread = new Thread(new LogicThread(ed));
		logicThread.start();
		
	}
	
	public void stateDetached(AppStateManager stateManager) {
		logicThread.interrupt();
		super.stateDetached(stateManager);
	}
	
}
