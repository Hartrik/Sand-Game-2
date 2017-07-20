
package cz.hartrik.sg2.io;

import cz.hartrik.common.io.NioUtil;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 
 * 
 * @version 2015-02-14
 * @author Patrik Harag
 * @param <T> typ ukládaných / načítaných dat
 */
public class BasicIOProvider<T> implements IOProvider<T> {
    
    private final List<FileTypeIOProvider<T>> providers;

    public BasicIOProvider(List<FileTypeIOProvider<T>> providers) {
        this.providers = providers;
    }

    @Override
    public List<FileTypeIOProvider<T>> getProviders() {
        return Collections.unmodifiableList(providers);
    }
    
    @Override
    public Optional<FileTypeIOProvider<T>> getProvider(Path path) {
        String ext = NioUtil.getExtension(path).toLowerCase();
        return (ext.isEmpty()) ? Optional.empty() : getProvider(ext);
    }
    
    @Override
    public Optional<FileTypeIOProvider<T>> getProvider(String ext) {
        return providers.stream()
                .filter(ioProvider -> ioProvider.getExtension().equals(ext))
                .findFirst();
    }
    
    @Override
    public String[] getExtensions() {
        return providers.stream()
                .map(ioProvider -> ioProvider.getExtension())
                .toArray(String[]::new);
    }

}