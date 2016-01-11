package presenter;

import com.simsilica.es.EntityId;

import model.state.GripState;
import presenter.common.HandleInputListener;

public class GripPresenter {
	HandleInputListener inputListener;
	
	public GripPresenter() {
		inputListener = new HandleInputListener(EditorPlatform.getScene());
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().attach(new GripState(EditorPlatform.getEntityData()));
			return true;
		});
		
		
		EditorPlatform.getSelectionProperty().addListener((observable, oldValue, newValue)-> {
			if(newValue != null){
				EditorPlatform.getSceneInputManager().addListener(inputListener);
				activateGrip(newValue.getEntityId());
			} else { 
				EditorPlatform.getSceneInputManager().removeListener(inputListener);
				desactivateGrip();
			}
		});
	}
	
	static private void activateGrip(EntityId eid){
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().getState(GripState.class).attach(eid);
			return true;
		});
	}
	static private void desactivateGrip(){
		EditorPlatform.getScene().enqueue(app -> {
			app.getStateManager().getState(GripState.class).detach();
			return true;
		});
	}
}
