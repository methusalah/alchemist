package commonLogic;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import model.ECS.builtInComponent.Parenting;

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
}
