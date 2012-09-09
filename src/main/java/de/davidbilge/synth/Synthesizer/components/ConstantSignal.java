package de.davidbilge.synth.Synthesizer.components;

import de.davidbilge.synth.Synthesizer.wiring.Source;

public class ConstantSignal<T> implements Source<T> {

	private T constantValue;
	
	public T getConstantValue() {
		return constantValue;
	}

	public void setConstantValue(T constantValue) {
		this.constantValue = constantValue;
	}

	public ConstantSignal(T constantValue) {
		this.constantValue = constantValue;
	}

	public void tick(long delta) {
		// Noop
	}

	public void shift() {
		// Noop
	}

	public T getOutput() {
		return constantValue;
	}


}
