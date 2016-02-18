package main.java.model.tempImport;

import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;

import controller.cameraManagement.ChasingCameraProc;
import main.java.model.Command;
import main.java.model.ES.processor.LifeTimeProc;
import main.java.model.ES.processor.ParentingCleanerProc;
import main.java.model.ES.processor.RemovedCleanerProc;
import main.java.model.ES.processor.RemoverProc;
import main.java.model.ES.processor.AI.BehaviorTreeProc;
import main.java.model.ES.processor.ability.AbilityCoolDownProc;
import main.java.model.ES.processor.ability.AbilityProc;
import main.java.model.ES.processor.ability.AbilityTriggerResetProc;
import main.java.model.ES.processor.ability.BoostProc;
import main.java.model.ES.processor.ability.ProjectileLauncherProc;
import main.java.model.ES.processor.ability.TriggerRepeaterProc;
import main.java.model.ES.processor.combat.resistance.ShieldProc;
import main.java.model.ES.processor.combat.resistance.SpawnOnShieldDepletedProc;
import main.java.model.ES.processor.command.NeededRotationProc;
import main.java.model.ES.processor.command.NeededThrustProc;
import main.java.model.ES.processor.command.PlayerAbilityControlProc;
import main.java.model.ES.processor.holder.BoneHoldingProc;
import main.java.model.ES.processor.holder.PlanarHoldingProc;
import main.java.model.ES.processor.interaction.DestroyedOnTouchProc;
import main.java.model.ES.processor.interaction.ShockwaveOnTouchProc;
import main.java.model.ES.processor.interaction.SpawnMultipleOnBornProc;
import main.java.model.ES.processor.interaction.SpawnOnDecayProc;
import main.java.model.ES.processor.interaction.SpawnOnTouchProc;
import main.java.model.ES.processor.interaction.TouchingClearingProc;
import main.java.model.ES.processor.interaction.damage.DamageOnTouchProc;
import main.java.model.ES.processor.interaction.damage.DamagingOverTimeProc;
import main.java.model.ES.processor.interaction.damage.DamagingProc;
import main.java.model.ES.processor.motion.RandomVelocityApplicationProc;
import main.java.model.ES.processor.motion.VelocityApplicationProc;
import main.java.model.ES.processor.motion.physic.DraggingProc;
import main.java.model.ES.processor.motion.physic.PhysicForceProc;
import main.java.model.ES.processor.motion.physic.RandomDraggingProc;
import main.java.model.ES.processor.motion.physic.collisionDetection.CircleCircleCollisionProc;
import main.java.model.ES.processor.motion.physic.collisionDetection.CircleEdgeCollisionProc;
import main.java.model.ES.processor.motion.physic.collisionDetection.CollisionResolutionProc;
import main.java.model.ES.processor.motion.physic.collisionDetection.EdgeEdgeCollisionProc;
import main.java.model.ES.processor.senses.SightProc;
import main.java.model.ES.processor.shipGear.AttritionProc;
import main.java.model.ES.processor.shipGear.LightThrusterProc;
import main.java.model.ES.processor.shipGear.RotationThrusterProc;
import main.java.model.ES.processor.shipGear.ThrusterProc;

public class LogicLoop implements Runnable {
    private AppStateManager stateManager;
    private int tickCount = 0;
    private int waitTime = 0;
    
    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;
    
    public LogicLoop(EntityData ed, Command command) {
    	stateManager = new AppStateManager(null);

    	stateManager.attach(new DataState(ed, command));

		stateManager.attach(new ChasingCameraProc());
		stateManager.attach(new RotationThrusterProc());
		stateManager.attach(new ThrusterProc());
		// forces
		stateManager.attach(new NeededRotationProc());
		stateManager.attach(new NeededThrustProc());
		stateManager.attach(new RandomDraggingProc());
		stateManager.attach(new DraggingProc());
		stateManager.attach(new PhysicForceProc());
		stateManager.attach(new RandomVelocityApplicationProc());
		stateManager.attach(new BoostProc());
		stateManager.attach(new VelocityApplicationProc());
		// collisions
		stateManager.attach(new TouchingClearingProc());
		stateManager.attach(new CircleCircleCollisionProc());
		stateManager.attach(new CircleEdgeCollisionProc());
		stateManager.attach(new EdgeEdgeCollisionProc());
		stateManager.attach(new CollisionResolutionProc());
		// relations	
		stateManager.attach(new BoneHoldingProc());
		stateManager.attach(new PlanarHoldingProc());
		stateManager.attach(new LightThrusterProc());
		
		// ability
		stateManager.attach(new PlayerAbilityControlProc());
		stateManager.attach(new BehaviorTreeProc());
		stateManager.attach(new AbilityProc());
		stateManager.attach(new AbilityTriggerResetProc());
		stateManager.attach(new AbilityCoolDownProc());
		stateManager.attach(new TriggerRepeaterProc());
		
		
		stateManager.attach(new SpawnMultipleOnBornProc());
		stateManager.attach(new ProjectileLauncherProc());

		stateManager.attach(new DamagingProc());
		stateManager.attach(new DamagingOverTimeProc());
		stateManager.attach(new AttritionProc());
		stateManager.attach(new ShieldProc());
		stateManager.attach(new SpawnOnShieldDepletedProc());

		stateManager.attach(new SightProc());
		
		stateManager.attach(new SpawnOnTouchProc());
		stateManager.attach(new DamageOnTouchProc());
		stateManager.attach(new DestroyedOnTouchProc());
		stateManager.attach(new ShockwaveOnTouchProc());
		
		stateManager.attach(new LifeTimeProc());
		stateManager.attach(new SpawnOnDecayProc());
		
		stateManager.attach(new RemovedCleanerProc());
		stateManager.attach(new RemoverProc());
		stateManager.attach(new ParentingCleanerProc());
    }

    public int getTickCount() {
		return tickCount;
	}

	public int getWaitTime() {
		return waitTime;
	}
	
	public void resetIdleStats(){
		tickCount = 0;
		waitTime = 0;
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			long time = System.currentTimeMillis();
			stateManager.update((float)0.02);
			long nextTick = (long) (time+20);
			long towait = nextTick - System.currentTimeMillis();

			tickCount++;
			waitTime += towait;
			
			if(towait > 0)
				try {
					Thread.sleep(towait);
				} catch (InterruptedException e) {
					break;
				}
		}
	}

	public static int getMillisPerTick() {
		return millisPerTick;
	}

	public static void setMillisPerTick(int millisPerTick) {
		LogicLoop.millisPerTick = millisPerTick;
		secondPerTick = (double)millisPerTick/1000;
	}

	public static double getSecondPerTick() {
		return secondPerTick;
	}
	
	
}
