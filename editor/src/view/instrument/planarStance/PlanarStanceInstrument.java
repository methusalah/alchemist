package view.instrument.planarStance;

import com.simsilica.es.EntityId;

import presenter.EditorPlatform;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import view.controls.JmeImageView;

public class PlanarStanceInstrument {
	private final PlanarStanceInstrumentPresenter presenter;
	private final PlanarStanceInstrumentInputListener inputListener;
	private final JmeImageView jme;
	private PlanarStanceInstrumentState state;
	
	public PlanarStanceInstrument(JmeImageView jme) {
		this.jme = jme;
		presenter = new PlanarStanceInstrumentPresenter(this);
		jme.enqueue(app -> {
			if(app.getStateManager().getState(PlanarStanceInstrumentState.class) == null){
				state = new PlanarStanceInstrumentState(presenter);
			}
			return true;
		});
		inputListener = new PlanarStanceInstrumentInputListener(jme);
	}
	
	public void showOn(EntityId eid){
		jme.enqueue(app -> {
			app.getStateManager().attach(state);
			return true;
		});
		EditorPlatform.getSceneInputManager().addListener(inputListener);
	}
	
	public void hide(){
		jme.enqueue(app -> {
			app.getStateManager().detach(state);
			return true;
		});
		EditorPlatform.getSceneInputManager().removeListener(inputListener);
	}

}
