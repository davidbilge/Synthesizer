package de.davidbilge.synth.Synthesizer;

import de.davidbilge.synth.Synthesizer.wiring.Clock;
import de.davidbilge.synth.Synthesizer.wiring.SimpleClock;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        System.out.println( "Hello World!" );
        
        Clock c = new SimpleClock(44100);
        c.start();
        
        Thread.sleep(3000);
        
        c.stop();
    }
}
