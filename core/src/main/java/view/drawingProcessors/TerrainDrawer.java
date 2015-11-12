package view.drawingProcessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import model.ModelManager;
import model.ES.component.world.TerrainTexturing;
import model.world.terrain.Parcel;
import model.world.terrain.Terrain;
import util.event.EventManager;
import view.SpatialPool;
import view.jme.SilentTangentBinormalGenerator;
import view.jme.TerrainSplatTexture;
import view.material.MaterialManager;
import view.math.TranslateUtil;
import app.AppFacade;

import com.google.common.eventbus.Subscribe;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

import controller.ECS.Processor;

public class TerrainDrawer {
	private final Terrain terrain;
	private final TerrainTexturing texturing;
	private final TerrainSplatTexture groundTexture;
	private final TerrainSplatTexture coverTexture;

	public Node mainNode = new Node();
	public Node castAndReceiveNode = new Node();
	public Node receiveNode = new Node();

	public PhysicsSpace mainPhysicsSpace = new PhysicsSpace();

	public TerrainDrawer(Terrain terrain, TerrainTexturing texturing) {
		this.terrain = terrain;
		this.texturing = texturing;
		groundTexture = new TerrainSplatTexture(terrain.getAtlas());
		coverTexture = new TerrainSplatTexture(terrain.getCover());
		coverTexture.transp = true;
		
		castAndReceiveNode.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
		receiveNode.setShadowMode(RenderQueue.ShadowMode.Receive);
		mainNode.attachChild(castAndReceiveNode);
		mainNode.attachChild(receiveNode);
		AppFacade.getRootNode().attachChild(mainNode);
		
		EventManager.register(this);
	}

	public void renderTiles() {
		int index = 0;
		for (String s : texturing.getDiffuses()) {
			Texture diffuse = AppFacade.getAssetManager().loadTexture(s);
			Texture normal = null;
			if (texturing.getNormals().get(index) != null) {
				normal = AppFacade.getAssetManager().loadTexture(texturing.getNormals().get(index));
			}
			double scale = texturing.getScales().get(index);

			groundTexture.addTexture(diffuse, normal, scale);
			index++;
		}
		groundTexture.buildMaterial();

		index = 0;
		for (String s : texturing.getCoverDiffuses()) {
			Texture diffuse = AppFacade.getAssetManager().loadTexture(s);
			Texture normal = null;
			if (texturing.getCoverNormals().get(index) != null) {
				normal = AppFacade.getAssetManager().loadTexture(texturing.getCoverNormals().get(index));
			}
			double scale = texturing.getCoverScales().get(index);

			coverTexture.addTexture(diffuse, normal, scale);
			index++;
		}
		coverTexture.buildMaterial();

		for (Parcel parcel : terrain.getParcelling().getAll()) {
			Geometry g = new Geometry();
			Mesh jmeMesh = TranslateUtil.toJMEMesh(parcel.getMesh());
			SilentTangentBinormalGenerator.generate(jmeMesh);
			g.setMesh(jmeMesh);
			g.setMaterial(groundTexture.getMaterial());
//			g.setQueueBucket(Bucket.Transparent);

			g.addControl(new RigidBodyControl(0));
			SpatialPool.terrainParcels.put(parcel, g);
			castAndReceiveNode.attachChild(g);
			mainPhysicsSpace.add(g);

			Geometry g2 = new Geometry();
			g2.setMesh(jmeMesh);
			g2.setMaterial(coverTexture.getMaterial());
			g2.setQueueBucket(Bucket.Transparent);
			g2.setLocalTranslation(0, 0, 0.01f);
			SpatialPool.coverParcels.put(parcel, g2);
			castAndReceiveNode.attachChild(g2);
		}
	}

//	@Subscribe
//	public void handleParcelUpdateEvent(ParcelUpdateEvent e) {
//		updateParcels(e.getToUpdate());
//	}
//
//	@Subscribe
//	public void handleGroundUpdateEvent(UpdateGroundAtlasEvent e) {
//		updateGroundTexture();
//	}

	private void updateGroundTexture() {
		groundTexture.getMaterial();
		coverTexture.getMaterial();
	}

	private void updateParcels(List<Parcel> toUpdate) {
		for (Parcel parcel : toUpdate) {
			Mesh jmeMesh = TranslateUtil.toJMEMesh(parcel.getMesh());
			SilentTangentBinormalGenerator.generate(jmeMesh);
			Geometry g = ((Geometry) SpatialPool.terrainParcels.get(parcel));
			g.setMesh(jmeMesh);
			mainPhysicsSpace.remove(g);
			mainPhysicsSpace.add(g);

			Geometry g2 = ((Geometry) SpatialPool.coverParcels.get(parcel));
			g2.setMesh(jmeMesh);
		}
	}
}
