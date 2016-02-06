package model.ES.processor.AI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.StreamUtils;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.ECS.Processor;
import model.AI.blackboard.ShipBlackboard;
import model.ES.component.ability.Sighting;
import model.ES.component.combat.Attackable;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;

public class BehaviorTreeProc extends Processor {
	HashMap<EntityId, BehaviorTree<ShipBlackboard>> bTrees = new HashMap<>();

	@Override
	protected void registerSets() {
		registerDefault(Sighting.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e) {
		Reader reader = null;
		try {
			reader = new FileReader("assets/data/btrees/ship.tree");
			BehaviorTreeParser<ShipBlackboard> parser = new BehaviorTreeParser<ShipBlackboard>(BehaviorTreeParser.DEBUG_NONE);
			bTrees.put(e.getId(), parser.parse(reader, new ShipBlackboard(entityData, e.getId())));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			StreamUtils.closeQuietly(reader);
		}
	}
	
	@Override
	protected void onEntityUpdated(Entity e) {
		bTrees.get(e.getId()).step();
	}
}
