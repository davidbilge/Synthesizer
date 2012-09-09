package de.davidbilge.synth.Synthesizer.components.filter;

import junit.framework.Assert;

import org.junit.Test;

import de.davidbilge.synth.Synthesizer.Constants;
import de.davidbilge.synth.Synthesizer.components.ConstantSignal;
import de.davidbilge.synth.Synthesizer.components.filter.ASDRFilter;
import de.davidbilge.synth.Synthesizer.components.filter.ASDRFilter.Phase;

public class ASDRFilterTest {
	
	@Test
	public void testGetPhaseFrom() {
		ASDRFilter f = new ASDRFilter(new ConstantSignal<Float>(1f), new ConstantSignal<Boolean>(true));
		
		Assert.assertEquals(Phase.ATTACK, f.getPhaseFrom(ASDRFilter.Phase.ATTACK, Constants.NANOS_PER_SECOND / 200));
		Assert.assertEquals(Phase.ATTACKDECAY, f.getPhaseFrom(ASDRFilter.Phase.ATTACK, Constants.NANOS_PER_SECOND / 20 + 1));
		Assert.assertEquals(Phase.SUSTAIN, f.getPhaseFrom(ASDRFilter.Phase.ATTACK, Constants.NANOS_PER_SECOND));
		Assert.assertEquals(Phase.RELEASE, f.getPhaseFrom(ASDRFilter.Phase.ATTACK, Constants.NANOS_PER_SECOND * 2));

		Assert.assertEquals(Phase.RELEASE, f.getPhaseFrom(ASDRFilter.Phase.RELEASE, Constants.NANOS_PER_SECOND * 2));
	}
	
	@Test
	public void testCalculate() {
		ConstantSignal<Boolean> activation = new ConstantSignal<Boolean>(true);
		
		ASDRFilter f = new ASDRFilter(new ConstantSignal<Float>(1f), activation);
		long delta = Constants.NANOS_PER_SECOND / 100;
		for (long i=0; i < Constants.NANOS_PER_SECOND * 2; i += delta) {
			Float v = f.calculate(delta);
			System.out.println(i + ": " + v + " (" + f.phase + ")");
		}
		
	}
}
