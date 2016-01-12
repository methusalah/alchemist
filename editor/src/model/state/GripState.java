package model.state;


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
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
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.Angle;
import util.math.AngleUtil;
import view.SpatialPool;
import view.jme.Cone;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import view.mesh.Circle;

public class GripState extends AbstractAppState {
	
	private static final ColorRGBA xColor = new ColorRGBA(0.8f, 0.8f, 0.2f, 1);
	private static final ColorRGBA yColor = new ColorRGBA(0.8f, 0.2f, 0.2f, 1);
	private static final ColorRGBA zColor = new ColorRGBA(0.2f, 0.2f, 0.8f, 1);
	private static final ColorRGBA xyColor = new ColorRGBA(0.5f, 0.2f, 0.2f, 1);
	
	private SceneSelectorState selector;
	private EntityData entityData;
	
	Node drawnNode = new Node("Grip visual node");
	Node gripNode = new Node("Grip grabbable node");
	Spatial attachedTo = null;
	EntityId eid;
	double scale = 1;
	
	Point2D dragStart = null;
	Point3D grabPoint = null;
	Spatial grabbed = null;
	private final Geometry xView, yView, zView, xyView, yawView, 
		x, y, z, xy, yaw;
	private Geometry pointedGeometry;
	
	public GripState(EntityData entityData) {
		this.entityData = entityData;
		xView = new Geometry(GripState.class.getSimpleName()+ " x translator view");
		yView = new Geometry(GripState.class.getSimpleName()+ " y translator view");
		zView = new Geometry(GripState.class.getSimpleName()+ " z translator view");
		xyView = new Geometry(GripState.class.getSimpleName()+ " xy translator view");
		yawView = new Geometry(GripState.class.getSimpleName()+ " yaw view");
		x = new Geometry(GripState.class.getSimpleName()+ " x translator grip");
		y = new Geometry(GripState.class.getSimpleName()+ " y translator grip");
		z = new Geometry(GripState.class.getSimpleName()+ " z translator grip");
		xy = new Geometry(GripState.class.getSimpleName()+ " xy translator grip");
		yaw = new Geometry(GripState.class.getSimpleName()+ " yaw grip");
		buildView();
		buildGrip();
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
			if(lastPointed == x)
				xView.getMaterial().clearParam("GlowColor");
			if(lastPointed == y)
				yView.getMaterial().clearParam("GlowColor");
			if(lastPointed == z)
				zView.getMaterial().clearParam("GlowColor");
			if(lastPointed == xy)
				xyView.getMaterial().clearParam("GlowColor");
			if(lastPointed == yaw)
				yawView.getMaterial().clearParam("GlowColor");
			
			if(pointedGeometry == x)
				xView.getMaterial().setColor("GlowColor", (ColorRGBA)(xView.getMaterial().getParam("Color").getValue()));
			if(pointedGeometry == y)
				yView.getMaterial().setColor("GlowColor", (ColorRGBA)(yView.getMaterial().getParam("Color").getValue()));
			if(pointedGeometry == z)
				zView.getMaterial().setColor("GlowColor", (ColorRGBA)(zView.getMaterial().getParam("Color").getValue()));
			if(pointedGeometry == xy)
				xyView.getMaterial().setColor("GlowColor", (ColorRGBA)(xyView.getMaterial().getParam("Color").getValue()));
			if(pointedGeometry == yaw)
				yawView.getMaterial().setColor("GlowColor", (ColorRGBA)(yawView.getMaterial().getParam("Color").getValue()));
		}

