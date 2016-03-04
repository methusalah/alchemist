package logic.commonLogic;

import org.slf4j.LoggerFactory;

import com.brainless.alchemist.model.ECS.builtInComponent.Naming;
import com.brainless.alchemist.model.ECS.builtInComponent.Parenting;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class Controlling {
	public static <T extends EntityComponent> T getControl(Class<T> controlClass, EntityId controlled, EntityData ed){
		Parenting p = ed.getComponent(controlled, Parenting.class);
		if(p == null){
			//LogUtil.warning("Can't find parent with controlling component '"+controlClass.getSimpleName()+"'.");
			return null;
		}
		
		EntityComponent res = ed.getComponent(p.getParent(), controlClass); 
		if(res != null)
			return (T) res;
		else
			return getControl(controlClass, p.getParent(), ed);
	}
	
	public static <T extends EntityComponent> EntityId getParentContaining(Class<T> controlClass, EntityId controlled, EntityData ed){
		EntityComponent res = ed.getComponent(controlled, controlClass); 
		if(res != null)
			return controlled;
		else {
			Parenting p = ed.getComponent(controlled, Parenting.class);
			if(p == null){
				LoggerFactory.getLogger(Controlling.class).warn("Can't find parent containing component '"+controlClass.getSimpleName()+"'. Entity " + ed.getComponent(controlled, Naming.class).getName());
				return null;
			} else{
				return getParentContaining(controlClass, p.getParent(), ed);
			}
		}
	}
}
