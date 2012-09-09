package de.davidbilge.synth.Synthesizer;

public final class MathH {
	private MathH() {}
	
	public static float lerp(float from, float to, float at) {
		if (at == 0f) {
			return from;
		}

		return from + ((to - from) * at);
	}
}
