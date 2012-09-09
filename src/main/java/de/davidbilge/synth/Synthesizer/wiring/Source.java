package de.davidbilge.synth.Synthesizer.wiring;

public interface Source<T> extends Component {
	T getOutput();
}
