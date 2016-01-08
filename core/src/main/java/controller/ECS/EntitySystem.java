package controller.ECS;

import model.Command;
import model.ES.processor.command.PlayerRotationControlProc;
import model.ES.processor.command.PlayerThrustControlProc;
import model.ES.processor.world.WorldProc;
import model.world.WorldData;
import util.LogUtil;
import view.drawingProcessors.CameraPlacingProc;
import view.drawingProcessors.EdgeCollisionShapeDrawingProc;
import view.drawingProcessors.LightProc;
import view.drawingProcessors.ModelProc;
import view.drawingProcessors.ParticleCasterInPlaneProc;
import view.drawingProcessors.ModelPlacingProc;
import view.drawingProcessors.SpritePlacingProc;
import view.drawingProcessors.SpriteProc;
import view.drawingProcessors.ModelRotationProc;
import view.drawingProcessors.VelocityVisualisationProc;
import view.drawingProcessors.audio.AbilityAudioProc;
import view.drawingProcessors.audio.ThrusterAudioProc;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;

public class EntitySystem extends AbstractAppState {
	private AppStateManager stateManager;
	private Thread logicThread;
	public final LogicLoop loop; 

	private final EntityData ed;
	private final WorldData world;
	private final Command command;

	
	List<AppState> visualStates = new ArrayList<>();
	List<AppState> audioStates = new ArrayList<>();
	List<AppState> commandStates = new ArrayList<>();
	List<AppState> logicStates = new ArrayList<>();

	public EntitySystem(EntityData ed, WorldData world, Command command) {
		this.ed = ed;
		this.world = world;
		this.command = command;
		loop = new LogicLoop(ed, world, command);
		
		
		visualStates.add(new ParticleCasterInPlaneProc());
		visualStates.add(new ModelProc());
		visualStates.add(new SpriteProc());
		//visualStates.add(new EdgeCollisionShapeDrawingProc());
		
		visualStates.add(new ModelPlacingProc());
		visualStates.add(new SpritePlacingProc());
		
		visualStates.add(new ModelRotationProc());
		visualStates.add(new LightProc());
		visualStates.add(new VelocityVisualisationProc());
		
		
		audioStates.add(new ThrusterAudioProc());
		audioStates.add(new AbilityAudioProc());
		
		commandStates.add(new PlayerRotationControlProc());
		commandStates.add(new PlayerThrustControlProc());
		commandStates.add(new CameraPlacingProc());
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		this.stateManager = stateManager;
		stateManager.attach(new DataState(ed, world, command));
    	stateManager.attach(new WorldProc(world));
    	stateManager.attach(new CommandState(command));
    	stateManager.attach(new SceneSelectorState());
	}
	
	@Override
	public void update(float tpf) {
    	world.attachDrawers();
	}
	
	public void initVisuals(boolean value){
		for(AppState as : visualStates)
			if(value)
				stateManager.attach(as);
			else
				stateManager.detach(as);
	}

	public void initCommand(boolean value){
		for(AppState as : commandStates)
			if(value)
				stateManager.attach(as);
			else
				stateManager.detach(as);
	}

	public void initAudio(boolean value){
		for(AppState as : audioStates)
			if(value)
				stateManager.attach(as);
			else
				stateManager.detach(as);
	}

	public void initLogic(boolean value){
		if(value){
			if(logicThread == null || !logicThread.isAlive()){
				logicThread = new Thread(loop);
				logicThread.start();
			}
		} else
			if(logicThread != null && logicThread.isAlive())
				logicThread.interrupt();
				
	}
	
	@Override
	public void cleanup() {
		initLogic(false);
	}
	
}
