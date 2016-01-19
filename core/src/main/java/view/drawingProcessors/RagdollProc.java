package view.drawingProcessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.Processor;
import model.ES.component.behavior.RagdollOnDestroy;
import model.ES.component.motion.physic.Physic;
import model.ES.component.visuals.Model;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.RandomUtil;
import view.SpatialPool;
import view.math.TranslateUtil;

public class RagdollProc extends Processor{
	private PhysicsSpace physicsSpace;
	Map<EntityId, Double> masses = new HashMap<>();
	Map<EntityId, Point2D> velocities = new HashMap<>();
	Map<Spatial, Long> managed = new HashMap<>();

	@Override
	protected void registerSets() {
		registerDefault(Model.class, RagdollOnDestroy.class, Physic.class);
	}
	
	@Override
	protected void onUpdated() {
		long time = System.currentTimeMillis();
		List<Spatial> toRemove = new ArrayList<>(); 
		for(Spatial s : managed.keySet())
			if(managed.get(s).longValue()+5000 < time)
				toRemove.add(s);
		for(Spatial s : toRemove){
			AppFacade.getMainSceneNode().detachChild(s);
			physicsSpace.remove(s);
			managed.remove(s);
			SpatialPool.models.remove(s);
		}
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		onEntityUpdated(e);
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		Physic ph = entityData.getComponent(e.getId(), Physic.class);
		masses.put(e.getId(), ph.getMass());
		velocities.put(e.getId(), ph.getVelocity());
	}
	
	@Override
	protected void onInitialized(AppStateManager stateManager) {
		super.onInitialized(stateManager);
		if(stateManager.getState(BulletAppState.class) == null){
			stateManager.attach(new BulletAppState());
		}
		stateManager.getState(BulletAppState.class).getPhysicsSpace().setGravity(new Vector3f(0, 0, -6));
		stateManager.getState(BulletAppState.class).startPhysics();
		
		physicsSpace = stateManager.getState(BulletAppState.class).getPhysicsSpace();
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		Spatial s = SpatialPool.models.get(e.getId());
		managed.put(s, System.currentTimeMillis());
		s.setName(s.getName() + " ragdoll");

		RigidBodyControl control = new RigidBodyControl(masses.get(e.getId()).floatValue());
		s.addControl(control);
		control.setLinearVelocity(TranslateUtil.toVector3f(velocities.get(e.getId())));
		control.setAngularVelocity(TranslateUtil.toVector3f(velocities.get(e.getId()).getScaled(RandomUtil.between(0.1, 3)).getRotation(AngleUtil.RIGHT*RandomUtil.between(0.8, 1.2))));
		
		physicsSpace.add(s);
		AppFacade.getMainSceneNode().attachChild(s);

	}
}
