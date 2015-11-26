package model.world;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import model.ES.component.motion.PlanarStance;
import model.ES.serial.Blueprint;
import model.ES.serial.EntityInstance;
import model.world.RegionManager;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;

public class EntityInstancierTool extends Tool {
	
	public EntityInstancierTool(WorldData world) {
		super(world);
	}

	private Blueprint bp;
	
	@Override
	public void onPrimarySingleAction() {
		PlanarStance stance = new PlanarStance(coord, new Angle(0), 0, Point3D.UNIT_Z);
		List<EntityComponent> comps = new ArrayList<EntityComponent>();
		comps.add(stance);
		
		EntityInstance i = new EntityInstance(bp, comps);
		
		world.addEntityInstance(i);
	}

	public void setBp(Blueprint bp) {
		this.bp = bp;
	}
}
