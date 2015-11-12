package app;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.simsilica.es.EntityData;

import model.world.WorldData;

public class AppFacade {

	private static SimpleApplication app;
	
	public static void setApp(SimpleApplication application){
		app = application;
	}
	
	public static Node getRootNode(){
		return app.getRootNode();
	}
	
	public static ViewPort getViewPort(){
		return app.getViewPort();
	}
	
	public static ViewPort getGUIViewPort(){
		return app.getGuiViewPort();
	}
	
	public static AssetManager getAssetManager(){
		return app.getAssetManager();
	}
	
	public static InputManager getInputManager(){
		return app.getInputManager();
	}
	
	public static Camera getCamera(){
		return app.getCamera();
	}
	
	public static SimpleApplication getApp(){
		return app;
	}
	
	public static AppStateManager getStateManager(){
		return app.getStateManager();
	}
	
}
