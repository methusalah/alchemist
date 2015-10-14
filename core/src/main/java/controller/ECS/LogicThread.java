package controller.ECS;

import model.ES.processor.LifeTimeProc;
import model.ES.processor.RemoveProc;
import model.ES.processor.AI.BehaviorTreeProc;
import model.ES.processor.ability.AbilityTriggerResetProc;
import model.ES.processor.ability.ProjectileLauncherProc;
import model.ES.processor.ability.TriggerCancelationProc;
import model.ES.processor.ability.TriggerObserverProc;
import model.ES.processor.ability.TriggerRepeaterProc;
import model.ES.processor.command.NeededRotationProc;
import model.ES.processor.command.NeededThrustProc;
import model.ES.processor.command.PlayerAbilityControlProc;
import model.ES.processor.holder.BoneHoldingProc;
import model.ES.processor.holder.PlanarHoldingProc;
import model.ES.processor.interaction.DamageOnTouchProc;
import model.ES.processor.interaction.DamagingProc;
import model.ES.processor.interaction.DestroyedOnTouchProc;
import model.ES.processor.interaction.EffectOnTouchProc;
import model.ES.processor.interaction.ShockwaveOnTouchProc;
import model.ES.processor.motion.VelocityApplicationProc;
import model.ES.processor.motion.physic.CollisionProc;
import model.ES.processor.motion.physic.CollisionResolutionProc;
import model.ES.processor.motion.physic.DraggingProc;
import model.ES.processor.motion.physic.PhysicForceProc;
import model.ES.processor.senses.SightProc;
import model.ES.processor.shipGear.AttritionProc;
import model.ES.processor.shipGear.LightThrusterProc;
import model.ES.processor.shipGear.ParticleThrusterProc;
import model.ES.processor.shipGear.RotationThrusterProc;
import model.ES.processor.shipGear.ThrusterProc;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.simsilica.es.EntityData;

import controller.cameraManagement.ChasingCameraProc;

public class LogicThread implements Runnable {
    public static final double TIME_PER_FRAME = 0.02;
    private AppStateManager stateManager;
 
    public LogicThread(EntityData ed) {
    	stateManager = new AppStateManager(null);

    	stateManager.attach(new EntityDataAppState(ed));
		stateManager.attach(new ChasingCameraProc());
		stateManager.attach(new RotationThrusterProc());
		stateManager.attach(new ThrusterProc());
		// forces
		stateManager.attach(new NeededRotationProc());
		stateManager.attach(new NeededThrustProc());
		stateManager.attach(new DraggingProc());
		stateManager.attach(new PhysicForceProc());
		stateManager.attach(new VelocityApplicationProc());
		// collisions
		stateManager.attach(new CollisionProc());
		stateManager.attach(new CollisionResolutionProc());
		// relations	
		stateManager.attach(new BoneHoldingProc());
		stateManager.attach(new PlanarHoldingProc());
		stateManager.attach(new ParticleThrusterProc());
		stateManager.attach(new LightThrusterProc());
		
		// ability
		stateManager.attach(new PlayerAbilityControlProc());
		stateManager.attach(new BehaviorTreeProc());
		stateManager.attach(new TriggerObserverProc());
		stateManager.attach(new AbilityTriggerResetProc());
		stateManager.attach(new TriggerCancelationProc());
		stateManager.attach(new TriggerRepeaterProc());
		
		stateManager.attach(new ProjectileLauncherProc());

		stateManager.attach(new DamagingProc());
		stateManager.attach(new AttritionProc());

		stateManager.attach(new SightProc());
		
		stateManager.attach(new EffectOnTouchProc());
		stateManager.attach(new DamageOnTouchProc());
		stateManager.attach(new DestroyedOnTouchProc());
		stateManager.attach(new ShockwaveOnTouchProc());
		
		stateManager.attach(new LifeTimeProc());
		stateManager.attach(new RemoveProc());
    }

    @Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			long time = System.currentTimeMillis();
			stateManager.update((float)TIME_PER_FRAME);
			try {
				long nextTick = time+20;
				long towait = nextTick - System.currentTimeMillis();
				if(towait > 0)
					Thread.sleep(towait);
//				((CosmoVania)app).
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
//    public void run() {
//	    LogUtil.info("run !");
//	    stateManager.update(tpf);
//	    //throw new RuntimeException("taggle");
//    }

}
