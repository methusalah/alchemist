package model.world.terrain.event;

import java.util.List;

import model.world.terrain.heightmap.Parcel;
import util.event.Event;

public class ParcelChangedEvent extends Event {

	private final List<Parcel> toUpdate;

	public ParcelChangedEvent(List<Parcel> toUpdate) {
		this.toUpdate = toUpdate;
	}

	public List<Parcel> getParcels() {
		return toUpdate;
	}

}
