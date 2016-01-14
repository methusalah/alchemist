package view.instrument.circleCollisionShape;


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Torus;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.SpatialSelector;
import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;
import model.ES.component.motion.PlanarStance;
import model.ES.component.motion.physic.CircleCollisionShape;
import presenter.instrument.CircleCollisionShapeInstrumentPresenter;
import sun.util.logging.resources.logging;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import view.SpatialPool;
import view.controls.jmeScene.InstrumentPart;
import view.math.TranslateUtil;

public class CircleCollisionShapeInstrumentState extends AbstractAppState {
	private final CircleCollisionShapeInstrumentPresenter presenter;
	private final InstrumentPart circle;
	private SceneSelectorState selector;
	private EntityData entityData;
	
	private Node drawnNode = new Node(CircleCollisionShapeInstrumentState.class.getSimpleName() + " view");
	private Node gripNode = new Node(CircleCollisionShapeInstrumentState.class.getSimpleName() + " grip");
	private Spatial chasedSpatial;
	private EntityId chasedEntity;
	private Geometry pointedGeometry;
	private double camDistance = 1;
	
	public CircleCollisionShapeInstrumentState(CircleCollisionShapeInstrumentPresenter presenter) {
		this.presenter = presenter;
		circle = new InstrumentPart(CircleCollisionShapeInstrumentState.class.getSimpleName()+ " circle", new ColorRGBA(0.8f, 0.2f, 0.8f, 1));
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
		if(lastPointed != pointedGeometry)
			circle.setHover(pointedGeometry == circle.getGrip());

		if(chasedSpatial != null){
			drawnNode.setLocalTranslation(chasedSpatial.getLocalTranslation());
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
		if(pointedGeometry == circle.getGrip())
			presenter.grab(grabStart);
		else
			presenter.grab(null);
	}

	public void release(){
		presenter.grab(null);
	}
	
	public void drag(){
		Camera cam = AppFacade.getCamera();
		double camX = chasedSpatial.getWorldTranslation().distance(cam.getLocation())/cam.getWidth();
		double camY = chasedSpatial.getWorldTranslation().distance(cam.getLocation())/cam.getHeight();
		presenter.dragTo(selector.getCoordInScreenSpace(), SpatialSelector.getScreenCoord(TranslateUtil.toPoint3D(chasedSpatial.getWorldTranslation())), camX, camY);
		updateShape();
	}
	
	public void attach(EntityId eid){
		chasedEntity = eid;
		chasedSpatial = SpatialPool.models.get(eid);
		AppFacade.getRootNode().attachChild(drawnNode);
		updateShape();
	}

	public void detach() {
		chasedEntity = null;
		chasedSpatial = null;
		drawnNode.removeFromParent();
	}
	
	private void updateShape(){
		double radius = entityData.getComponent(chasedEntity, CircleCollisionShape.class).getRadius();
		Mesh viewTorus = new Torus(25, 3, 0.03f, (float)radius);
		Mesh gripTorus = new Torus(12, 3, 0.1f, (float)radius);
		circle.setMesh(viewTorus, gripTorus);
	}
	
	private void createSceneTools(){
		Mesh viewTorus = new Torus(25, 3, 0.03f, 1.8f);
		Mesh gripTorus = new Torus(12, 3, 0.1f, 1.8f);
		circle.setMesh(viewTorus, gripTorus);
		
		drawnNode.attachChild(circle.getView());
		drawnNode.setQueueBucket(Bucket.Transparent);
		
		gripNode.attachChild(circle.getGrip());
	}
}
