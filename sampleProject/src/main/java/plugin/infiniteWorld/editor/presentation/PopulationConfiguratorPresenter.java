package plugin.infiniteWorld.editor.presentation;

import java.util.ArrayList;
import java.util.List;

import com.brainless.alchemist.model.ECS.blueprint.Blueprint;
import com.brainless.alchemist.model.ECS.blueprint.BlueprintLibrary;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.simsilica.es.EntityComponent;

import component.motion.PlanarStance;
import javafx.collections.FXCollections;
import logic.processor.logic.world.WorldProc;
import plugin.infiniteWorld.editor.view.population.PopulationConfigurator;
import plugin.infiniteWorld.world.EntityInstance;
import util.geometry.geom3d.Point3D;
import util.math.Angle;

public class PopulationConfiguratorPresenter extends WorldTool {
	
	private Blueprint bp;
	
	public PopulationConfiguratorPresenter(PopulationConfigurator view) {
		view.majBlueprintList(FXCollections.observableArrayList(BlueprintLibrary.getAllBlueprints()));
	}
	
	@Override
	public void doPrimary() {
		PlanarStance stance = new PlanarStance(coord, new Angle(0), 0, Point3D.UNIT_Z);
		List<EntityComponent> comps = new ArrayList<EntityComponent>();
		comps.add(stance);
		
		EntityInstance i = new EntityInstance(bp.getName(), comps);
		
		RendererPlatform.getStateManager().getState(WorldProc.class).addEntityInstance(i);
	}

	public void setBlueprint(Blueprint bp) {
		this.bp = bp;
	}
}
