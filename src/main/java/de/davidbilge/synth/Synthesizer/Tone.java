package de.davidbilge.synth.Synthesizer;

public enum Tone {
	 C(261.63f),
	 DES(277.18f),
	 D(293.66f),
	 ES(311.13f),
	 E(329.63f),
	 F(349.23f),
	 GES(369.99f),
	 G(392.00f),
	 AS(415.30f),
	 A(440f),
	 B(466.16f),
	 H(493.88f);
	 
	 public final float frequency;
	 
	 private Tone(float frequency) {
		 this.frequency = frequency;
	 }
}
