package com.brainless.alchemist.model.tempImport;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.SceneProcessor;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;

public class RendererPlatform {

	private static SimpleApplication app;
	private static Node mainSceneNode = new Node();
	private static Node instrumentNode = new Node();
	
	public static void setApp(SimpleApplication application){
		app = application;
		app.getRootNode().attachChild(mainSceneNode);
		app.getRootNode().attachChild(instrumentNode);
		AmbientLight l = new AmbientLight();
		l.setColor(ColorRGBA.Blue);
		instrumentNode.addLight(l);
		instrumentNode.setShadowMode(ShadowMode.Off);
	}
	
	public static Node getMainSceneNode(){
		return mainSceneNode;
	}

	public static Node getInstrumentNode(){
		return instrumentNode;
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
	
	public static FilterPostProcessor getFilterPostProcessor(){
		for(SceneProcessor proc : getViewPort().getProcessors())
			if(proc instanceof FilterPostProcessor)
				return (FilterPostProcessor)proc;
		throw new RuntimeException("No FilterPostProcessor on that scene.");
	}
	
	public static void enqueue(Callable<?> callable){
		app.enqueue(callable);
	}

	public static void enqueue(Runnable runnable){
		app.enqueue(() -> {runnable.run(); return true;});
	}
	
	public static void enqueue(Consumer<SimpleApplication> consumer) {
		app.enqueue(() -> {consumer.accept(app); return true;});
	}

}
