
package cz.hartrik.sg2.app.extension.io;

import java.nio.file.Path;

/**
 * Rozhraní pro zachycení změn v {@link IIOManager}.
 * 
 * @version 2014-04-16
 * @author Patrik Harag
 */
@FunctionalInterface
public interface IOManagerListener {
    
    public void pathChanged(Path path);
    
}