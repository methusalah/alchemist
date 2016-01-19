package view.instrument.planarStance;

import presenter.EditorPlatform;
import presenter.instrument.PlanarStanceInstrumentPresenter;
import view.controls.jmeScene.JmeImageView;
import view.instrument.InstrumentInputListener;

public class PlanarStanceInstrument {
	private final PlanarStanceInstrumentPresenter presenter;
	private final InstrumentInputListener inputListener;
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
		inputListener = new InstrumentInputListener(jme, PlanarStanceInstrumentState.class);
	}
	
	public void setVisible(boolean value){
		if(value && !EditorPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().attach(state));
			EditorPlatform.getSceneInputManager().addListener(inputListener);
		} else if(!value && EditorPlatform.getSceneInputManager().hasListener(inputListener)){
			jme.enqueue(app -> app.getStateManager().detach(state));
			EditorPlatform.getSceneInputManager().removeListener(inputListener);
		}
	}
}
