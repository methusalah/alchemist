package com.brainless.alchemist.view.tab.scene.customControl;

import java.util.concurrent.Future;
import java.util.function.Function;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.SceneProcessorCopyToImageView;

import javafx.scene.image.ImageView;

/**
 * JmeForImageView create a jme'SimpleApplication viewable into an JavaFX's ImageView.
 *
 * You can manage the wrapped SimpleApplication by calling enqueue (by example to add/remove
 * AppState, Node, Light, to change the AppSettings...).
 *
 * See TestDisplayInImageView.java for sample usage.
 *
 * The usage of the class is optional, it can avoid some
 * pitfall in the configuration. If you want a better control, I suggest you to browse
 * the source of this class to see sample of configuration and usage of
 * SceneProcessorCopyToImage.
 *
 * @TODO auto-stop when the ImageView is removed from JavaFX Stage
 * @author davidB
 */
public class JmeImageView {
	
	private final SimpleApplication app;
	private SceneProcessorCopyToImageView jmeAppDisplayBinder = new SceneProcessorCopyToImageView();

	public JmeImageView() {
		app = makeJmeApplication(30);
		app.start();
	}

	private static SimpleApplication makeJmeApplication(int framerate) {
		AppSettings settings = new AppSettings(true);

		// important to use those settings
//		settings.putBoolean("GraphicsDebug", true);
		settings.setFullscreen(false);
		settings.setUseInput(false);
		settings.setFrameRate(Math.max(1, Math.min(60, framerate)));
		settings.setCustomRenderer(com.jme3x.jfx.injfx.JmeContextOffscreenSurface.class);

		SimpleApplication app = new SimpleApplication(){
			@Override
			public void simpleInitApp() {
				// to prevent a NPE (due to setUseInput(null)) on Application.stop()
				getStateManager().detach(getStateManager().getState(DebugKeysAppState.class));
			}
		};
		app.setSettings(settings);
		app.setShowSettings(false);
		return app;
	}

	/**
	 * Bind the wrapped SimpleApplication to an imageView.
	 *
	 * <ul>
	 * <li>Only one imageView can be binded.</li>
	 * <li>Only jmeApp.getViewPort(), jmeApp.getGuiViewPort() are binded</li>
	 * </ul>
	 *
	 * @param imageView destination
	 * @return Future when bind is done (async)
	 */
	public Future<Boolean> bind(ImageView imageView) {
		return enqueue((jmeApp) -> {
			jmeAppDisplayBinder.bind(imageView, jmeApp);
			return true;
		});
	}

	public Future<Boolean> unbind() {
		return enqueue((jmeApp) -> {
			jmeAppDisplayBinder.unbind();
			return true;
		});
	}

	/**
	 * Enqueue action to apply in Jme's Thread
	 * Action can be add/remove AppState, Node, Light,
	 * change the AppSettings....
	 *
	 * @param f(jmeApp) the action to apply
	 */
	public <R> Future<R> enqueue(Function<SimpleApplication,R> f) {
		return app.enqueue(() -> {return f.apply(app);});
	}

	public void enqueue(Runnable r) {
		app.enqueue(() -> {r.run(); return true;});
	}

	public void stop(boolean waitFor) {
		try {
			unbind().get();
		} catch (Exception exc) {
			//TODO
			exc.printStackTrace();
		}
		app.stop(waitFor);
	}

	public SimpleApplication getApp() {
		return app;
	}
	
	
}