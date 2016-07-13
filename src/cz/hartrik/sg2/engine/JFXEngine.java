
package cz.hartrik.sg2.engine;

import cz.hartrik.sg2.engine.render.JFXRenderer;

/**
 * Engine vykreslující do plátna frameworku JavaFX.
 *
 * @version 2016-06-24
 * @author Patrik Harag
 * @param <P>
 */
public interface JFXEngine<P extends Processor> extends Engine<P, JFXRenderer> {

}