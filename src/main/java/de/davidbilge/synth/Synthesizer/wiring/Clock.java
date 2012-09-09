package de.davidbilge.synth.Synthesizer.wiring;

public interface Clock {
	void register(Component c);
	
	void unregister(Component c);
	
	void start();
	
	void stop();
}
