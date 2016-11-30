package plugin.planarStanceInstrument;

import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.brainless.alchemist.view.ViewPlatform;
import com.brainless.alchemist.view.instrument.InstrumentInputListener;
import com.brainless.alchemist.view.tab.scene.customControl.JmeImageView;

public class PlanarStanceInstrument {
	private final PlanarStanceInstrumentPresenter presenter;
	private final InstrumentInputListener inputListener;
	private PlanarStanceInstrumentState state;
	
	public PlanarStanceInstrument() {
		presenter = new PlanarStanceInstrumentPresenter(this);
		RendererPlatform.enqueue(app -> {
			if(app.getStateManager().getState(PlanarStanceInstrumentState.class) == null){
				state = new PlanarStanceInstrumentState(presenter);
			}
		});
		inputListener = new InstrumentInputListener(PlanarStanceInstrumentState.class);
	}
	
	public void setVisible(boolean value){
		if(value && !ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			RendererPlatform.enqueue(app -> app.getStateManager().attach(state));
			ViewPlatform.getSceneInputManager().addListener(inputListener);
		} else if(!value && ViewPlatform.getSceneInputManager().hasListener(inputListener)){
			RendererPlatform.enqueue(app -> app.getStateManager().detach(state));
			ViewPlatform.getSceneInputManager().removeListener(inputListener);
		}
	}
}
