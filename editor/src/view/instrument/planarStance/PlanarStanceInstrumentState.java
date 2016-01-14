package view.instrument.planarStance;


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Torus;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.SpatialSelector;
import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;
import model.ES.component.motion.PlanarStance;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import presenter.instrument.PlanarStanceInstrumentPresenter.Tool;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import view.SpatialPool;
import view.controls.jmeScene.SceneTool;
import view.jme.Cone;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import view.mesh.Circle;

public class PlanarStanceInstrumentState extends AbstractAppState {
	private final PlanarStanceInstrumentPresenter presenter;
	private final SceneTool x, y, z, xy, yaw;
	private SceneSelectorState selector;
	private EntityData entityData;
	
	private Node drawnNode = new Node(PlanarStanceInstrumentState.class.getSimpleName() + " view");
	private Node gripNode = new Node(PlanarStanceInstrumentState.class.getSimpleName() + " grip");
	private Spatial chasedSpatial;
	private EntityId chasedEntity;
	private Geometry pointedGeometry;
	
	public PlanarStanceInstrumentState(PlanarStanceInstrumentPresenter presenter) {
		this.presenter = presenter;
		x = new SceneTool(PlanarStanceInstrumentState.class.getSimpleName()+ " x", new ColorRGBA(0.8f, 0.8f, 0.2f, 1));
		y = new SceneTool(PlanarStanceInstrumentState.class.getSimpleName()+ " y", new ColorRGBA(0.8f, 0.2f, 0.2f, 1));
		z = new SceneTool(PlanarStanceInstrumentState.class.getSimpleName()+ " z", new ColorRGBA(0.2f, 0.2f, 0.8f, 1));
		xy = new SceneTool(PlanarStanceInstrumentState.class.getSimpleName()+ " xy", new ColorRGBA(0.5f, 0.2f, 0.2f, 1));
		yaw = new SceneTool(PlanarStanceInstrumentState.class.getSimpleName()+ " yaw", new ColorRGBA(0.2f, 0.2f, 0.8f, 1));
		createSceneTools();
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
		entityData = stateManager.getState(DataState.class).getEntityData();
	}
	
	@Override
	public void update(float tpf) {
		Geometry lastPointed = pointedGeometry;
		pointedGeometry = SpatialSelector.getPointedGeometry(gripNode, selector.getCoordInScreenSpace());
		if(lastPointed != pointedGeometry){
			x.setHover(pointedGeometry == x.getGrip());
			y.setHover(pointedGeometry == y.getGrip());
			z.setHover(pointedGeometry == z.getGrip());
			xy.setHover(pointedGeometry == xy.getGrip());
			yaw.setHover(pointedGeometry == yaw.getGrip());
		}

		if(chasedSpatial != null){
			drawnNode.setLocalScale(AppFacade.getCamera().getLocation().distance(chasedSpatial.getWorldTranslation())/20);
			drawnNode.setLocalTranslation(chasedSpatial.getLocalTranslation());
			gripNode.setLocalScale(AppFacade.getCamera().getLocation().distance(chasedSpatial.getWorldTranslation())/20);
			gripNode.setLocalTranslation(chasedSpatial.getLocalTranslation());
			PlanarStance stance = entityData.getComponent(chasedEntity, PlanarStance.class);
			if(stance != null){
				drawnNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)stance.getOrientation().getValue()));
				gripNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)stance.getOrientation().getValue()));
			}
		}
	}

	public void grab(){
		Point2D grabStart = selector.getCoordInScreenSpace();
		if(pointedGeometry == x.getGrip())
			presenter.grab(Tool.X, grabStart);
		else if(pointedGeometry == y.getGrip())
			presenter.grab(Tool.Y, grabStart);
		else if(pointedGeometry == z.getGrip())
			presenter.grab(Tool.Z, grabStart);
		else if(pointedGeometry == xy.getGrip())
			presenter.grab(Tool.XY, grabStart);
		else if(pointedGeometry == yaw.getGrip())
			presenter.grab(Tool.YAW, grabStart);
		else
			presenter.grab(null, null);
	}

	public void release(){
		presenter.grab(null, null);
	}
	
	public void drag(){
		presenter.dragTo(selector.getCoordInScreenSpace(), SpatialSelector.getScreenCoord(TranslateUtil.toPoint3D(chasedSpatial.getWorldTranslation())));

	}
	
	public void attach(EntityId eid){
		chasedEntity = eid;
		chasedSpatial = SpatialPool.models.get(eid);
		AppFacade.getRootNode().attachChild(drawnNode);
	}

	public void detach() {
		chasedEntity = null;
		chasedSpatial = null;
		drawnNode.removeFromParent();
	}
	
	private void createSceneTools(){
		Mesh viewCone = TranslateUtil.toJMEMesh(new Cone(0.2, 0.3, 12));
		Mesh gripCone = TranslateUtil.toJMEMesh(new Cone(0.2, 0.3, 12));
		
		x.setMesh(viewCone, gripCone);
		x.getView().setLocalTranslation(2, 0, 0);
		x.getGrip().setLocalTranslation(2, 0, 0);
		x.getView().setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		x.getGrip().setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		y.setMesh(viewCone, gripCone);
		y.getView().setLocalTranslation(0, 2, 0);
		y.getGrip().setLocalTranslation(0, 2, 0);
		y.getView().setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		y.getGrip().setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		z.setMesh(new Box(0.05f, 0.05f, 2));

		xy.setMesh(new Box(0.2f, 0.2f, 0.001f));
		xy.getView().setLocalTranslation(0.2f, 0.2f, 0);
		xy.getGrip().setLocalTranslation(0.2f, 0.2f, 0);
		
		Mesh viewTorus = new Torus(25, 3, 0.03f, 1.8f);
		Mesh gripTorus = new Torus(12, 3, 0.1f, 1.8f);
		yaw.setMesh(viewTorus, gripTorus);
		
		drawnNode.attachChild(x.getView());
		drawnNode.attachChild(y.getView());
		drawnNode.attachChild(z.getView());
		drawnNode.attachChild(xy.getView());
		drawnNode.attachChild(yaw.getView());
		drawnNode.setQueueBucket(Bucket.Transparent);
		
		gripNode.attachChild(x.getGrip());
		gripNode.attachChild(y.getGrip());
		gripNode.attachChild(z.getGrip());
		gripNode.attachChild(xy.getGrip());
		gripNode.attachChild(yaw.getGrip());
	}
}
