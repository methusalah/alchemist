package model.state;

import com.jme3.app.state.AbstractAppState;

import app.AppFacade;
import model.ES.processor.world.WorldProc;
import util.geometry.geom2d.Point2D;
import view.math.TranslateUtil;

public class WorldLocaliserState extends AbstractAppState {
	private Point2D oldCoord = null;
	
	@Override
	public void update(float tpf) {
		if(AppFacade.getStateManager().getState(WorldProc.class) == null)
			return;
		Point2D camCoord = TranslateUtil.toPoint2D(AppFacade.getCamera().getLocation());
		if(oldCoord == null || oldCoord.getDistance(camCoord) > 10){
			oldCoord = camCoord;
			AppFacade.getStateManager().getState(WorldProc.class).setCoord(camCoord);
		}
	}

}
