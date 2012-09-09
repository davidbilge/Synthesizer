package de.davidbilge.synth.Synthesizer.wiring;

public class SimpleWire<T> implements Wire<T> {

	private T value;
	private Source<T> source;
	
	public void tick(long frequency) {
		this.value = source.getOutput();
	}

	public void shift() {
		// Do nothing during the shift phase
	}

	public T getOutput() {
		return value;
	}

	public void plugInto(Source<T> source) {
		this.source = source;
	}

}
