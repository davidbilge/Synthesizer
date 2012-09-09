package de.davidbilge.synth.Synthesizer;

import de.davidbilge.synth.Synthesizer.wiring.Component;

public class CountingComponent implements Component {

	private long ticks = 0;
	private long shifts = 0;

	public void tick(long frequency) {
		ticks++;
	}

	public void shift() {
		shifts++;
	}

	public long getTicks() {
		return ticks;
	}

	public long getShifts() {
		return shifts;
	}

}
