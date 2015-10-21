package util.event;

import model.EntityPresenter;


public class SaveEntityEvent extends Event {
	public final EntityPresenter ep;
	
	public SaveEntityEvent(EntityPresenter ep) {
		this.ep = ep;
	}
}
