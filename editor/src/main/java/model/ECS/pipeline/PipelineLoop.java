package main.java.model.ECS.pipeline;

import com.jme3.app.state.AppStateManager;

public class PipelineLoop implements Runnable {
    private static int millisPerTick = 20;
    private static double secondPerTick = (double)millisPerTick/1000;

    private final AppStateManager stateManager;
    private int tickCount = 0;
    private int waitTime = 0;

	public PipelineLoop(AppStateManager stateManager) {
    	this.stateManager = stateManager;
	}
	
    public int getTickCount() {
		return tickCount;
	}

	public int getWaitTime() {
		return waitTime;
	}
	
	public void resetIdleStats(){
		tickCount = 0;
		waitTime = 0;
	}
	
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			long time = System.currentTimeMillis();
			stateManager.update((float)0.02);
			long nextTick = (long) (time+20);
			long towait = nextTick - System.currentTimeMillis();

			tickCount++;
			waitTime += towait;
			
			if(towait > 0)
				try {
					Thread.sleep(towait);
				} catch (InterruptedException e) {
					break;
				}
		}
	}

	public static int getMillisPerTick() {
		return millisPerTick;
	}

	public static void setMillisPerTick(int millisPerTick) {
		LogicLoop.millisPerTick = millisPerTick;
		secondPerTick = (double)millisPerTick/1000;
	}

	public static double getSecondPerTick() {
		return secondPerTick;
	}

}
