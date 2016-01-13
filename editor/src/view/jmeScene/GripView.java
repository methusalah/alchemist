package view.jmeScene;

import com.simsilica.es.EntityId;

import presenter.EditorPlatform;
import presenter.GripPresenter;
import view.controls.JmeImageView;

public class GripView {
	private final GripPresenter presenter;
	private final GripInputListener inputListener;
	private final JmeImageView jme;
	
	public GripView(JmeImageView jme) {
		this.jme = jme;
		presenter = new GripPresenter(this);
		jme.enqueue(app -> {
			app.getStateManager().attach(new GripState(presenter));
			return true;
		});
		inputListener = new GripInputListener(jme);
	}
	
	public void showOn(EntityId eid){
		jme.enqueue(app -> {
			app.getStateManager().getState(GripState.class).attach(eid);
			return true;
		});
		EditorPlatform.getSceneInputManager().addListener(inputListener);
	}
	
	public void hide(){
		jme.enqueue(app -> {
			app.getStateManager().getState(GripState.class).detach();
			return true;
		});
		EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}

}
