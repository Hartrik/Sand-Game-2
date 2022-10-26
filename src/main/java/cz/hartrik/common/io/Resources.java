
package cz.hartrik.common.io;

import cz.hartrik.common.Exceptions;
import cz.hartrik.common.reflect.TODO;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.image.Image;

/**
 * Poskytuje statické metody pro pohodlné načítání dat z obsahu jar.
 * 
 * @version 2015-02-07
 * @author Patrik Harag
 */
@TODO
public class Resources {
    
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;
    
    public static final LazyResources lazy = new LazyResources();
    
    public static String absolutePath(String fileName, Class<?> context) {
        return new StringBuilder("/")
                .append(context.getPackage().getName().replace(".", "/"))
                .append("/")
                .append(fileName)
                .toString();
    }
    
    // --- lines ---
    
    // absolutní cesta
    
    public static Stream<String> lines(String absolutePath) {
        return lines(absolutePath, DEFAULT_ENCODING);
    }
    
    public static Stream<String> lines(String absolutePath, Charset charset) {
        return lines(System.class.getResourceAsStream(absolutePath), charset);
    }
    
    // relativní cesta
    
    public static Stream<String> lines(String fileName, Class<?> context) {
        return lines(fileName, context, DEFAULT_ENCODING);
    }
    
    public static Stream<String> lines(String fileName, Class<?> context,
            Charset charset) {
        
        return lines(absolutePath(fileName, context), charset);
    }
    
    // proud
    
    public static Stream<String> lines(InputStream is) {
        return lines(is, DEFAULT_ENCODING);
    }
    
    public static Stream<String> lines(InputStream is, Charset charset) {
        InputStreamReader isr = new InputStreamReader(is, charset);
        BufferedReader br = new BufferedReader(isr);
        
        try {
            return br.lines().onClose(() -> closeStream(br));
        } catch (Error | RuntimeException e) {
            try {
                br.close();
            } catch (IOException ex) {
                try {
                    e.addSuppressed(ex);
                } catch (Throwable ignore) {}
            }
            throw e;
        }
    }
    
    private static void closeStream(Closeable closeable) {
        Exceptions.unchecked(closeable::close);
    }
    
    // --- text
    
    // absolutní cesta
    
    public static String text(String absolutePath) {
        return text(absolutePath, StandardCharsets.UTF_8);
    }
    
    public static String text(String absolutePath, Charset charset) {
        return text(System.class.getResourceAsStream(absolutePath), charset);
    }
    
    // relativní cesta
    
    public static String text(String fileName, Class<?> context) {
        return text(fileName, context, DEFAULT_ENCODING);
    }
    
    public static String text(String fileName, Class<?> context,
            Charset charset) {
        
        return text(absolutePath(fileName, context), charset);
    }
    
    // proud
    
    public static String text(InputStream is) {
        return text(is, DEFAULT_ENCODING);
    }
    
    public static String text(InputStream is, Charset charset) {
        return lines(is, charset)
                .collect(Collectors.joining("\n"));
    }
    
    // --- image
    
    // absolutní cesta
    
    public static Image image(String absolutePath) {
        return new Image(absolutePath);
    }
    
    // relativní cesta
    
    public static Image image(String fileName, Class<?> context) {
        return image(absolutePath(fileName, context));
    }
    
    // proud
    
    public static Image image(InputStream is) {
        return new Image(is);
    }
    
}