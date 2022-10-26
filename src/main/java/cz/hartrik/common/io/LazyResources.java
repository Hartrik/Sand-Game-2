package cz.hartrik.common.io;

import cz.hartrik.common.reflect.LibraryClass;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.function.Supplier;
import javafx.scene.image.Image;

/**
 * Stejně jako třída {@link Resources}, poskytuje statické metody pro
 * jednoduché načítání dat z obsahu jar. Metody v této třídě ovšem předávají
 * {@link Supplier}, který data načte až ve chvíli, kdy je to potřeba.
 * Data jsou načtena pouze jednou, {@link Supplier} si po prvním načtení
 * ukládá referenci.
 * 
 * @version 2015-04-04
 * @author Patrik Harag
 */
@LibraryClass
public final class LazyResources {

    protected LazyResources() { }
    
    // --- text
    
    // absolutní cesta
    
    public static Supplier<String> text(String absolutePath) {
        return new ResourceHolder<>(()
                -> Resources.text(absolutePath));
    }
    
    public static Supplier<String> text(String absolutePath, Charset charset) {
        return new ResourceHolder<>(()
                -> Resources.text(absolutePath, charset));
    }
    
    // relativní cesta
    
    public static Supplier<String> text(String fileName, Class<?> context) {
        return text(Resources.absolutePath(fileName, context));
    }
    
    public static Supplier<String> text(String fileName, Class<?> context,
            Charset charset) {
        
        return text(Resources.absolutePath(fileName, context), charset);
    }
    
    // proud
    
    public static Supplier<String> text(InputStream inputStream) {
        return new ResourceHolder<>(()
                -> Resources.text(inputStream));
    }
    
    public static Supplier<String> text(InputStream is, Charset charset) {
        return new ResourceHolder<>(()
                -> Resources.text(is, charset));
    }
    
    // --- image
    
    // absolutní cesta
    
    public static Supplier<Image> image(String absolutePath) {
        return new ResourceHolder<>(()
                -> Resources.image(absolutePath));
    }
    
    // relativní cesta
    
    public static Supplier<Image> image(String fileName, Class<?> context) {
        return image(Resources.absolutePath(fileName, context));
    }
    
    // proud
    
    public static Supplier<Image> image(InputStream inputStream) {
        return new ResourceHolder<>(()
                -> Resources.image(inputStream));
    }
    
}