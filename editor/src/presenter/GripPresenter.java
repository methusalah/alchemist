package presenter;

import com.simsilica.es.EntityId;

import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.Angle;
import util.math.AngleUtil;
import view.UIConfig;
import view.jmeScene.GripInputListener;
import view.jmeScene.GripView;

public class GripPresenter {
	public enum Tool{X, Y, Z, XY, YAW};
	private final GripView view;
	
	private EntityId selection;
	private Tool grabbedTool = null;
	private Point2D grabStart;
	
	GripInputListener inputListener;
	
	public GripPresenter(GripView view) {
		this.view = view;
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> {
			updateAttachement();
		});
		UIConfig.expandedComponents.addListener((observable, oldValue, newValue) -> {
			updateAttachement();
		});
	}

	public void grab(Tool tool, Point2D screenCoord){
		grabbedTool = tool;
		grabStart = screenCoord;
	}
	
	public void dragTo(Point2D screenCoord, Point2D pivot){
		if(grabbedTool != null) {
			Point2D v = screenCoord.getSubtraction(grabStart).getDivision(50);
			
			PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class);
			PlanarStance newStance;
			switch (grabbedTool){
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
				Point2D grabVec = grabStart.getSubtraction(pivot);
				Point2D targetVec = screenCoord.getSubtraction(pivot);
				double delta = AngleUtil.getOrientedDifference(grabVec.getAngle(), targetVec.getAngle());
				newStance = new PlanarStance(stance.getCoord(), new Angle(stance.getOrientation().getValue()+delta), stance.getElevation(), stance.getUpVector());
				break;
				default : throw new RuntimeException();
			}
			
			EditorPlatform.getEntityData().setComponent(selection, newStance);
			grabStart = screenCoord;
		}
	}
	
	private void updateAttachement(){
		selection = EditorPlatform.getSelectionProperty().getValue().getEntityId();
		if(EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class) != null && UIConfig.expandedComponents.contains(PlanarStance.class))
			view.showOn(selection);
		else
			view.hide();
	}
}
