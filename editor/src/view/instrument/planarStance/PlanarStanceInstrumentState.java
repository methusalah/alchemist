package view.instrument.planarStance;


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Torus;

import app.AppFacade;
import controller.SpatialSelector;
import controller.ECS.SceneSelectorState;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import presenter.instrument.PlanarStanceInstrumentPresenter.Tool;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.controls.jmeScene.InstrumentPart;
import view.jme.Cone;
import view.math.TranslateUtil;

public class PlanarStanceInstrumentState extends AbstractAppState {
	private static final String Name = PlanarStanceInstrumentState.class.getSimpleName();
	
	private final PlanarStanceInstrumentPresenter presenter;
	private final InstrumentPart x, y, z, xy, yaw;
	private SceneSelectorState selector;
	
	private Node drawnNode = new Node(PlanarStanceInstrumentState.class.getSimpleName() + " view");
	private Node gripNode = new Node(PlanarStanceInstrumentState.class.getSimpleName() + " grip");
	private Geometry pointedGeometry;
	
	public PlanarStanceInstrumentState(PlanarStanceInstrumentPresenter presenter) {
		this.presenter = presenter;
		x = new InstrumentPart(Name + " x");
		y = new InstrumentPart(Name + " y");
		z = new InstrumentPart(Name + " z");
		xy = new InstrumentPart(Name + " xy");
		yaw = new InstrumentPart(Name + " yaw");
		createSceneTools();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
	}
	
	@Override
	public void update(float tpf) {
		Geometry lastPointed = pointedGeometry;
		pointedGeometry = SpatialSelector.getPointedGeometry(gripNode, selector.getCoordInScreenSpace());
		if(lastPointed != pointedGeometry){
			x.setHover(x.containsGrip(pointedGeometry));
			y.setHover(y.containsGrip(pointedGeometry));
			z.setHover(z.containsGrip(pointedGeometry));
			xy.setHover(xy.containsGrip(pointedGeometry));
			yaw.setHover(yaw.containsGrip(pointedGeometry));
		}

		Point3D pos = presenter.getPosition();
		if(pos != null){
			// translation
			drawnNode.setLocalTranslation(TranslateUtil.toVector3f(pos));
			gripNode.setLocalTranslation(TranslateUtil.toVector3f(pos));
			
			// rotation
			double orientation = presenter.getOrientation();
			drawnNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)orientation));
			gripNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)orientation));
			
			// scale
			double camDistance = AppFacade.getCamera().getLocation().distance(TranslateUtil.toVector3f(pos));
			drawnNode.setLocalScale((float)camDistance/20);
			gripNode.setLocalScale((float)camDistance/20);
		}
	}

	public void dragStart(){
		Point2D grabStart = selector.getCoordInScreenSpace();
		if(x.containsGrip(pointedGeometry))
			presenter.grab(Tool.X, grabStart);
		else if(y.containsGrip(pointedGeometry))
			presenter.grab(Tool.Y, grabStart);
		else if(z.containsGrip(pointedGeometry))
			presenter.grab(Tool.Z, grabStart);
		else if(xy.containsGrip(pointedGeometry))
			presenter.grab(Tool.XY, grabStart);
		else if(yaw.containsGrip(pointedGeometry))
			presenter.grab(Tool.YAW, grabStart);
		else
			presenter.grab(null, null);
	}

	public void dragStop(){
		presenter.grab(null, null);
	}
	
	public void drag(){
		presenter.dragTo(selector.getCoordInScreenSpace());

	}
	
	@Override
	public void stateAttached(AppStateManager stateManager) {
		super.stateAttached(stateManager);
		AppFacade.getRootNode().attachChild(drawnNode);
	}

	@Override
	public void stateDetached(AppStateManager stateManager) {
		drawnNode.removeFromParent();
		x.setHover(false);
		y.setHover(false);
		z.setHover(false);
		xy.setHover(false);
		yaw.setHover(false);
	}
	
	private void createSceneTools(){
		Mesh cone = TranslateUtil.toJMEMesh(new Cone(0.2, 0.3, 12));
		x.putViewAndGripGeometry("0", new ColorRGBA(0.8f, 0.8f, 0.2f, 1), cone, true);
		x.setTranslation(new Point3D(2, 0, 0));
		x.setRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		x.setOnDrag(() -> presenter.

		y.putViewAndGripGeometry("0", new ColorRGBA(0.8f, 0.2f, 0.2f, 1), cone, true);
		y.setTranslation(new Point3D(0, 2, 0));
		y.setRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));

		z.putViewAndGripGeometry("0", new ColorRGBA(0.2f, 0.2f, 0.8f, 1), new Box(0.05f, 0.05f, 2), true);

		xy.putViewAndGripGeometry("0", new ColorRGBA(0.5f, 0.2f, 0.2f, 1), new Box(0.2f, 0.2f, 0.001f), true);
		xy.setTranslation(new Point3D(0.2, 0.2, 0));

		yaw.putViewGeometry("0", new ColorRGBA(0.2f, 0.2f, 0.8f, 1), new Torus(25, 3, 0.03f, 1.8f), true);
		yaw.putGripGeometry("0", new Torus(12, 3, 0.1f, 1.8f));
		
		drawnNode.attachChild(x.getViewNode());
		drawnNode.attachChild(y.getViewNode());
		drawnNode.attachChild(z.getViewNode());
		drawnNode.attachChild(xy.getViewNode());
		drawnNode.attachChild(yaw.getViewNode());
		drawnNode.setQueueBucket(Bucket.Transparent);
		
		gripNode.attachChild(x.getGripNode());
		gripNode.attachChild(y.getGripNode());
		gripNode.attachChild(z.getGripNode());
		gripNode.attachChild(xy.getGripNode());
		gripNode.attachChild(yaw.getGripNode());
	}
}
