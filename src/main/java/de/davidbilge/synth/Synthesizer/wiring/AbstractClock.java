package de.davidbilge.synth.Synthesizer.wiring;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractClock implements Clock {

	protected Set<Component> components = new HashSet<Component>();
	
	public void register(Component c) {
		components.add(c);
	}

	public void unregister(Component c) {
		components.remove(c);
	}

}
