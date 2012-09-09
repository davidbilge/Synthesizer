package de.davidbilge.synth.Synthesizer.components;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import de.davidbilge.synth.Synthesizer.misc.StoppableWorker;
import de.davidbilge.synth.Synthesizer.wiring.Component;
import de.davidbilge.synth.Synthesizer.wiring.Source;

public class Speaker implements Component {
	private static final int BUFFER_SIZE = 4 * 10000;

	private Source<Float> input;
	private SourceDataLine line;

	private byte[] buffer = new byte[BUFFER_SIZE];
	private int readPointer = 0;
	private int writeAhead = 0;

	float fSampleRate = 22050.0F;
	AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
			fSampleRate, 16, 2, 4, fSampleRate, false);
	private float fAmplitude = 0.7F;
	private float internalAmplitude = (float) (fAmplitude * Math.pow(2,
			audioFormat.getSampleSizeInBits() - 1));

	private StoppableWorker readerWorker = new ReaderWorker();
	private Thread readerThread = new Thread(readerWorker);
	
	boolean initializing = true;
	
	public Speaker(Source<Float> input) {
		this.input = input;

		line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		line.start();

		// TODO: Register shutdown hook to stop line (need to stop weird audio artifacts on shutdown)
	}

	public void tick(long delta) {
		Float value = input.getOutput();
		if (value != null) {
			writeToBuffer(value);
		}
		
		if (initializing && writeAhead >= 0.9 * BUFFER_SIZE) {
			readerThread.start();
			initializing = false;
		}
		
		//readFromBuffer();
	}

	private void writeToBuffer(float value) {
		writeToBuffer(value, buffer, (readPointer + writeAhead) % BUFFER_SIZE);
		writeAhead += 4;
	}

	private void readFromBuffer() {
		if (writeAhead > 0) {
			int bytesWritten = line.write(buffer, readPointer, Math.min(writeAhead, BUFFER_SIZE - readPointer));
			readPointer += bytesWritten;
			readPointer %= BUFFER_SIZE;
			writeAhead -= bytesWritten;
		} else {
			//System.out.println("Buffer underrun " + System.currentTimeMillis() );
		}
	}

	public void shift() {
		// Do nothing
	}

	private void writeToBuffer(float value, byte[] data, int offset) {
		int nValue = Math.round(value * internalAmplitude);
		// int nBaseAddr = (nFrame) * getFormat().getFrameSize();
		// this is for 16 bit stereo, little endian
		data[(offset + 0) % BUFFER_SIZE] = (byte) (nValue & 0xFF);
		data[(offset + 1) % BUFFER_SIZE] = (byte) ((nValue >>> 8) & 0xFF);
		data[(offset + 2) % BUFFER_SIZE] = (byte) (nValue & 0xFF);
		data[(offset + 3) % BUFFER_SIZE] = (byte) ((nValue >>> 8) & 0xFF);
	}

	private class ReaderWorker extends StoppableWorker {

		@Override
		protected void work() {
			readFromBuffer();
		}
		
	}

}
