package main.java.model.state;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.es.EntityId;

import main.java.model.tempImport.AppFacade;
import main.java.model.tempImport.SpatialSelector;
import main.java.model.tempImport.TranslateUtil;
import util.geometry.geom2d.Point2D;

public class SceneSelectorState extends AbstractAppState {

	private Point2D coordInScreenSpace = Point2D.ORIGIN;
	public Point2D getCoordInScreenSpace() {
		return coordInScreenSpace;
	}

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

	public Point2D getPointedCoordInPlan(Point2D coordInScreenSpace){
		return SpatialSelector.getCoord(plan, coordInScreenSpace);
	}
	
	public EntityId getPointedEntity(){
		Spatial s = SpatialSelector.getPointedGeometry(AppFacade.getMainSceneNode(), coordInScreenSpace);
		while(s != null){
			if(s.getUserData("EntityId") != null){
				return new EntityId(s.getUserData("EntityId"));
			}
			s = s.getParent();
		}
		return null;
	}

	public Geometry getPointedGeometry(){
		return SpatialSelector.getPointedGeometry(AppFacade.getMainSceneNode(), coordInScreenSpace);
	}
}