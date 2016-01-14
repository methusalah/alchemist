package presenter.instrument;


import com.simsilica.es.EntityId;

import app.AppFacade;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import presenter.EditorPlatform;
import util.geometry.geom2d.Point2D;
import view.UIConfig;
import view.instrument.CircleCollisionShape.CircleCollisionShapeInstrument;
import view.instrument.planarStance.PlanarStanceInstrumentInputListener;

public class CircleCollisionShapeInstrumentPresenter {
	private final CircleCollisionShapeInstrument view;
	
	private EntityId selection;
	private Point2D grabStart;
	
	PlanarStanceInstrumentInputListener inputListener;
	
	public CircleCollisionShapeInstrumentPresenter(CircleCollisionShapeInstrument view) {
		this.view = view;
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue) -> updateAttachement());
		UIConfig.expandedComponents.addListener((observable, oldValue, newValue) -> updateAttachement());
	}

	public void grab(Point2D screenCoord){
		grabStart = screenCoord;
	}
	
	public void dragTo(Point2D screenCoord, Point2D pivot, double camRatioX, double camRatioY){
		Point2D v = screenCoord.getSubtraction(grabStart).getMult(camRatioX, camRatioY);
		
		PlanarStance stance = EditorPlatform.getEntityData().getComponent(selection, PlanarStance.class);
		CircleCollisionShape shape = EditorPlatform.getEntityData().getComponent(selection, CircleCollisionShape.class);
		double newRadius = Math.max(shape.getRadius()+(v.x+v.y)/2, 0);
		EditorPlatform.getEntityData().setComponent(selection, new CircleCollisionShape(newRadius));
		grabStart = screenCoord;
	}
	
	private void updateAttachement(){
		selection = EditorPlatform.getSelectionProperty().getValue().getEntityId();
		if(EditorPlatform.getEntityData().getComponent(selection, CircleCollisionShape.class) != null && UIConfig.expandedComponents.contains(CircleCollisionShape.class))
			view.showOn(selection);
		else
			view.hide();
	}
}
