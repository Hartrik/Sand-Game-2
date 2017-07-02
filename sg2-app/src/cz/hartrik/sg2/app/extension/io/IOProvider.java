
package cz.hartrik.sg2.app.extension.io;

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
    
    public List<FileTypeIOProvider<T>> getProviders();
    
    public Optional<FileTypeIOProvider<T>> getProvider(Path path);
    
    public Optional<FileTypeIOProvider<T>> getProvider(String extension);
    
    public String[] getExtensions();
    
}