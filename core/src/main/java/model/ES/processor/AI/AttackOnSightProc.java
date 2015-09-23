package model.ES.processor.AI;

import java.util.HashMap;

import com.simsilica.es.Entity;
import com.simsilica.es.EntityId;

import controller.entityAppState.Processor;
import model.AI.IdleState;
import model.ES.component.motion.PlanarStance;
import model.ES.component.senses.Sighting;
import util.FSM.FiniteStateMachine;

public class AttackOnSightProc extends Processor {
	HashMap<EntityId, FiniteStateMachine> machines = new HashMap<>();

	@Override
	protected void registerSets() {
		register(Sighting.class, PlanarStance.class);
	}
	
	@Override
	protected void onEntityAdded(Entity e, float elapsedTime) {
		FiniteStateMachine fsm = new FiniteStateMachine();
		fsm.pushState(new IdleState(entityData, e.getId()));
		machines.put(e.getId(), fsm);
	}
	@Override
	protected void onEntityUpdated(Entity e, float elapsedTime) {
		machines.get(e.getId()).update();
	}
}
