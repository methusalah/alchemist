package main.java.model.tempImport;

import java.util.ArrayList;
import java.util.List;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityData;

import app.AppFacade;
import controller.CommandState;
import main.java.model.Command;
import main.java.model.ES.processor.command.PlayerRotationControlProc;
import main.java.model.ES.processor.command.PlayerThrustControlProc;
import main.java.model.ES.processor.world.WorldProc;
import main.java.model.state.SceneSelectorState;
import main.java.view.drawingProcessors.CameraPlacingProc;
import main.java.view.drawingProcessors.LightProc;
import main.java.view.drawingProcessors.audio.AbilityAudioProc;
import main.java.view.drawingProcessors.audio.AudioSourcePlacingProc;
import main.java.view.drawingProcessors.audio.AudioSourceProc;
import main.java.view.drawingProcessors.audio.ThrusterAudioProc;
import main.java.view.drawingProcessors.model.ModelPlacingProc;
import main.java.view.drawingProcessors.model.ModelProc;
import main.java.view.drawingProcessors.model.ModelRotationProc;
import main.java.view.drawingProcessors.particle.ParticlePlacingProc;
import main.java.view.drawingProcessors.particle.ParticleProc;
import main.java.view.drawingProcessors.particle.ParticleThrusterProc;
import main.java.view.drawingProcessors.sprite.SpritePlacingProc;
import main.java.view.drawingProcessors.sprite.SpriteProc;
import main.java.view.drawingProcessors.ui.FloatingLabelProc;
import main.java.view.drawingProcessors.ui.VelocityVisualisationProc;

public class EntitySystem extends AbstractAppState {
	private AppStateManager stateManager;
	private Thread logicThread;
	public final LogicLoop loop; 

	private final EntityData ed;
	private final Command command;

	
	List<AppState> visualStates = new ArrayList<>();
	List<AppState> audioStates = new ArrayList<>();
	List<AppState> commandStates = new ArrayList<>();
	List<AppState> logicStates = new ArrayList<>();

	public EntitySystem(EntityData ed, Command command) {
		this.ed = ed;
		this.command = command;
		loop = new LogicLoop(ed, command);
		
		
		visualStates.add(new ModelProc());
		visualStates.add(new SpriteProc());
		visualStates.add(new ParticleProc());
		//visualStates.add(new RagdollProc());
		//visualStates.add(new EdgeCollisionShapeDrawingProc());
		
		visualStates.add(new ModelPlacingProc());
		visualStates.add(new SpritePlacingProc());
		visualStates.add(new ParticlePlacingProc());
		visualStates.add(new ParticleThrusterProc());
		
		visualStates.add(new ModelRotationProc());
		visualStates.add(new LightProc());
		visualStates.add(new VelocityVisualisationProc());
		visualStates.add(new FloatingLabelProc());
		
		
		audioStates.add(new AudioSourceProc());
		audioStates.add(new AudioSourcePlacingProc());
		audioStates.add(new ThrusterAudioProc());
		audioStates.add(new AbilityAudioProc());
		
		commandStates.add(new PlayerRotationControlProc());
		commandStates.add(new PlayerThrustControlProc());
		commandStates.add(new CameraPlacingProc());
	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		this.stateManager = stateManager;
		stateManager.attach(new DataState(ed));
    	stateManager.attach(new WorldProc());
    	stateManager.attach(new CommandState(command));
    	stateManager.attach(new SceneSelectorState());
	}
	
	public String sceneReport = "Not computed";
	@Override
	public void update(float tpf) {
    	//world.attachDrawers();
    	StringBuilder sb = new StringBuilder();
    	reportSceneDeeply(sb, 0, AppFacade.getMainSceneNode());
    	sceneReport = sb.toString();
	}
	
	private void reportSceneDeeply(StringBuilder sb, int indent, Spatial s){
		for(int i = 0; i<indent; i++)
			sb.append("    ");
		sb.append(s.getName() == null || s.getName().isEmpty()? "Unnamed" : s.getName());
		sb.append(System.lineSeparator());
		if(s instanceof Node){
			Node n = ((Node)s);
			if(s != AppFacade.getMainSceneNode() && n.getChildren().size() > 5){
				for(int i = 0; i < 5; i++)
					reportSceneDeeply(sb, indent+1, n.getChild(i));
				for(int i = 0; i < indent+1; i++)
					sb.append("    ");
				sb.append("... and " + (n.getChildren().size()-5) + " more.");
				sb.append(System.lineSeparator());
			} else
				for(Spatial child : ((Node)s).getChildren())
					reportSceneDeeply(sb, indent+1, child);
		}
		
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
