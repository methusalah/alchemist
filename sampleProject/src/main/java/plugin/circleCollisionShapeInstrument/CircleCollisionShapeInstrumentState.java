package plugin.circleCollisionShapeInstrument;


import com.jme3.math.ColorRGBA;
import com.jme3.scene.shape.Torus;

import model.tempImport.RendererPlatform;
import model.tempImport.TranslateUtil;
import util.geometry.geom3d.Point3D;
import view.instrument.AbstractInstrumentState;
import view.instrument.customControl.InstrumentPart;

public class CircleCollisionShapeInstrumentState extends AbstractInstrumentState {
	InstrumentPart circle;
	
	public CircleCollisionShapeInstrumentState(CircleCollisionShapeInstrumentPresenter presenter) {
		super(presenter);
	}
	
	private CircleCollisionShapeInstrumentPresenter getPresenter(){
		return (CircleCollisionShapeInstrumentPresenter)presenter;
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		updateShape(getPresenter().getShapeRadius());
	}
	private void updateShape(double radius){
		Point3D pos = presenter.getPosition();
		double camDistance = RendererPlatform.getCamera().getLocation().distance(TranslateUtil.toVector3f(pos));
		circle.setViewMesh("0", new Torus(25, 3, 0.0015f, (float)(radius/camDistance)));
		circle.setGripMesh("0", new Torus(12, 3, 0.005f, (float)(radius/camDistance)));
	}
	
	@Override
	protected void createInstrumentParts() {
		circle = new InstrumentPart(Name + " yaw");
		circle.putViewGeometry("0", new ColorRGBA(0.8f, 0.2f, 0.8f, 1), new Torus(25, 3, 0.03f, 1), true);
		circle.putGripGeometry("0", new Torus(12, 3, 0.1f, 1));
		circle.setOnSelection(() -> getPresenter().selectTool());
		drawnNode.attachChild(circle.getViewNode());
		gripNode.attachChild(circle.getGripNode());
		parts.add(circle);
	}
}