		if(attachedTo != null){
			drawnNode.setLocalScale(AppFacade.getCamera().getLocation().distance(attachedTo.getWorldTranslation())/20);
			drawnNode.setLocalTranslation(attachedTo.getLocalTranslation());
			gripNode.setLocalScale(AppFacade.getCamera().getLocation().distance(attachedTo.getWorldTranslation())/20);
			gripNode.setLocalTranslation(attachedTo.getLocalTranslation());
			PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
			if(stance != null){
				drawnNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)stance.getOrientation().getValue()));
				gripNode.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)stance.getOrientation().getValue()));
			}
		}
	}

	public void grab(Point2D screenCoord){
		grabbed = pointedGeometry;
		dragStart = screenCoord;
	}

	public void release(){
		grabbed = null;
	}
	
	public void drag(Point2D screenCoord){
		if(grabbed == null)
			return;
		Point2D v = screenCoord.getSubtraction(dragStart).getDivision(50);
		
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
		if(stance != null){
			if(grabbed == x){
				v = v.getRotation(-stance.getOrientation().getValue());
				v = new Point2D(v.x, 0);
				v = v.getRotation(stance.getOrientation().getValue());
				entityData.setComponent(eid, new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			} else if(grabbed == y) {
				v = v.getRotation(-stance.getOrientation().getValue()-AngleUtil.RIGHT);
				v = new Point2D(v.x, 0);
				v = v.getRotation(stance.getOrientation().getValue()+AngleUtil.RIGHT);
				entityData.setComponent(eid, new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			} else if(grabbed == z) {
				entityData.setComponent(eid, new PlanarStance(stance.getCoord(), stance.getOrientation(), stance.getElevation()+(v.x+v.y)/2, stance.getUpVector()));
			} else if(grabbed == xy) {
				entityData.setComponent(eid, new PlanarStance(stance.getCoord().getAddition(v), stance.getOrientation(), stance.getElevation(), stance.getUpVector()));
			} else if(grabbed == yaw) {
				Point2D spatialCoord = SpatialSelector.getScreenCoord(TranslateUtil.toPoint3D(grabbed.getWorldTranslation()));
				Point2D grabVec = dragStart.getSubtraction(spatialCoord);
				Point2D targetVec = screenCoord.getSubtraction(spatialCoord);
				double delta = AngleUtil.getOrientedDifference(grabVec.getAngle(), targetVec.getAngle());
				entityData.setComponent(eid, new PlanarStance(stance.getCoord(), new Angle(stance.getOrientation().getValue()+delta), stance.getElevation(), stance.getUpVector()));
			}
		}
		dragStart = screenCoord;
	}
	
	public void attach(EntityId eid){
		this.eid = eid;
		attachedTo = SpatialPool.models.get(eid);
		AppFacade.getRootNode().attachChild(drawnNode);
	}

	public void detach() {
		this.eid = null;
		attachedTo = null;
		drawnNode.removeFromParent();
	}
	
	private void buildView(){
		xView.setMesh(TranslateUtil.toJMEMesh(new Cone(0.2, 0.3, 12)));
		xView.setMaterial(MaterialManager.getColor(xColor));
		xView.getMaterial().getAdditionalRenderState().setDepthTest(false);
		xView.setLocalTranslation(2, 0, 0);
		xView.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		yView.setMesh(TranslateUtil.toJMEMesh(new Cone(0.2, 0.3, 12)));
		yView.setMaterial(MaterialManager.getColor(yColor));
		yView.getMaterial().getAdditionalRenderState().setDepthTest(false);
		yView.setLocalTranslation(0, 2, 0);
		yView.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		zView.setMesh(new Box(0.05f, 0.03f, 2));
		zView.setMaterial(MaterialManager.getColor(zColor));
		zView.getMaterial().getAdditionalRenderState().setDepthTest(false);

		xyView.setMesh(new Box(0.2f, 0.2f, 0.001f));
		xyView.setMaterial(MaterialManager.getColor(xyColor));
		xyView.getMaterial().getAdditionalRenderState().setDepthTest(false);
		xyView.setLocalTranslation(0.2f, 0.2f, 0);
		
		yawView.setMesh(new Torus(25, 3, 0.03f, 1.8f));
		yawView.setMaterial(MaterialManager.getColor(zColor));
		yawView.getMaterial().getAdditionalRenderState().setDepthTest(false);
		
		drawnNode.attachChild(xView);
		drawnNode.attachChild(yView);
		drawnNode.attachChild(zView);
		drawnNode.attachChild(xyView);
		drawnNode.attachChild(yawView);
		drawnNode.setQueueBucket(Bucket.Transparent);
	}
	private void buildGrip(){
		x.setMesh(TranslateUtil.toJMEMesh(new Cone(0.3, 0.5, 12)));
		x.setLocalTranslation(2, 0, 0);
		x.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		y.setMesh(TranslateUtil.toJMEMesh(new Cone(0.3, 0.5, 12)));
		y.setLocalTranslation(0, 2, 0);
		y.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		z.setMesh(new Box(0.05f, 0.05f, 2));
		
		xy.setMesh(new Box(0.2f, 0.2f, 0.001f));
		xy.setLocalTranslation(0.2f, 0.2f, 0);

		yaw.setMesh(new Torus(12, 3, 0.1f, 1.8f));
		
		gripNode.attachChild(x);
		gripNode.attachChild(y);
		gripNode.attachChild(z);
		gripNode.attachChild(xy);
		gripNode.attachChild(yaw);
	}
}
