package de.davidbilge.synth.Synthesizer.components.mixer;

import java.util.ArrayList;
import java.util.List;

import de.davidbilge.synth.Synthesizer.wiring.AbstractGenerator;
import de.davidbilge.synth.Synthesizer.wiring.Source;

public class SimpleAddingMixer extends AbstractGenerator<Float> {
	List<Source<Float>> inputs = new ArrayList<Source<Float>>();

	public SimpleAddingMixer(Source<Float>... input) {
		for (Source<Float> i : input) {
			this.inputs.add(i);
		}
	}

	public SimpleAddingMixer(Iterable<Source<Float>> inputs) {
		for (Source<Float> i : inputs) {
			this.inputs.add(i);
		}
	}

	@Override
	protected Float calculate(long delta) {
		if (this.inputs.isEmpty()) {
			return 0f;
		}

		float mixed = 0f;

		for (Source<Float> input : this.inputs) {
			Float output = input.getOutput();
			if (output != null) {
				mixed += output;
			}
		}

		return mixed / this.inputs.size();
	}

}
