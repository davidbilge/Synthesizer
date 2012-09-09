package de.davidbilge.synth.Synthesizer.components.wavegen;

public class SineWaveGenerator extends AbstractWaveGenerator {

	public SineWaveGenerator(float frequency) {
		super(frequency);
	}

	@Override
	protected Float calculateWaveValue(float positionInInterval) {
		return (float) Math.sin(positionInInterval * Math.PI * 2);
	}

}
