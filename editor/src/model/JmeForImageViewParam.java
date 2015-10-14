package model;

import java.util.concurrent.Future;
import java.util.function.Function;

import javafx.scene.image.ImageView;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeContextOffscreenSurface;
import com.jme3x.jfx.injfx.SceneProcessorCopyToImageView;

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
public class JmeForImageViewParam {
	
	private final SimpleApplication app;
	private SceneProcessorCopyToImageView jmeAppDisplayBinder = new SceneProcessorCopyToImageView();

	public JmeForImageViewParam(SimpleApplication app, int framerate){
		AppSettings settings = new AppSettings(true);

		// important to use those settings
		settings.setFullscreen(false);
		settings.setUseInput(false);
		settings.setFrameRate(Math.max(1, Math.min(60, framerate)));
		settings.setCustomRenderer(JmeContextOffscreenSurface.class);

		this.app = app;
		app.setSettings(settings);
		app.setShowSettings(false);
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
		SimpleApplication jmeApp = app;
		return jmeApp.enqueue(() -> {return f.apply(jmeApp);});
	}

	public void stop(boolean waitFor) {
		if (app != null){
			try {
				unbind().get();
			} catch (Exception exc) {
				//TODO
				exc.printStackTrace();
			}
			app.stop(waitFor);
		}
	}
}