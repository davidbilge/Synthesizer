package de.davidbilge.synth.Synthesizer.components.wavegen;

public class TriangleWaveGenerator extends AbstractWaveGenerator {

	public TriangleWaveGenerator(float frequency) {
		super(frequency);
	}

	@Override
	protected Float calculateWaveValue(float positionInInterval) {
		if (positionInInterval < 0.5) {
			return 1f - (4f * positionInInterval);
		} else {
			return -1f + (4f * (positionInInterval - 0.5f));
		}
	}

}
