/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package view.jme;

import com.jme3.material.Material;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;

import app.AppFacade;
import model.ES.component.world.TerrainTexturing;
import model.world.terrain.atlas.Atlas;

/**
 * @author Benoît
 */
public class TerrainSplatTexture extends Material {

	private final Atlas atlas;
	private final TerrainTexturing texturing;

	public boolean transp = false;
	
	public TerrainSplatTexture(Atlas atlas, TerrainTexturing texturing) {
		super(AppFacade.getAssetManager(), "matdefs/MyTerrainLighting.j3md");
		this.atlas = atlas;
		this.texturing = texturing;
		updateTextures();
		updateAlpha();
	}
	
	public void updateAlpha(){
		setTexture("AlphaMap", new Texture2D(new Image(Image.Format.RGBA8, atlas.getWidth(), atlas.getHeight(), atlas.getBuffer(0))));
		setTexture("AlphaMap_1", new Texture2D(new Image(Image.Format.RGBA8, atlas.getWidth(), atlas.getHeight(), atlas.getBuffer(1))));
	}
	
	public void updateTextures(){
		int index = 0;
		for (String s : texturing.getDiffuses()) {
			Texture diffuse = AppFacade.getAssetManager().loadTexture(s);
			diffuse.setWrap(Texture.WrapMode.Repeat);
			diffuse.setAnisotropicFilter(8);
			setTexture(index == 0? "DiffuseMap" : "DiffuseMap_" + index, diffuse);

			double scale = texturing.getScales().get(index);
			setFloat("DiffuseMap_" + index + "_scale", (float)scale);

			if(texturing.getNormals().get(index) != null){ 
				Texture normal = AppFacade.getAssetManager().loadTexture(texturing.getNormals().get(index));
				normal.setAnisotropicFilter(8);
				normal.setWrap(Texture.WrapMode.Repeat);
				setTexture(index == 0? "NormalMap" : "NormalMap_" + index, normal);
			}
			index++;
		}
	}
}
