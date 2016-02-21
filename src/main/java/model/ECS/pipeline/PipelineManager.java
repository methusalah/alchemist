package model.ECS.pipeline;

import java.util.HashMap;
import java.util.Map;

import com.jme3.app.state.AppStateManager;

import model.tempImport.RendererPlatform;

public class PipelineManager {
	private final Map<PipelineRun, Thread> editionPipelines = new HashMap<>();
	private final Map<PipelineRun, Thread> pipelines = new HashMap<>();
	
	
	public Pipeline createPipeline(String name, boolean inRendererThread, boolean inEditionMode){
		Pipeline res = new Pipeline(name, inRendererThread);
		PipelineRun pr;
		if(inRendererThread) {
			pr = new PipelineRun(res, RendererPlatform.getStateManager(), null);
		} else {
			AppStateManager stateManager = new AppStateManager(null);
			pr = new PipelineRun(res, stateManager, () -> {
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
			
		pipelines.put(pr, null);
		if(inEditionMode)
			editionPipelines.put(pr, null);
		return res;
	}

	public void runEditionPiplines(){
		run(editionPipelines);
	}
	
	public void runPipelines(){
		run(pipelines);
	}
	
	private void run(Map<PipelineRun, Thread> pipelineSet){
		for(PipelineRun pr : pipelineSet.keySet()){
			for(Processor p : pr.getPipeline().getProcessors().values())
				pr.getStateManager().attach(p);
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
	
	private void stop(Map<PipelineRun, Thread> pipelineSet){
		for(PipelineRun pr : pipelineSet.keySet()){
			for(Processor p : pr.getPipeline().getProcessors().values())
				pr.getStateManager().detach(p);
			if(pr.getRunnable() != null){
				pipelineSet.get(pr).interrupt();
			}
		}
	}

}
