package model.ECS.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jme3.app.state.AppStateManager;

import model.tempImport.RendererPlatform;
import util.LogUtil;

public class PipelineManager {
	private final Map<Pipeline, Thread> editionPipelines = new HashMap<>();
	private final Map<Pipeline, Thread> pipelines = new HashMap<>();
	
	public Pipeline createPipeline(String name, boolean inRendererThread, boolean inEditionMode){
		final Pipeline res;
		if(inRendererThread) {
			res = new Pipeline(name, RendererPlatform.getStateManager());
		} else {
			AppStateManager stateManager = new AppStateManager(null);
			res = new Pipeline(name, stateManager);
			res.setRunnable(() -> {
				while (!Thread.currentThread().isInterrupted()) {
					long time = System.currentTimeMillis();
					stateManager.update((float)0.02);
					long nextTick = time+20;
					long towait = nextTick - System.currentTimeMillis();
	
					res.tickCount++;
					res.waitTime += towait;
					
					if(towait > 0)
						try {
							Thread.sleep(towait);
						} catch (InterruptedException e) {
							break;
						}
				}
			});
		}
			
		pipelines.put(res, null);
		if(inEditionMode){
			editionPipelines.put(res, null);
		}
		return res;
	}

	public void runEditionPiplines(){
		run(editionPipelines);
	}
	
	public void runPipelines(){
		run(pipelines);
	}
	
	private void run(Map<Pipeline, Thread> pipelineSet){
		for(Pipeline pr : pipelineSet.keySet()){
			LogUtil.info("order : " + pr.getName());
			for(Processor p : pr.getProcessors().values()){
				RendererPlatform.enqueue(() -> pr.getStateManager().attach(p));
			}
			if(pr.getRunnable() != null){
				Thread thread = new Thread(pr.getRunnable());
				pipelineSet.put(pr, thread);
			}
		}
	}
	
	public void stopEditionPiplines(){
		stop(editionPipelines);
	}
	
	public void stopPiplines(){
		stop(pipelines);
	}
	
	private void stop(Map<Pipeline, Thread> pipelineSet){
		for(Pipeline pr : pipelineSet.keySet()){
			for(Processor p : pr.getProcessors().values())
				RendererPlatform.enqueue(() -> pr.getStateManager().detach(p));
			if(pr.getRunnable() != null){
				pipelineSet.get(pr).interrupt();
			}
		}
	}
	
	public List<Pipeline> getIndependantPipelines(){
		List<Pipeline> res = new ArrayList<>();
		for(Pipeline pr : pipelines.keySet()){
			if(pr.getRunnable() != null)
				res.add(pr);
		}
		return res;
	}

}
