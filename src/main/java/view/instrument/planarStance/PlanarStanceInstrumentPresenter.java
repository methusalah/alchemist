package view.instrument.planarStance;

import com.simsilica.es.EntityId;

import component.motion.PlanarStance;
import model.EditorPlatform;
import model.state.SpatialSelector;
import presentation.common.EntityNode;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import view.ViewPlatform;
import view.instrument.InstrumentPresenter;

public class PlanarStanceInstrumentPresenter implements InstrumentPresenter{
	public enum Tool{X, Y, Z, XY, YAW};
	private final PlanarStanceInstrument view;
	
	private EntityId selection;
	private Tool actualTool = null;
	private Point2D dragStart;
	
	public PlanarStanceInstrumentPresenter(PlanarStanceInstrument view) {
		this.view = view;
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> updateAttachement());
		ViewPlatform.expandedComponents.addListener((observable, oldValue, newValue) -> updateAttachement());
	}

	public void selectTool(Tool tool){
		actualTool = tool;
	}
	
	@Override
	public void startDrag(Point2D screenCoord){
		dragStart = screenCoord;
	}
	
	@Override
	public void drag(Point2D screenCoord){
		if(actualTool != null) {
			Point2D v = screenCoord.getSubtraction(dragStart).getDivision(50);
			
			PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class);
			PlanarStance newStance;
			switch (actualTool){
			case X :
				v = v.getRotation(-stance.getOrientation().getValue());
				v = new Point2D(v.x, 0);
				v = v.getRotation(stance.getOrientation().getValue());
				newStance = new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector());
				break;
			case Y :
				v = v.getRotation(-stance.getOrientation().getValue()-AngleUtil.RIGHT);
				v = new Point2D(v.x, 0);
				v = v.getRotation(stance.getOrientation().getValue()+AngleUtil.RIGHT);
				newStance = new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector());
				break;
			case Z :
				newStance = new PlanarStance(stance.getCoord(), stance.getOrientation(), stance.getElevation()+(v.x+v.y)/2, stance.getUpVector());
				break;
			case XY :
				newStance = new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector());
				break;
			case YAW :
				Point2D pivot = SpatialSelector.getScreenCoord(getPosition());
				Point2D grabVec = dragStart.getSubtraction(pivot);
				Point2D targetVec = screenCoord.getSubtraction(pivot);
				double delta = AngleUtil.getOrientedDifference(grabVec.getAngle(), targetVec.getAngle());
				newStance = new PlanarStance(stance.getCoord(), new Angle(stance.getOrientation().getValue()+delta), stance.getElevation(), stance.getUpVector());
				break;
				default : throw new RuntimeException();
			}
			
			EditorPlatform.getEntityData().setComponent(selection, newStance);
			dragStart = screenCoord;
		}
	}
	
	@Override
	public void stopDrag() {
		selectTool(null);
	}
	
	private void updateAttachement(){
		EntityNode selectedNode = EditorPlatform.getSelectionProperty().getValue();
		if(selectedNode != null &&
				EditorPlatform.getEntityData().getComponent(selectedNode.getEntityId(), PlanarStance.class) != null && 
				ViewPlatform.expandedComponents.contains(PlanarStance.class)){
			selection = selectedNode.getEntityId();
			view.setVisible(true);
		} else {
			selection = null;
			view.setVisible(false);
		}
	}

	@Override
	public Point3D getPosition(){
		if(selection == null)
			return null;
		PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class); 
		return stance.getCoord().get3D(stance.getElevation());
	}

	@Override
	public double getOrientation(){
		if(selection == null)
			return 0;
		PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class); 
		return stance.getOrientation().getValue();
	}
}
