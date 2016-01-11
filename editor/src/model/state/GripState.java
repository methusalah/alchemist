package model.state;


import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import app.AppFacade;
import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;
import model.ES.component.Naming;
import util.LogUtil;
import util.geometry.geom2d.Point2D;
import util.math.AngleUtil;
import view.SpatialPool;
import view.jme.Cone;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import view.mesh.Circle;

public class GripState extends AbstractAppState {
	private SceneSelectorState selector;
	private EntityData entityData;
	
	Geometry x, y, z, yaw, pitch, roll, xScale, yScale, zScale, pointedGeometry, grabbedGeom = null;
	
	public GripState(EntityData entityData) {
		this.entityData = entityData;
		x = new Geometry(GripState.class.getSimpleName()+ " x translator");
		x.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 2, 12)));
		x.setMaterial(MaterialManager.getColor(ColorRGBA.Yellow));
		x.setLocalTranslation(2, 0, 0);
		x.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		y = new Geometry(GripState.class.getSimpleName()+ " y translator");
		y.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 2, 12)));
		y.setMaterial(MaterialManager.getColor(ColorRGBA.Red));
		y.setLocalTranslation(0, 2, 0);
		y.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		z = new Geometry(GripState.class.getSimpleName()+ " z translator");
		z.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 2, 12)));
		z.setMaterial(MaterialManager.getColor(ColorRGBA.Blue));
		z.setLocalTranslation(0, 0, 2);
		
		roll = new Geometry(GripState.class.getSimpleName()+ " roll");
		roll.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		roll.setMaterial(MaterialManager.getColor(ColorRGBA.Yellow));
		roll.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));

		pitch = new Geometry(GripState.class.getSimpleName()+ " pitch");
		pitch.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		pitch.setMaterial(MaterialManager.getColor(ColorRGBA.Red));
		pitch.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));

		yaw = new Geometry(GripState.class.getSimpleName()+ " yaw");
		yaw.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		yaw.setMaterial(MaterialManager.getColor(ColorRGBA.Blue));

//		AppFacade.getRootNode().attachChild(x);
//		AppFacade.getRootNode().attachChild(y);
//		AppFacade.getRootNode().attachChild(z);
//		
//		AppFacade.getRootNode().attachChild(roll);
//		AppFacade.getRootNode().attachChild(pitch);
//		AppFacade.getRootNode().attachChild(yaw);
	}

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		selector = stateManager.getState(SceneSelectorState.class);
		entityData = stateManager.getState(DataState.class).getEntityData();
	}
	
	@Override
	public void update(float tpf) {
		pointedGeometry = selector.getPointedGeometry();
	}

	public void grab(){
		grabbedGeom = pointedGeometry;
	}

	public void release(){
		grabbedGeom = null;
	}
	
	public void drag(Point2D screenCoord){
		if(grabbedGeom == null)
			return;
		LogUtil.info("dragging " + grabbedGeom + " at "+screenCoord);
	}
	
	public void attach(EntityId eid){
		Spatial s = SpatialPool.models.get(eid);
		if(s instanceof Node){
			((Node)s).attachChild(x);
			((Node)s).attachChild(y);
			((Node)s).attachChild(z);
		}
		
		LogUtil.info("attached to "+entityData.getComponent(eid, Naming.class).getName());
	}

	public void detach() {
		x.removeFromParent();
		y.removeFromParent();
		z.removeFromParent();
	}
}
