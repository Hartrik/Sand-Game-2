package cz.hartrik.sg2.app.module.frame.module.script;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * 
 * 
 * @version 2015-03-13
 * @author Patrik Harag
 */
public class FileDictionaryComparator implements Comparator<Path> {

    @Override
    public int compare(Path path1, Path path2) {
        return comparePath(path1, path2);
    }
    
    public static int comparePath(Path path1, Path path2) {
        if (Files.isDirectory(path1))
            return (Files.isDirectory(path2)) ? 0 : -1;
        else
            return (Files.isDirectory(path2)) ? 1 : 0;
    }
    
}