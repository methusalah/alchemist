package processor.logic.ability;

import com.simsilica.es.Entity;

import model.ECS.pipeline.Processor;

public class AbilityCoolDownProc extends Processor {

	@Override
	protected void registerSets() {
		registerDefault(Cooldown.class, Ability.class);
	}
	
	@Override
	protected void onEntityEachTick(Entity e) {
		Cooldown cd = e.get(Cooldown.class);
		Ability t = e.get(Ability.class);
		if(cd.getRemaining() > 0){
			if(t.isTriggered())
				setComp(e, new Ability(t.getName(), false));
			setComp(e, new Cooldown(cd.getRemaining() - LogicLoop.getMillisPerTick(), cd.getDuration()));
		}
	}
}