package presenter;

import model.Command;
import model.ECS.TraversableEntityData;
import model.world.WorldData;
import presenter.util.UserComponentList;

public class OverviewPresenter {
	
	public OverviewPresenter() {
		EditorPlatform.setEntityData(new TraversableEntityData());
		EditorPlatform.setWorldData(new WorldData(EditorPlatform.getEntityData()));
		EditorPlatform.setCommand(new Command());
	}

	public void stopScene(){
		EditorPlatform.getScene().stop(false);
	}
	
	public void setComponentList(UserComponentList compList){
		EditorPlatform.getUserComponentList().setValue(compList);
	}
}
