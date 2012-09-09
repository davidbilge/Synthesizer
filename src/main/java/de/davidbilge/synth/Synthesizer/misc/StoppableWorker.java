package de.davidbilge.synth.Synthesizer.misc;


public abstract class StoppableWorker implements Runnable {
	private boolean stopRequested = false;

	public final void stop() {
		this.stopRequested = true;
	}

	public final void run() {
		while (!stopRequested) {
			work();
		}

		stopRequested = false;
	}

	protected abstract void work();
	
	public final boolean isStopRequested() {
		return stopRequested;
	}

}
