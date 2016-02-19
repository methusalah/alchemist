package presentation.worldEditor;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import component.motion.PlanarStance;
import javafx.collections.FXCollections;
import model.ECS.blueprint.Blueprint;
import model.ECS.blueprint.BlueprintLibrary;
import model.tempImport.AppFacade;
import processor.logic.world.WorldProc;
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
