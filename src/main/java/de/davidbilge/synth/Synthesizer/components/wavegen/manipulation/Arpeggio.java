package de.davidbilge.synth.Synthesizer.components.wavegen.manipulation;

import java.util.ArrayList;
import java.util.List;

import de.davidbilge.synth.Synthesizer.components.wavegen.AbstractWaveGenerator;

public class Arpeggio extends WaveformManipulator {

	List<AbstractWaveGenerator> wavegens = new ArrayList<AbstractWaveGenerator>();

	List<Float> frequencies = new ArrayList<Float>();
	int currentFrequency = 0;

	long duration;

	long position = 0L;

	public Arpeggio(List<AbstractWaveGenerator> wavegens,
			List<Float> frequencies, long duration) {
		super();
		this.wavegens = wavegens;
		this.frequencies = frequencies;
		this.duration = duration;
	}

	@Override
	public void tick(long delta) {
		position += delta;
		position %= duration;

		int newFrequency = (int) Math
				.floor(((float) position / (float) duration)
						* (float) frequencies.size());

		if (newFrequency != currentFrequency) {
			currentFrequency = newFrequency;
			for (AbstractWaveGenerator wg : this.wavegens) {
				wg.setFrequency(frequencies.get(currentFrequency));
			}
		}

	}

}
