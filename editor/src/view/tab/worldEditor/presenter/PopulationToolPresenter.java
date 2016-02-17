package view.tab.worldEditor.presenter;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import app.AppFacade;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import model.ES.component.motion.PlanarStance;
import model.ES.processor.world.WorldProc;
import model.ES.serial.Blueprint;
import model.ES.serial.BlueprintLibrary;
import model.ES.serial.EntityInstance;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import view.tab.worldEditor.population.PopulationConfigurator;

public class PopulationToolPresenter extends WorldTool {
	
	private Blueprint bp;
	
	public PopulationToolPresenter(PopulationConfigurator view) {
		view.majBlueprintList(FXCollections.observableArrayList(BlueprintLibrary.getAllBlueprints()));
	}
	
	@Override
	public void doPrimary() {
		PlanarStance stance = new PlanarStance(coord, new Angle(0), 0, Point3D.UNIT_Z);
		List<EntityComponent> comps = new ArrayList<EntityComponent>();
		comps.add(stance);
		
		EntityInstance i = new EntityInstance(bp.getName(), comps);
		
		AppFacade.getStateManager().getState(WorldProc.class).addEntityInstance(i);
	}

	public void setBlueprint(Blueprint bp) {
		this.bp = bp;
	}
}
