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
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.SpatialSelector;
import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;
import model.ES.component.motion.PlanarStance;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.geometry.geom3d.Point3D;
import util.math.AngleUtil;
import view.SpatialPool;
import view.jme.Cone;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import view.mesh.Circle;

public class GripState extends AbstractAppState {
	private SceneSelectorState selector;
	private EntityData entityData;
	
	
	
	Node node = new Node("Grip node");
	Spatial attachedTo = null;
	EntityId eid;
	double scale = 1;
	
	
	Point2D dragStart = null;
	Point3D grabPoint = null;
	Spatial grabbed = null;
	Geometry x, y, z, yaw, pitch, roll, xScale, yScale, zScale, pointedGeometry;
	
	public GripState(EntityData entityData) {
		this.entityData = entityData;
		x = new Geometry(GripState.class.getSimpleName()+ " x translator");
		x.setMesh(TranslateUtil.toJMEMesh(new Cone(0.3, 0.5, 12)));
		x.setMaterial(MaterialManager.getColor(new ColorRGBA(0.8f, 0.8f, 0.2f, 1)));
		x.setLocalTranslation(2, 0, 0);
		x.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		y = new Geometry(GripState.class.getSimpleName()+ " y translator");
		y.setMesh(TranslateUtil.toJMEMesh(new Cone(0.3, 0.5, 12)));
		y.setMaterial(MaterialManager.getColor(new ColorRGBA(0.8f, 0.2f, 0.2f, 1)));
		y.setLocalTranslation(0, 2, 0);
		y.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		z = new Geometry(GripState.class.getSimpleName()+ " z translator");
		z.setMesh(TranslateUtil.toJMEMesh(new Cone(0.3, 0.5, 12)));
		z.setMaterial(MaterialManager.getColor(new ColorRGBA(0.2f, 0.8f, 0.2f, 1)));
		z.setLocalTranslation(0, 0, 2);
		
		roll = new Geometry(GripState.class.getSimpleName()+ " roll");
		roll.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		roll.setMaterial(MaterialManager.getColor(new ColorRGBA(0.8f, 0.8f, 0.2f, 1)));
		roll.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));

		pitch = new Geometry(GripState.class.getSimpleName()+ " pitch");
		pitch.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		pitch.setMaterial(MaterialManager.getColor(new ColorRGBA(0.8f, 0.2f, 0.2f, 1)));
		pitch.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));

		yaw = new Geometry(GripState.class.getSimpleName()+ " yaw");
		yaw.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		yaw.setMaterial(MaterialManager.getColor(new ColorRGBA(0.2f, 0.8f, 0.2f, 1)));
		
		node.attachChild(x);
		node.attachChild(y);
		node.attachChild(z);
		node.attachChild(yaw);
		node.attachChild(pitch);
		node.attachChild(roll);
		node.setQueueBucket(Bucket.Translucent);
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
		entityData = stateManager.getState(DataState.class).getEntityData();
	}
	
	@Override
	public void update(float tpf) {
		pointedGeometry = SpatialSelector.getPointedGeometry(node, selector.getCoordInScreenSpace());
		if(attachedTo != null){
			node.setLocalScale(AppFacade.getCamera().getLocation().distance(attachedTo.getWorldTranslation())/20);
			node.setLocalTranslation(attachedTo.getLocalTranslation());
			PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
			if(stance != null){
				node.setLocalRotation(new Quaternion().fromAngles(0, 0, (float)stance.getOrientation().getValue()));
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
		Point2D v = screenCoord.getSubtraction(dragStart).getDivision(10);
		dragStart = screenCoord;
		
		PlanarStance stance = entityData.getComponent(eid, PlanarStance.class);
		LogUtil.info("drag : " + grabbed + stance);
		if(stance != null){
			if(grabbed == x){
				LogUtil.info("x tr : " + v);
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
			}
		}
	}
	
	public void attach(EntityId eid){
		this.eid = eid;
		attachedTo = SpatialPool.models.get(eid);
		AppFacade.getRootNode().attachChild(node);
	}

	public void detach() {
		this.eid = null;
		attachedTo = null;
		node.removeFromParent();
		
	}
}
