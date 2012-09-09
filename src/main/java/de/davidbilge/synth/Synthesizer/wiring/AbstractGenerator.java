package de.davidbilge.synth.Synthesizer.wiring;

public abstract class AbstractGenerator<T> implements Source<T> {

	private T currentValue = null;
	private T nextValue = null;
	
	public final void tick(long delta) {
		nextValue = calculate(delta);
	}

	public final void shift() {
		currentValue = nextValue;
		nextValue = null;
	}
	
	public final T getOutput() {
		return currentValue;
	}

	protected abstract T calculate(long delta);

}
