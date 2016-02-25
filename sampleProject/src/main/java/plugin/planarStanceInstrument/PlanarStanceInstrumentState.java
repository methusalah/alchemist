package plugin.planarStanceInstrument;


import com.brainless.alchemist.model.tempImport.TranslateUtil;
import com.brainless.alchemist.view.instrument.AbstractInstrumentState;
import com.brainless.alchemist.view.instrument.customControl.InstrumentPart;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Torus;

import plugin.planarStanceInstrument.PlanarStanceInstrumentPresenter.Tool;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;

public class PlanarStanceInstrumentState extends AbstractInstrumentState {
	public PlanarStanceInstrumentState(PlanarStanceInstrumentPresenter presenter) {
		super(presenter);
	}

	@Override
	protected void createInstrumentParts(){
		InstrumentPart x = new InstrumentPart(Name + " x");
		Mesh cone = TranslateUtil.toJMEMesh(new Cone(0.01, 0.015, 12));
		x.putViewAndGripGeometry("0", new ColorRGBA(0.8f, 0.8f, 0.2f, 1), cone, true);
		x.setTranslation(new Point3D(0.1, 0, 0));
		x.setRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		x.setOnSelection(() -> getPresenter().selectTool(Tool.X));
		drawnNode.attachChild(x.getViewNode());
		gripNode.attachChild(x.getGripNode());
		parts.add(x);
		
		InstrumentPart y = new InstrumentPart(Name + " y");
		y.putViewAndGripGeometry("0", new ColorRGBA(0.8f, 0.2f, 0.2f, 1), cone, true);
		y.setTranslation(new Point3D(0, 0.1, 0));
		y.setRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		y.setOnSelection(() -> getPresenter().selectTool(Tool.Y));
		drawnNode.attachChild(y.getViewNode());
		gripNode.attachChild(y.getGripNode());
		parts.add(y);

		InstrumentPart z = new InstrumentPart(Name + " z");
		z.putViewAndGripGeometry("0", new ColorRGBA(0.2f, 0.2f, 0.8f, 1), new Box(0.0025f, 0.0025f, 0.1f), true);
		z.setOnSelection(() -> getPresenter().selectTool(Tool.Z));
		drawnNode.attachChild(z.getViewNode());
		gripNode.attachChild(z.getGripNode());
		parts.add(z);

		InstrumentPart xy = new InstrumentPart(Name + " xy");
		xy.putViewAndGripGeometry("0", new ColorRGBA(0.5f, 0.2f, 0.2f, 1), new Box(0.02f, 0.02f, 0.001f), true);
		xy.setTranslation(new Point3D(0.02, 0.02, 0));
		xy.setOnSelection(() -> getPresenter().selectTool(Tool.XY));
		drawnNode.attachChild(xy.getViewNode());
		gripNode.attachChild(xy.getGripNode());
		parts.add(xy);

		InstrumentPart yaw = new InstrumentPart(Name + " yaw");
		yaw.putViewGeometry("0", new ColorRGBA(0.2f, 0.2f, 0.8f, 1), new Torus(25, 3, 0.0015f, 0.09f), true);
		yaw.putGripGeometry("0", new Torus(12, 3, 0.005f, 0.09f));
		yaw.setOnSelection(() -> getPresenter().selectTool(Tool.YAW));
		drawnNode.attachChild(yaw.getViewNode());
		gripNode.attachChild(yaw.getGripNode());
		parts.add(yaw);
	}
	
	private PlanarStanceInstrumentPresenter getPresenter(){
		return (PlanarStanceInstrumentPresenter)presenter;
	}
}
