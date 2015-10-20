package util.event;

import model.EntityPresenter;

public class EntitySelectionChanged extends Event {
	public final EntityPresenter ep;
	
	public EntitySelectionChanged(EntityPresenter ep) {
		this.ep = ep;
	}
}
