package de.davidbilge.synth.Synthesizer.components.wavegen;

import de.davidbilge.synth.Synthesizer.Constants;
import de.davidbilge.synth.Synthesizer.wiring.AbstractGenerator;

public abstract class AbstractWaveGenerator extends AbstractGenerator<Float> {
	private float frequency;
	private float intervalLength;
	private float pitch = 1f;
	private float phaseShift = 0f;
	
	private float positionInInterval = 0;
	
	public AbstractWaveGenerator(float frequency, float pitch, float phaseShift) {
		this(frequency, pitch);
		this.setPhaseShift(phaseShift);
	}
	
	public AbstractWaveGenerator(float frequency, float pitch) {
		this(frequency);
		this.pitch = pitch;
	}
	
	public AbstractWaveGenerator(float frequency) {
		setFrequency(frequency);
	}
	
	@Override
	protected final Float calculate(long delta) {
		positionInInterval += ((float)delta / intervalLength) + getPhaseShift();
		positionInInterval %= 1f;
		
		return calculateWaveValue(positionInInterval);
	}
	
	protected abstract Float calculateWaveValue(float positionInInterval);
	
	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		frequency *= pitch;
		
		this.intervalLength = (float)Constants.NANOS_PER_SECOND / frequency;

		this.frequency = frequency;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getPhaseShift() {
		return phaseShift;
	}

	public void setPhaseShift(float phaseShift) {
		this.phaseShift = phaseShift;
	}
}
