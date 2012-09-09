package de.davidbilge.synth.Synthesizer.components.wavegen;

public class SquareWaveGenerator extends AbstractWaveGenerator {
	private float width;

	public SquareWaveGenerator(float frequency, float width) {
		super(frequency);
		this.width = width;
	}

	@Override
	protected Float calculateWaveValue(float positionInInterval) {
		if (positionInInterval > width) {
			return 1.0f;
		} else {
			return -1.0f;
		}
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}


}
