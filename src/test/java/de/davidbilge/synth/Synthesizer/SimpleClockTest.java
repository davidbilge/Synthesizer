package de.davidbilge.synth.Synthesizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.davidbilge.synth.Synthesizer.components.ConstantSignal;
import de.davidbilge.synth.Synthesizer.components.Speaker;
import de.davidbilge.synth.Synthesizer.components.filter.ASDRFilter;
import de.davidbilge.synth.Synthesizer.components.mixer.SimpleAddingMixer;
import de.davidbilge.synth.Synthesizer.components.wavegen.AbstractWaveGenerator;
import de.davidbilge.synth.Synthesizer.components.wavegen.SawtoothWaveGenerator;
import de.davidbilge.synth.Synthesizer.components.wavegen.SineWaveGenerator;
import de.davidbilge.synth.Synthesizer.components.wavegen.SquareWaveGenerator;
import de.davidbilge.synth.Synthesizer.components.wavegen.manipulation.Arpeggio;
import de.davidbilge.synth.Synthesizer.wiring.Clock;
import de.davidbilge.synth.Synthesizer.wiring.SimpleClock;
import de.davidbilge.synth.Synthesizer.wiring.Source;

public class SimpleClockTest {

	private static final long FREQUENCY = 22050;
	private static final long DURATION = 10;

	@Test
	public void testASDR() throws InterruptedException {
		Clock c = new SimpleClock(FREQUENCY);

		CountingComponent counter = new CountingComponent();
		c.register(counter);

		// SquareWaveGenerator swg = new SquareWaveGenerator(220L, 0.5f);
		SquareWaveGenerator h_waveGen = new SquareWaveGenerator(
				Tone.H.frequency, 0.5f);
		SawtoothWaveGenerator d_waveGen = new SawtoothWaveGenerator(
				Tone.D.frequency / 2);
		SawtoothWaveGenerator g_waveGen = new SawtoothWaveGenerator(
				Tone.G.frequency / 2);
		SawtoothWaveGenerator f_waveGen = new SawtoothWaveGenerator(
				Tone.F.frequency / 2);
		// AbstractWaveGenerator swg = new SawtoothWaveGenerator(110);

		c.register(h_waveGen);
		c.register(d_waveGen);
		c.register(f_waveGen);
		c.register(g_waveGen);

		List<Source<Float>> inputList = new ArrayList<Source<Float>>();
		inputList.add(g_waveGen);
		inputList.add(f_waveGen);
		inputList.add(d_waveGen);
		inputList.add(h_waveGen);

		SimpleAddingMixer mixer = new SimpleAddingMixer(inputList);
		c.register(mixer);

		ConstantSignal<Boolean> activationInput = new ConstantSignal<Boolean>(
				true);
		c.register(activationInput);
		ASDRFilter asdrf = new ASDRFilter(mixer, activationInput);
		c.register(asdrf);

		Speaker s = new Speaker(asdrf);
		c.register(s);

		c.start();

		// Thread.sleep(DURATION * 1000);

		for (int i = 0; i < 4; ++i) {
			activationInput.setConstantValue(true);
			Thread.sleep(1000);
			activationInput.setConstantValue(false);
			Thread.sleep(500);
		}

		// float pitch = 0f;
		// while (pitch < 1f) {
		// pitch += 0.001f;
		// h_waveGen.setWidth(pitch);
		// Thread.sleep((DURATION*1000)/1000);
		// }

		c.stop();
	}
	
	@Test
	public void testArpeggio() throws InterruptedException {
		Clock c = new SimpleClock(FREQUENCY);
		
		CountingComponent counter = new CountingComponent();
		c.register(counter);
		
		SawtoothWaveGenerator swg = new SawtoothWaveGenerator(Tone.A.frequency / 2);
		SawtoothWaveGenerator swgPhaseShift = new SawtoothWaveGenerator(Tone.A.frequency / 4);
		
		c.register(swg);
		c.register(swgPhaseShift);
		
		List<AbstractWaveGenerator> wavegens = new ArrayList<AbstractWaveGenerator>();
		wavegens.add(swg);
		wavegens.add(swgPhaseShift);
		
		List<Source<Float>> inputList = new ArrayList<Source<Float>>();
		inputList.add(swg);
		inputList.add(swgPhaseShift);
		
		List<Float> frequencies = new ArrayList<Float>() {
			private static final long serialVersionUID = 1L;

		{
			add(Tone.C.frequency);
			add(Tone.E.frequency);
			add(Tone.G.frequency);
		}};
		
		Arpeggio arp = new Arpeggio(wavegens, frequencies, Constants.NANOS_PER_SECOND / 16);
		c.register(arp);
		
		ConstantSignal<Boolean> activationInput = new ConstantSignal<Boolean>(
				true);
		c.register(activationInput);
		
		SimpleAddingMixer mixer = new SimpleAddingMixer(inputList);
		c.register(mixer);
		
		ASDRFilter asdrf = new ASDRFilter(mixer, activationInput);
		c.register(asdrf);
		
		Speaker s = new Speaker(asdrf);
		c.register(s);
		
		c.start();
		
		// Thread.sleep(DURATION * 1000);
		
		for (int i = 0; i < 4; ++i) {
			activationInput.setConstantValue(true);
			Thread.sleep(1000);
			activationInput.setConstantValue(false);
			Thread.sleep(500);
		}
		
		// float pitch = 0f;
		// while (pitch < 1f) {
		// pitch += 0.001f;
		// h_waveGen.setWidth(pitch);
		// Thread.sleep((DURATION*1000)/1000);
		// }
		
		c.stop();
	}
	@Test
	public void testPhaseShift() throws InterruptedException {
		Clock c = new SimpleClock(FREQUENCY);
		
		CountingComponent counter = new CountingComponent();
		c.register(counter);
		
		SawtoothWaveGenerator swg = new SawtoothWaveGenerator(Tone.A.frequency / 2);
		SawtoothWaveGenerator swgPhaseShift = new SawtoothWaveGenerator(Tone.A.frequency / 4);
		swgPhaseShift.setPhaseShift(0f);
		swgPhaseShift.setPitch(1.1f);
		
		c.register(swg);
		c.register(swgPhaseShift);
		
		List<Source<Float>> inputList = new ArrayList<Source<Float>>();
		inputList.add(swg);
		inputList.add(swgPhaseShift);
		
		SimpleAddingMixer mixer = new SimpleAddingMixer(inputList);
		c.register(mixer);
		
		ConstantSignal<Boolean> activationInput = new ConstantSignal<Boolean>(
				true);
		c.register(activationInput);
		ASDRFilter asdrf = new ASDRFilter(mixer, activationInput);
		c.register(asdrf);
		
		Speaker s = new Speaker(asdrf);
		c.register(s);
		
		c.start();
		
		// Thread.sleep(DURATION * 1000);
		
		for (int i = 0; i < 4; ++i) {
			activationInput.setConstantValue(true);
			Thread.sleep(1000);
			activationInput.setConstantValue(false);
			Thread.sleep(500);
		}
		
		// float pitch = 0f;
		// while (pitch < 1f) {
		// pitch += 0.001f;
		// h_waveGen.setWidth(pitch);
		// Thread.sleep((DURATION*1000)/1000);
		// }
		
		c.stop();
	}
}
