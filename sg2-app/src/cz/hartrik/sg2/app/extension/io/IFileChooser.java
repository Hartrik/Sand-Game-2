
package cz.hartrik.sg2.app.extension.io;

import java.nio.file.Path;

/**
 * Rozhraní pro výběr souboru a výběr místa uložení souboru.
 * 
 * @version 2014-09-21
 * @author Patrik Harag
 * @param <T> typ kontextu (např. hlavní okno pro tvorbu modálních dialogů)
 */
public interface IFileChooser<T> {
    
    Path openFile(T context);
    
    Path saveFile(T context);
    
}