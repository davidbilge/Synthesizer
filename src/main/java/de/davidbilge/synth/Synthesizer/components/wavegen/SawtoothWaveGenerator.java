package de.davidbilge.synth.Synthesizer.components.wavegen;

public class SawtoothWaveGenerator extends AbstractWaveGenerator {

	public SawtoothWaveGenerator(float frequency) {
		super(frequency);
	}

	@Override
	protected Float calculateWaveValue(float positionInInterval) {
		return -1f + (2f * positionInInterval);
	}

}
