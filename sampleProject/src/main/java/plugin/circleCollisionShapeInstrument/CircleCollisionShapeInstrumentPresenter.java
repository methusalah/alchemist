package plugin.circleCollisionShapeInstrument;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.instrument.InstrumentPresenter;
import com.simsilica.es.EntityId;

import component.motion.PlanarStance;
import component.motion.physic.CircleCollisionShape;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;

public class CircleCollisionShapeInstrumentPresenter implements InstrumentPresenter{
	private final CircleCollisionShapeInstrument view;
	
	private EntityId selection;
	private boolean toolSelected = false;
	private Point2D dragStart;
	
	public CircleCollisionShapeInstrumentPresenter(CircleCollisionShapeInstrument view) {
		this.view = view;
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> updateAttachement());
		ViewPlatform.expandedComponents.addListener((observable, oldValue, newValue) -> updateAttachement());
	}

	private void updateAttachement(){
		if(EditorPlatform.getSelectionProperty().getValue() != null){
			selection = EditorPlatform.getSelectionProperty().getValue().getEntityId();
			view.setVisible(EditorPlatform.getEntityData().getComponent(selection, CircleCollisionShape.class) != null && ViewPlatform.expandedComponents.contains(CircleCollisionShape.class));
		}
	}

	@Override
	public Point3D getPosition() {
		if(selection == null)
			return null;
		PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class);
		if(stance == null)
			return null;
		return stance.getCoord().get3D(stance.getElevation());
	}

	@Override
	public double getOrientation() {
		return 0;
	}
	
	public double getShapeRadius(){
		if(selection != null)
			return EditorPlatform.getEntityData().getComponent(selection, CircleCollisionShape.class).getRadius();
		else
			return 1;
	}
	
	public void selectTool(){
		toolSelected = true;
	}

	@Override
	public void startDrag(Point2D screenCoord) {
		dragStart = screenCoord;
	}

	@Override
	public void drag(Point2D screenCoord) {
		if(toolSelected){
			Point2D v = screenCoord.getSubtraction(dragStart).getDivision(50);
			
			CircleCollisionShape shape = EditorPlatform.getEntityData().getComponent(selection, CircleCollisionShape.class);
			double newRadius = Math.max(shape.getRadius()+(v.x+v.y)/2, 0);
			EditorPlatform.getEntityData().setComponent(selection, new CircleCollisionShape(newRadius, shape.getDensity(), shape.getRestitution()));
			dragStart = screenCoord;
		}
	}

	@Override
	public void stopDrag() {
		toolSelected = false;
	}
}
