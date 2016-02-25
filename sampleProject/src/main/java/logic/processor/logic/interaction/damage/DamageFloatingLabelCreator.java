package logic.processor.logic.interaction.damage;

import java.util.ArrayList;

import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import component.assets.FloatingLabel;
import component.combat.damage.DamageType;
import component.lifeCycle.LifeTime;
import component.motion.PlanarStance;
import component.motion.PlanarVelocityToApply;
import component.motion.physic.Physic;
import model.ECS.builtInComponent.Naming;
import model.tempImport.ColorData;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import util.math.Fraction;
import util.math.RandomUtil;

public class DamageFloatingLabelCreator {

	public static void create(EntityData ed, EntityId target, DamageType type, int amount, boolean onShield, boolean isDOT){
		
		ColorData color;
		switch(type){
		case BASIC : color = new ColorData(1, 1, 1, 1); break;
		case INCENDIARY : color = new ColorData(1, 1, 0.5, 0.5); break;
		case CORROSIVE : color = new ColorData(1, 0.5, 1, 0.5); break;
		case SHOCK : color = new ColorData(1, 0.5, 0.5, 1); break;
		default : throw new UnsupportedOperationException(type.toString());
		}

		int size = isDOT? 10 : 15;
		int lifetime = isDOT? 1000 : 1500;
		Point2D velocity = Point2D.ORIGIN.getTranslation(RandomUtil.between(-AngleUtil.FLAT, AngleUtil.FLAT), isDOT? 1 : 0.5);
		
		EntityId eid = ed.createEntity();
		ed.setComponent(eid, new Naming("floating damage "));
		ed.setComponent(eid, ed.getComponent(target, PlanarStance.class));
		String label = onShield? "(" + amount + ")" : ""+amount;
		ed.setComponent(eid, new FloatingLabel(label, color, size));
		ed.setComponent(eid, new Physic(Point2D.ORIGIN, "", new ArrayList<String>(), 0, new Fraction(0), null));
		ed.setComponent(eid, new PlanarVelocityToApply(velocity));
		ed.setComponent(eid, new LifeTime(lifetime));
	}
}
