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

import component.motion.PlanarStance;
import component.motion.physic.CircleCollisionShape;
import component.motion.physic.Physic;
import logic.processor.Pool;
import logic.util.Point2DAdapter;
import logic.util.Vector2Adapter;
import util.geometry.geom2d.Point2D;
import util.math.Angle;

public class PhysicWorldProc extends BaseProcessor {
	
	private final World world;

	public PhysicWorldProc() {
		world = new World(new AxisAlignedBounds(1000, 1000));
		world.setGravity(new Vector2Adapter(Point2D.ORIGIN));
	}
	
	@Override
	protected void registerSets() {
		registerDefault(Physic.class, CircleCollisionShape.class, PlanarStance.class);
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
	protected void onEntityEachTick(Entity e) {
		PlanarStance stance = e.get(PlanarStance.class);
		Body b = Pool.bodies.get(e.getId());
		setComp(e, new PlanarStance(new Point2DAdapter(b.getTransform().getTranslation()), new Angle(b.getTransform().getRotation()), stance.elevation, stance.upVector));
	}
	
	
	@Override
	protected void onEntityRemoved(Entity e) {
		world.removeBody(Pool.bodies.get(e.getId()));
		Pool.bodies.remove(e.getId());
	}
	
	
	
}
