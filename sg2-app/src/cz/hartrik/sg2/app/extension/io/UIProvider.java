
package cz.hartrik.sg2.app.extension.io;

/**
 * Rozhraní, které zajišťuje uživatelské rozhraní správce vstupních/výstupních
 * operací. Může fungovat i bez ohledu na typ ukládaných dat.
 * 
 * @version 2015-01-12
 * @author Patrik Harag
 * @param <T> typ objektu, který je nutný pro výstup - např. hlavní okno, ke
 *            kterému bude vytvořen dialog; nějaký proud atd...
 */
public interface UIProvider<T> {
    
    void onSaveIOException(Exception e, T context);
    
    void onLoadIOException(Exception e, T context);
    
    void onLoadParseException(ParseException e, T context);
    
    boolean newFile(T context);
    
    IFileChooser<T> getFileChooser();
    
}