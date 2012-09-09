package de.davidbilge.synth.Synthesizer.wiring;

import de.davidbilge.synth.Synthesizer.Constants;
import de.davidbilge.synth.Synthesizer.misc.StoppableWorker;

public final class SimpleClock extends AbstractClock {

	private final long sampleLengthInNanos;
	// private final long frequency;

	private Worker w = new Worker();
	private boolean isWorking = false;

	private long skippedSamples = 0;

	public SimpleClock(long frequency) {
		// this.frequency = frequency;
		this.sampleLengthInNanos = Constants.NANOS_PER_SECOND / frequency;
	}

	public void start() {
		if (isWorking && !w.isStopRequested()) {
			return;
		}

		w = new Worker();
		Thread workerThread = new Thread(w);
		workerThread.start();
		isWorking = true;
	}

	public void stop() {
		if (!isWorking) {
			return;
		}

		w.stop();

		System.out.println("Skipped " + skippedSamples + " Samples.");
	}

	private final class Worker extends StoppableWorker {
		public void work() {
			long before = System.nanoTime();

			for (Component c : components) {
				c.tick(sampleLengthInNanos);
			}

			for (Component c : components) {
				c.shift();
			}

			long elapsed = System.nanoTime() - before;

			if (elapsed > sampleLengthInNanos) {
				skippedSamples += Math.floor((double) elapsed
						/ (double) sampleLengthInNanos);
				System.out.println("Skipped" + elapsed);
			}

			long remainingWaitTime = sampleLengthInNanos
					- (elapsed % sampleLengthInNanos);
			waitNanos(remainingWaitTime);

		}

		private void waitNanos(long nanos) {
			long lastTimestamp = System.nanoTime();
			while (nanos > 0) {
				long nowTimestamp = System.nanoTime();
				nanos -= (nowTimestamp - lastTimestamp);
				lastTimestamp = nowTimestamp;
			}
		}

	}

}
