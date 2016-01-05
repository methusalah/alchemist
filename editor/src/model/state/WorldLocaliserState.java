package model.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import app.AppFacade;
import controller.ECS.DataState;
import model.world.WorldData;
import util.geometry.geom2d.Point2D;
import view.math.TranslateUtil;

public class WorldLocaliserState extends AbstractAppState {
	
	private WorldData worldData;
	private Point2D oldCoord = null;
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		worldData = stateManager.getState(DataState.class).getWorldData();
	}

	@Override
	public void update(float tpf) {
		Point2D camCoord = TranslateUtil.toPoint2D(AppFacade.getCamera().getLocation());
		if(oldCoord == null || oldCoord.getDistance(camCoord) > 10){
			oldCoord = camCoord;
			worldData.setCoord(camCoord);
		}
	}

}
