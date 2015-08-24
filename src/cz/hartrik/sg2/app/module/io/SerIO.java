
package cz.hartrik.sg2.app.module.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Zprostředkuje io operace pomocí serializace.
 * 
 * @version 2015-01-15
 * @author Patrik Harag
 * @param <T> vstup/výstup
 */
public class SerIO<T> implements FileTypeIOProvider<T> {

    @Override
    public String getInfo() {
        return "Serializovaná data";
    }
    
    @Override
    public String getExtension() {
        return "ser";
    }

    @Override
    public void save(Path path, T data) throws IOException {
        writeData(data, new FileOutputStream(path.toFile()));
    }

    protected void writeData(T data, OutputStream out) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(data);
        }
    }
    
    @Override
    public T load(Path path) throws IOException {
        try {
            return load(new FileInputStream(path.toFile()));
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new ParseException(e);
        }
    }
    
    public T load(InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream iis = new ObjectInputStream(in)) {

            @SuppressWarnings("unchecked")
            T result = (T) iis.readObject();

            return result;
        }
    }

}