package logic.processor.logic.motion.physic;

import java.util.HashMap;
import java.util.Map;

import org.dyn4j.collision.AxisAlignedBounds;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;
import com.sun.org.apache.bcel.internal.generic.GETFIELD;

import component.motion.PlanarStance;
import component.motion.physic.CircleCollisionShape;
import component.motion.physic.Physic;
import logic.commonLogic.Controlling;
import logic.processor.Pool;
import logic.util.Point2DAdapter;
import logic.util.Vector2Adapter;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.Angle;

public class CircleCollisionShapeProc extends BaseProcessor {

	private final Map<EntityId, BodyFixture> fixtures = new HashMap<>();
	private final Map<EntityId, Body> bodies = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(CircleCollisionShape.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		CircleCollisionShape shape = e.get(CircleCollisionShape.class);
		EntityId physicEntity = Controlling.getParentContaining(Physic.class, e.getId(), entityData);	
		if(physicEntity != null){
			Body b = Pool.bodies.get(physicEntity);
			BodyFixture f = b.addFixture(new Circle(shape.getRadius()));
			f.setDensity(shape.getDensity());
			
			b.setMass(MassType.NORMAL);

			LogUtil.info("added a circle fixture");
			fixtures.put(e.getId(), f);
			bodies.put(e.getId(), b);
		}
	}

	@Override
	protected void onEntityRemoved(Entity e) {
		LogUtil.info("removed a circle fixture");
		bodies.get(e.getId()).removeFixture(fixtures.get(e.getId()));
		bodies.remove(e.getId());
		fixtures.remove(e.getId());
	}
}
