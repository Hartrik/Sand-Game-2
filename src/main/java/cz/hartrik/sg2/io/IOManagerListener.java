
package cz.hartrik.sg2.io;

import java.nio.file.Path;

/**
 * Rozhraní pro zachycení změn v {@link IOManager}.
 * 
 * @version 2014-04-16
 * @author Patrik Harag
 */
@FunctionalInterface
public interface IOManagerListener {
    
    void pathChanged(Path path);
    
}