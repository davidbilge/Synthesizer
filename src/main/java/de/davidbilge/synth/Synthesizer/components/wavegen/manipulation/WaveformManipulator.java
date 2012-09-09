package de.davidbilge.synth.Synthesizer.components.wavegen.manipulation;

import de.davidbilge.synth.Synthesizer.wiring.Component;

public abstract class WaveformManipulator implements Component {

	public abstract void tick(long delta);

	public final void shift() {
		// Nothing
	}

}
