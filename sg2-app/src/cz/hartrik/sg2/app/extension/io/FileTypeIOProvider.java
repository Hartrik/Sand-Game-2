
package cz.hartrik.sg2.app.extension.io;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Rozhraní pro třídu zprostředkovávající jeden druh IO operací.
 * 
 * @version 2015-01-12
 * @author Patrik Harag
 * @param <T> druh načítaných / ukládaných dat
 */
public interface FileTypeIOProvider<T> {

    String getInfo();

    String getExtension();

    void save(Path path, T data) throws IOException;

    T load(Path path) throws IOException, ParseException;

}