package de.davidbilge.synth.Synthesizer.wiring;

public interface Component {
	void tick(long delta);
	void shift();
}
