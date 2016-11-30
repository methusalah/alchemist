package com.brainless.alchemist.model.ECS.pipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brainless.alchemist.model.EditorPlatform;
import com.brainless.alchemist.model.state.DataState;
import com.brainless.alchemist.model.tempImport.RendererPlatform;
import com.jme3.app.state.AppStateManager;

/**
 * The pipeline manager creates, holds and manage life cycle of the application piplines.
 * 
 * @author Benoît
 *
 */
public class PipelineManager {
	private final Map<Pipeline, Thread> editionPipelines = new HashMap<>();
	private final Map<Pipeline, Thread> pipelines = new HashMap<>();

	/**
	 * Create a new pipeline preconfigured accordingly to the given parameters 
	 * @param name the pipline name for user convenience
	 * @param inRendererThread true if the pipeline should be executed by the renderer thread.
	 * If false, the pipeline will have it's own thread, at 50 tick per seconds maximum
	 * @param inEditionMode true if the pipeline should be executed in edition mode.
	 * If false, the pipeline will be executed in playtime and runtime
	 * @return the created pipeline.
	 */
	public Pipeline createPipeline(String name, boolean inRendererThread, boolean inEditionMode){
		final Pipeline res;
		if(inRendererThread) {
			res = new Pipeline(name, RendererPlatform.getStateManager());
		} else {
			AppStateManager stateManager = new AppStateManager(null);
			res = new Pipeline(name, stateManager);
			res.addProcessor(new DataState(EditorPlatform.getEntityData()));
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

	/**
	 * Run only pipelines that are assigned to the edition mode
	 */
	public void runEditionPiplines(){
		run(editionPipelines);
	}
	
	/**
	 * Run all pipelines
	 */
	public void runPipelines(){
		run(pipelines);
	}
	
	private void run(Map<Pipeline, Thread> pipelineSet){
		for(Pipeline pr : pipelineSet.keySet()){
			for(Processor p : pr.getProcessors()){
				RendererPlatform.enqueue(() -> pr.getStateManager().attach(p));
			}
			if(pr.getRunnable() != null){
				Thread thread = new Thread(pr.getRunnable());
				thread.start();
				pipelineSet.put(pr, thread);
			}
		}
	}
	
	/**
	 * Stops only pipelines that are assigned to the edition mode
	 */
	public void stopEditionPiplines(){
		stop(editionPipelines);
	}
	
	/**
	 * Stops all pipelines
	 */
	public void stopPiplines(){
		stop(pipelines);
	}
	
	private void stop(Map<Pipeline, Thread> pipelineSet){
		for(Pipeline pr : pipelineSet.keySet()){
			for(Processor p : pr.getProcessors())
				RendererPlatform.enqueue(() -> pr.getStateManager().detach(p));
			if(pr.getRunnable() != null){
				pipelineSet.get(pr).interrupt();
			}
		}
	}
	
	/**
	 * Returns a list of the pipeline that are running on their own thread
	 * @return
	 */
	public List<Pipeline> getIndependantPipelines(){
		List<Pipeline> res = new ArrayList<>();
		for(Pipeline pr : pipelines.keySet()){
			if(pr.getRunnable() != null)
				res.add(pr);
		}
		return res;
	}

}
