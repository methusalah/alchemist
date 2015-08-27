package app;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

import de.lessvoid.nifty.Nifty;

public class AppFacade {

	private static CosmoVania app;
	
	public static void setApp(CosmoVania application){
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
	
	public static CosmoVania getApp(){
		return app;
	}
	
	public static Nifty getNifty(){
		return app.getNifty();
	}
	
	
}
