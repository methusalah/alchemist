package view.instrument.circleCollisionShape;

import com.simsilica.es.EntityId;

import presenter.EditorPlatform;
import presenter.instrument.CircleCollisionShapeInstrumentPresenter;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import view.controls.JmeImageView;

public class CircleCollisionShapeInstrument {
	private final CircleCollisionShapeInstrumentPresenter presenter;
	private final CircleCollisionShapeInstrumentInputListener inputListener;
	private final JmeImageView jme;
	
	public CircleCollisionShapeInstrument(JmeImageView jme) {
		this.jme = jme;
		presenter = new CircleCollisionShapeInstrumentPresenter(this);
		jme.enqueue(app -> {
			if(app.getStateManager().getState(CircleCollisionShapeInstrumentState.class) == null)
				app.getStateManager().attach(new CircleCollisionShapeInstrumentState(presenter));
			return true;
		});
		inputListener = new CircleCollisionShapeInstrumentInputListener(jme);
	}
	
	public void showOn(EntityId eid){
		jme.enqueue(app -> {
			app.getStateManager().getState(CircleCollisionShapeInstrumentState.class).attach(eid);
			return true;
		});
		EditorPlatform.getSceneInputManager().addListener(inputListener);
	}
	
	public void hide(){
		jme.enqueue(app -> {
			app.getStateManager().getState(CircleCollisionShapeInstrumentState.class).detach();
			return true;
		});
		EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}

}
