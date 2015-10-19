package model.ES.commonLogic;

import model.ES.component.relation.Parenting;
import model.ES.component.shipGear.Thruster;
import util.LogUtil;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class ThrusterControlling {

	public static EntityId findThruster(EntityId controlled, EntityData ed){
		Parenting p = ed.getComponent(controlled, Parenting.class);
		if(p == null)
			LogUtil.warning("Can't find parent thruster.");
		
		if(ed.getComponent(p.getParent(), Thruster.class) != null)
			return p.getParent();
		else
			return findThruster(p.getParent(), ed);
	}
}
