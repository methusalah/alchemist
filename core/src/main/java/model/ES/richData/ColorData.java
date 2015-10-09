package model.ES.richData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorData {
	public final int a, r, g, b;
	
	public ColorData(@JsonProperty("a")int a,
			@JsonProperty("r")int r,
			@JsonProperty("g")int g,
			@JsonProperty("b")int b) {
		assert a >= 0 && a < 256 &&
				r >= 0 && g < 256 &&
				g >= 0 && g < 256 &&
				b >= 0 && b < 256 : "Invalid color (a="+a+";r="+r+";g="+g+";b="+b+")";
		this.a = a;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public ColorData(float a, float r, float g, float b) {
		assert a >= 0 && a <= 1 &&
				r >= 0 && g <= 1 &&
				g >= 0 && g <= 1 &&
				b >= 0 && b <= 1 : "Invalid color (a="+a+";r="+r+";g="+g+";b="+b+")";
		this.a = Math.round(a*255);
		this.r = Math.round(r*255);
		this.g = Math.round(g*255);
		this.b = Math.round(b*255);
	}

	public ColorData(double a, double r, double g, double b) {
		assert a >= 0 && a <= 1 &&
				r >= 0 && g <= 1 &&
				g >= 0 && g <= 1 &&
				b >= 0 && b <= 1 : "Invalid color (a="+a+";r="+r+";g="+g+";b="+b+")";
		this.a = (int)Math.round(a*255);
		this.r = (int)Math.round(r*255);
		this.g = (int)Math.round(g*255);
		this.b = (int)Math.round(b*255);
	}
	
	@Override
	public String toString() {
		return "(a="+a+";r="+r+";g="+g+";b="+b+")";
	}
}
