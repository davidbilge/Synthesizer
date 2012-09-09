package de.davidbilge.synth.Synthesizer.wiring;

public interface Wire<T> extends Source<T> {
	T getOutput();
	
	void plugInto(Source<T> source);
}
