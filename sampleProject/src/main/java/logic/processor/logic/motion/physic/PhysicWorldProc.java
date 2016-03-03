package logic.processor.logic.motion.physic;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.simsilica.es.Entity;

import component.motion.physic.CircleCollisionShape;
import component.motion.physic.Physic;
import logic.processor.Pool;

public class PhysicWorldProc extends BaseProcessor {
	
	private final World world;

	public PhysicWorldProc() {
		world = new World(new AxisAlignedBounds(1000, 1000));
	}
	
	@Override
	protected void registerSets() {
		registerDefault(Physic.class, CircleCollisionShape.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		CircleCollisionShape shape = e.get(CircleCollisionShape.class);

		Body body = new Body();
		BodyFixture f = body.addFixture(new Circle(shape.getRadius()));
		f.setDensity(5);
		body.setMass(MassType.NORMAL);
		world.addBody(body);
		Pool.bodies.put(e.getId(), body);
	}
	
	@Override
	protected void onUpdated() {
		world.update(Pipeline.getSecondPerTick());
		
	}
	
	@Override
	protected void onEntityRemoved(Entity e) {
		world.removeBody(Pool.bodies.get(e.getId()));
		Pool.bodies.remove(e.getId());
	}
	
	
	
}
