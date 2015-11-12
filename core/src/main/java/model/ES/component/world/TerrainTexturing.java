package model.ES.component.world;

import java.util.ArrayList;
import java.util.List;

public class TerrainTexturing {
    private final List<String> diffuses;
    private final List<String> normals;
    private final List<Double> scales;

    private final List<String> coverDiffuses;
    private final List<String> coverNormals;
    private final List<Double> coverScales;

    public TerrainTexturing(){
    	diffuses = new ArrayList<String>();
    	normals = new ArrayList<String>();
    	scales = new ArrayList<Double>();

    	coverDiffuses = new ArrayList<String>();
    	coverNormals = new ArrayList<String>();
    	coverScales = new ArrayList<Double>();
    }
    
    public TerrainTexturing(List<String> diffuses,
    		List<String> normals,
    		List<Double> scales,
    		List<String> coverDiffuses,
    		List<String> coverNormals,
    		List<Double> coverScales){
    	this.diffuses = diffuses;
    	this.normals = normals;
    	this.scales = scales;
    	this.coverDiffuses = coverDiffuses;
    	this.coverNormals = coverNormals;
    	this.coverScales = coverScales;
    }

	public List<String> getDiffuses() {
		return diffuses;
	}

	public List<String> getNormals() {
		return normals;
	}

	public List<Double> getScales() {
		return scales;
	}

	public List<String> getCoverDiffuses() {
		return coverDiffuses;
	}

	public List<String> getCoverNormals() {
		return coverNormals;
	}

	public List<Double> getCoverScales() {
		return coverScales;
	}

    

}
