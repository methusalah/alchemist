package plugin.infiniteWorld.world;

import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.jme3.app.state.AbstractAppState;

import logic.processor.logic.world.WorldProc;
import util.geometry.geom2d.Point2D;

public class WorldLocaliserState extends AbstractAppState {
	private Point2D oldCoord = null;
	
	@Override
	public void update(float tpf) {
		if(RendererPlatform.getStateManager().getState(WorldProc.class) == null)
			return;
		Point2D camCoord = TranslateUtil.toPoint2D(RendererPlatform.getCamera().getLocation());
		if(oldCoord == null || oldCoord.getDistance(camCoord) > 10){
			oldCoord = camCoord;
			RendererPlatform.getStateManager().getState(WorldProc.class).setCoord(camCoord);
		}
	}

}
