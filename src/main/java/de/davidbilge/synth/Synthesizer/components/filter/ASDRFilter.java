package de.davidbilge.synth.Synthesizer.components.filter;

import java.util.HashMap;
import java.util.Map;

import de.davidbilge.synth.Synthesizer.Constants;
import de.davidbilge.synth.Synthesizer.MathH;
import de.davidbilge.synth.Synthesizer.wiring.AbstractGenerator;
import de.davidbilge.synth.Synthesizer.wiring.Source;

public class ASDRFilter extends AbstractGenerator<Float> {

	private Source<Float> signalInput;
	private Source<Boolean> activationInput;

	public ASDRFilter(Source<Float> signalInput, Source<Boolean> activationInput) {
		this.signalInput = signalInput;
		this.activationInput = activationInput;
	}

	// TODO: Make phase lengths and phaseFilterValue configurable

	Map<Phase, Long> phaseLengths = new HashMap<ASDRFilter.Phase, Long>() {
		private static final long serialVersionUID = 1L;

		{
			put(Phase.ATTACK, Constants.NANOS_PER_SECOND / 200);
			put(Phase.ATTACKDECAY, Constants.NANOS_PER_SECOND / 300);
			put(Phase.SUSTAIN, Constants.NANOS_PER_SECOND);
			put(Phase.DECAY, Constants.NANOS_PER_SECOND / 10);
			put(Phase.RELEASE, Long.MAX_VALUE);
		}
	};

	Map<Phase, Float> phaseFilterValue = new HashMap<ASDRFilter.Phase, Float>() {
		private static final long serialVersionUID = 1L;

		{
			put(Phase.ATTACK, 1f);
			put(Phase.ATTACKDECAY, 0.4f);
			put(Phase.SUSTAIN, 0.2f);
			put(Phase.DECAY, 0f);
			put(Phase.RELEASE, 0f);
		}
	};

	private boolean lastKnownActivationValue = false;
	private long timeInPhase = 0;
	Phase phase = Phase.RELEASE;

	private float phaseStartFilterValue = 0f;
	float currentFilterValue = 0f;

	@Override
	protected Float calculate(long delta) {
		if (signalInput == null || signalInput.getOutput() == null) {
			return null;
		}

		Boolean activation = activationInput.getOutput();
		Phase newPhase = newPhase(activation);
		if (newPhase != phase) {
			timeInPhase = 0;
			phaseStartFilterValue = currentFilterValue;
		} else {
			timeInPhase += delta;
		}

		phase = newPhase;

		Long currentPhaseLength = phaseLengths.get(phase);
		float percentageInPhase = Math.min(currentPhaseLength == 0 ? 0f
				: (float) timeInPhase / (float) currentPhaseLength, 1f);

		currentFilterValue = MathH.lerp(phaseStartFilterValue,
				phaseFilterValue.get(phase), percentageInPhase);

		lastKnownActivationValue = activation;

		return currentFilterValue * signalInput.getOutput();
	}

	private Phase newPhase(boolean activation) {
		if (activation && !lastKnownActivationValue) {
			// KeyDown

			return Phase.ATTACK;
		} else if (!activation && lastKnownActivationValue) {
			// KeyUp

			return Phase.DECAY;
		} else {
			// Hold
			return getPhaseFrom(phase, timeInPhase);
		}
	}

	Phase getPhaseFrom(Phase current, long timeInCurrentPhase) {
		int i = 0;

		while (Phase.values()[i] != current) {
			++i;
		}

		if (i == Phase.values().length) {
			return current;
		}

		long acc = 0;
		int j = i + 1;
		for (; j < Phase.values().length; ++j) {
			acc += phaseLengths.get(Phase.values()[j - 1]);
			if (acc >= timeInCurrentPhase) {
				break;
			}
		}

		return Phase.values()[j - 1];
	}

	static enum Phase {
		ATTACK, ATTACKDECAY, SUSTAIN, DECAY, RELEASE;
	}

}
