package model.ES.component.command;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import model.ES.richData.Angle;
import model.ES.richData.ColorData;

public class PlayerControl implements EntityComponent{
	public ColorData color = new ColorData(255, 150, 0, 0);
	public Angle angledelamortquitue = new Angle(3);
	public Angle getAngledelamortquitue() {
		return angledelamortquitue;
	}

	public List<Double>  doubles = new ArrayList<Double>() {{
	    add(1d);
	    add(3d);
	    add(8d);
	}};

	public List<Double> getDoubles() {
		return doubles;
	}

	public ColorData getColor() {
		return color;
	}

	public void setDoubles(List<Double> doubles) {
		this.doubles = doubles;
	}
}
