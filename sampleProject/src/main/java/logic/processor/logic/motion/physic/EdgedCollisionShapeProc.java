package logic.processor.logic.motion.physic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Segment;

import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import component.motion.physic.EdgedCollisionShape;
import component.motion.physic.Physic;
import logic.commonLogic.Controlling;
import logic.processor.Pool;
import logic.util.Vector2Adapter;
import util.LogUtil;
import util.geometry.geom2d.Segment2D;

public class EdgedCollisionShapeProc extends BaseProcessor {

	private final Map<EntityId, List<BodyFixture>> fixtures = new HashMap<>();
	private final Map<EntityId, Body> bodies = new HashMap<>();
	
	@Override
	protected void registerSets() {
		registerDefault(EdgedCollisionShape.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		EdgedCollisionShape shape = e.get(EdgedCollisionShape.class);
		EntityId physicEntity = Controlling.getParentContaining(Physic.class, e.getId(), entityData);	
		if(physicEntity != null){
			Body b = Pool.bodies.get(physicEntity);
			if(b == null)
				LogUtil.info("body à null");
			fixtures.put(e.getId(), new ArrayList<>());
			for(Segment2D seg : shape.getEdges()){
				if(seg == null)
					LogUtil.info("segment à null");
				BodyFixture f = b.addFixture(new Segment(new Vector2Adapter(seg.getStart()), new Vector2Adapter(seg.getEnd())));
				f.setDensity(1);
				fixtures.get(e.getId()).add(f);
				bodies.put(e.getId(), b);
			}
			b.setMass(MassType.INFINITE);
		}
	}

	@Override
	protected void onEntityRemoved(Entity e) {
		for(BodyFixture fixture : fixtures.get(e.getId()))
			bodies.get(e.getId()).removeFixture(fixture);
		bodies.remove(e.getId());
		fixtures.remove(e.getId());
	}
}
