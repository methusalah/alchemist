package logic.processor.logic.ability;

import com.brainless.alchemist.model.ECS.pipeline.Pipeline;
import com.brainless.alchemist.model.ECS.pipeline.BaseProcessor;
import com.simsilica.es.Entity;

import component.ability.Ability;
import component.ability.Cooldown;

public class AbilityCoolDownProc extends BaseProcessor {

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
			setComp(e, new Cooldown(cd.getRemaining() - Pipeline.getMillisPerTick(), cd.getDuration()));
		}
	}
}