
package cz.hartrik.sg2.io;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Poskytuje další služby na konkrétní typy souborů.
 * 
 * @version 2015-02-14
 * @author Patrik Harag
 * @param <T> typ ukládaných dat
 */
public interface IOProvider<T> {
    
    List<FileTypeIOProvider<T>> getProviders();
    
    Optional<FileTypeIOProvider<T>> getProvider(Path path);
    
    Optional<FileTypeIOProvider<T>> getProvider(String extension);
    
    String[] getExtensions();
    
}