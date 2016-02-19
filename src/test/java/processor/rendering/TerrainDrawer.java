package processor.rendering;

import java.util.List;

import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

import commonLogic.SpatialPool;
import commonLogic.TerrainSplatTexture;
import model.tempImport.TranslateUtil;
import model.world.terrain.Terrain;
import model.world.terrain.heightmap.Parcel;
import util.geometry.geom2d.Point2D;

public class TerrainDrawer {
	private final Terrain terrain;
	private final TerrainSplatTexture groundTexture;
	private final TerrainSplatTexture coverTexture;
	private final Point2D coord;
	
	public Node mainNode;
	public Node castAndReceiveNode = new Node("Cast & receive shadow");
	public Node receiveNode = new Node("Receive shadow node");
	public boolean rendered = false;
	
	public TerrainDrawer(Terrain terrain, Point2D coord) {
		mainNode = new Node("Terrain chunk at "+coord);
		this.terrain = terrain;
		this.coord = coord;
		groundTexture = new TerrainSplatTexture(terrain.getAtlas(), terrain.getTexturing());
		coverTexture = new TerrainSplatTexture(terrain.getCover(), terrain.getTexturing());
		coverTexture.transp = true;
		
		castAndReceiveNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
		receiveNode.setShadowMode(RenderQueue.ShadowMode.Receive);
		mainNode.attachChild(castAndReceiveNode);
		mainNode.attachChild(receiveNode);
	}

	public void render() {
		if(rendered)
			return;
		rendered = true;
		for (Parcel parcel : terrain.getParcelling().getAll()) {
			Geometry g = new Geometry("Parcel "+parcel.getIndex());
			Mesh jmeMesh = TranslateUtil.toJMEMesh(parcel.getMesh());
			//SilentTangentBinormalGenerator.generate(jmeMesh);
			g.setMesh(jmeMesh);
			g.setMaterial(groundTexture);
//			g.setLocalTranslation(new Vector3f(5, 0, 0));
			g.setQueueBucket(Bucket.Opaque);

			//g.addControl(new RigidBodyControl(0));
			SpatialPool.terrainParcels.put(parcel, g);
			castAndReceiveNode.attachChild(g);
			
			//AppFacade.getApp().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(g);

//			Geometry g2 = new Geometry("Cover parcel "+parcel.getIndex());
//			g2.setMesh(jmeMesh);
//			g2.setMaterial(coverTexture.getMaterial());
//			g2.setQueueBucket(Bucket.Transparent);
////			g2.setLocalTranslation(TranslateUtil.toVector3f(coord));
//			g2.setLocalTranslation(0, 0, 0.01f);
//			SpatialPool.coverParcels.put(parcel, g2);
////			castAndReceiveNode.attachChild(g2);
		}
	}

	public void updateParcels(List<Parcel> parcels) {
		for (Parcel parcel : parcels) {
			Mesh jmeMesh = TranslateUtil.toJMEMesh(parcel.getMesh());
			//SilentTangentBinormalGenerator.generate(jmeMesh);
			Geometry g = ((Geometry) SpatialPool.terrainParcels.get(parcel));
			g.setMesh(jmeMesh);

//			AppFacade.getApp().getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(g);
//			AppFacade.getApp().getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(g);

//			Geometry g2 = ((Geometry) SpatialPool.coverParcels.get(parcel));
//			g2.setMesh(jmeMesh);
		}
	}

	public void updateAtlas() {
		groundTexture.updateAlpha();
		//coverTexture.updateAlpha();
	}

	public void updateTexturing() {
		groundTexture.updateTextures();
		//coverTexture.updateTextures();
	}
}
