package application.topDownScene.state;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import app.AppFacade;
import controller.SpatialSelector;
import util.geometry.geom2d.Point2D;
import view.math.TranslateUtil;

public class SceneSelectorState extends AbstractAppState {

	private Point2D coordInScreenSpace = Point2D.ORIGIN;
	private Node plan = new Node("plan");
	
	public SceneSelectorState() {
		plan.attachChild(new Geometry("plan geometry", new Box(1000000, 1000000, 0.001f)));
	}
	
	@Override
	public void update(float tpf) {
		if(AppFacade.getInputManager() != null)
			coordInScreenSpace = TranslateUtil.toPoint2D(AppFacade.getInputManager().getCursorPosition());
	}
	
	public void setCoordInScreenSpace(Point2D coord){
		coordInScreenSpace = coord;
	}
	
	
	public Point2D getPointedCoordInPlan(){
		return SpatialSelector.getCoord(plan, coordInScreenSpace);
	}
}
