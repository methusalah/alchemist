package model.world;

public abstract class LayerProcessor {
	private final Class<? extends Layer> layerClass;
	public LayerProcessor(Class<? extends Layer> layerClass){
		this.layerClass = layerClass;
	}
	
	public void load(Layer l){
		if(l.getClass() == layerClass)
			onLoad(layerClass.cast(l));
	}
	
	protected <T extends Layer> void onLoad(T l){
				
	}

	public void save(Layer l){
		if(l.getClass() == layerClass)
			onSave(layerClass.cast(l));
	}
	
	protected <T extends Layer> void onSave(T l){
				
	}
	
}
