
package cz.hartrik.sg2.app.module.io;

import java.nio.file.Path;

/**
 * Rozhraní pro výběr souboru a výběr místa uložení souboru.
 * 
 * @version 2014-09-21
 * @author Patrik Harag
 * @param <T> typ kontextu (např. hlavní okno pro tvorbu modálních dialogů)
 */
public interface IFileChooser<T> {
    
    public Path openFile(T context);
    
    public Path saveFile(T context);
    
}