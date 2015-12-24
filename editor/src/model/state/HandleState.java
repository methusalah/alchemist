package model.state;

import util.math.AngleUtil;
import view.jme.Cone;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import view.mesh.Circle;
import app.AppFacade;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.simsilica.es.EntityData;

import controller.ECS.DataState;
import controller.ECS.SceneSelectorState;

public class HandleState extends AbstractAppState {
	private SceneSelectorState selector;
	private EntityData entityData;
	Geometry x, y, z, yaw, pitch, roll, xScale, yScale, zScale, pointedGeometry;
	
	public HandleState(EntityData entityData) {
		this.entityData = entityData;
		x = new Geometry(HandleState.class.getSimpleName()+ " x translator");
		x.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 0.3, 12)));
		x.setMaterial(MaterialManager.getColor(ColorRGBA.Yellow));
		x.setLocalTranslation(2, 0, 0);
		x.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));
		
		y = new Geometry(HandleState.class.getSimpleName()+ " y translator");
		y.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 0.3, 12)));
		y.setMaterial(MaterialManager.getColor(ColorRGBA.Red));
		y.setLocalTranslation(0, 2, 0);
		y.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));
		
		z = new Geometry(HandleState.class.getSimpleName()+ " z translator");
		z.setMesh(TranslateUtil.toJMEMesh(new Cone(0.1, 0.5, 12)));
		z.setMaterial(MaterialManager.getColor(ColorRGBA.Blue));
		z.setLocalTranslation(0, 0, 2);
		
		roll = new Geometry(HandleState.class.getSimpleName()+ " roll");
		roll.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		roll.setMaterial(MaterialManager.getColor(ColorRGBA.Yellow));
		roll.setLocalRotation(new Quaternion().fromAngles(0, (float) AngleUtil.RIGHT, 0));

		pitch = new Geometry(HandleState.class.getSimpleName()+ " pitch");
		pitch.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		pitch.setMaterial(MaterialManager.getColor(ColorRGBA.Red));
		pitch.setLocalRotation(new Quaternion().fromAngles((float) -AngleUtil.RIGHT, 0, 0));

		yaw = new Geometry(HandleState.class.getSimpleName()+ " yaw");
		yaw.setMesh(new Circle(Vector3f.ZERO, 1.8f, 12, 6));
		yaw.setMaterial(MaterialManager.getColor(ColorRGBA.Blue));

		AppFacade.getRootNode().attachChild(x);
		AppFacade.getRootNode().attachChild(y);
		AppFacade.getRootNode().attachChild(z);
		
		AppFacade.getRootNode().attachChild(roll);
		AppFacade.getRootNode().attachChild(pitch);
		AppFacade.getRootNode().attachChild(yaw);
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
}
