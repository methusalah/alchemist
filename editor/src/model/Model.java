package model;

import com.simsilica.es.base.DefaultEntityData;

import model.ES.component.Naming;
import model.ES.component.command.PlayerControl;
import model.ES.component.motion.PlanarStance;
import model.ES.serial.BlueprintCreator;

public class Model {
	public final Inspector inspector;
	public final Hierarchy hierarchy;
	
	public Model() {
		DefaultEntityData ed = new DefaultEntityData();
		BlueprintCreator.setEntityData(ed);
		BlueprintCreator.create("player ship", null);
		BlueprintCreator.create("enemy", null);
		BlueprintCreator.create("sun", null);

		inspector = new Inspector(ed);
		inspector.addComponentToScan(Naming.class);
		inspector.addComponentToScan(PlanarStance.class);
		inspector.addComponentToScan(PlayerControl.class);

		hierarchy = new Hierarchy(ed);
	}
}
