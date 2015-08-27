/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package view.mapDrawing;

import model.ModelManager;
import util.annotation.RootNodeRef;
import view.math.TranslateUtil;
import app.AppFacade;

import com.google.inject.Inject;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.post.ssao.SSAOFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowFilter;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;

/**
 * @author Benoît
 */
public class LightDrawer {
	AmbientLight al;
	DirectionalLight sun;
	DirectionalLight shadowCaster;
	DirectionalLightShadowRenderer sr;
	DirectionalLightShadowFilter sf;

	public LightDrawer() {
		FilterPostProcessor fpp = new FilterPostProcessor(AppFacade.getAssetManager());

		int SHADOWMAP_SIZE = 4096;
//		sr = new DirectionalLightShadowRenderer(am, SHADOWMAP_SIZE, 1);
//		sr.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
//		sr.setShadowIntensity((float) ModelManager.getBattlefield().getSunLight().shadowCaster.intensity);
//		vp.addProcessor(sr);

		sf = new DirectionalLightShadowFilter(AppFacade.getAssetManager(), SHADOWMAP_SIZE, 1);
		sf.setEnabled(true);
		sf.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
		sf.setShadowZExtend(SHADOWMAP_SIZE);
		fpp.addFilter(sf);

		// Ambiant occlusion filter
		SSAOFilter ssaoFilter = new SSAOFilter(0.5f, 4f, 0.2f, 0.3f);
		// fpp.addFilter(ssaoFilter);
		// Glow filter
		BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
		fpp.addFilter(bloom);
		AppFacade.getViewPort().addProcessor(fpp);
	}
	
	public void Initialize(){
		al = new AmbientLight();
		
		sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-1, -1, -1));
		
		sf.setLight(sun);
		
		AppFacade.getRootNode().addLight(al);
		AppFacade.getRootNode().addLight(sun);
	}

	public void reset() {
		AppFacade.getRootNode().removeLight(al);
		AppFacade.getRootNode().removeLight(sun);
		AppFacade.getRootNode().removeLight(shadowCaster);

		al = TranslateUtil.toJMELight(ModelManager.getBattlefield().getSunLight().ambient);
		sun = TranslateUtil.toJMELight(ModelManager.getBattlefield().getSunLight().sun);
		shadowCaster = TranslateUtil.toJMELight(ModelManager.getBattlefield().getSunLight().shadowCaster);
//		sr.setLight(shadowCaster);
		sf.setLight(shadowCaster);

		
		updateLights();
	}

	public void updateLights() {
		TranslateUtil.toJMELight(al, ModelManager.getBattlefield().getSunLight().ambient);
		TranslateUtil.toJMELight(sun, ModelManager.getBattlefield().getSunLight().sun);
		TranslateUtil.toJMELight(shadowCaster, ModelManager.getBattlefield().getSunLight().shadowCaster);
		shadowCaster.setColor(ColorRGBA.Blue.mult(0));
//		sr.setShadowIntensity((float) ModelManager.getBattlefield().getSunLight().shadowCaster.intensity);
		sf.setShadowIntensity((float) ModelManager.getBattlefield().getSunLight().shadowCaster.intensity);

	}

}
