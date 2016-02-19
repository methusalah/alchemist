package model.ES.component.ability;

import java.util.ArrayList;
import java.util.List;

import com.simsilica.es.EntityComponent;

import model.tempImport.ColorData;
import util.math.Angle;

public class PlayerControl implements EntityComponent{
	public ColorData color = new ColorData(255, 150, 0, 0);
	public Angle angledelamortquitue = new Angle(3);
	public List<String> listdetest = new ArrayList<String>() {{
		add("bonjour");
		add("aurevoir");
		add("ca va?");
	}};

	public List<Boolean> listdeboule = new ArrayList<Boolean>() {{
		add(true);
		add(false);
		add(true);
	}};

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

	public Angle getAngledelamortquitue() {
		return angledelamortquitue;
	}

	public List<String> getListdetest() {
		return listdetest;
	}

	public List<Boolean> getListdeboule() {
		return listdeboule;
	}
}
