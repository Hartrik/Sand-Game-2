package cz.hartrik.sg2.app.service;

import cz.hartrik.sg2.app.Application;
import java.util.function.Consumer;

/**
 * Rozhraní pro službu.
 *
 * @version 2016-07-10
 * @author Patrik Harag
 */
public interface ExecutableService extends Consumer<Application> {

}