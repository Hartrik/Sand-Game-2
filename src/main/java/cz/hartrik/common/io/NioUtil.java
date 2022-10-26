
package cz.hartrik.common.io;

import cz.hartrik.common.reflect.LibraryClass;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Nástroje pro zjednodušení práce s NIO a NIO2. Některé metody víceméně pouze 
 * odstraňují nutnost odchytávat výjimky či jinak zjednodušují manipulaci se
 * soubory. To se hodí na místech, kde chyba není očekávána - např. při načítání
 * z resources atd.
 * 
 * @version 2014-08-05
 * @author Patrik Harag
 */
@LibraryClass
public final class NioUtil {
    
    private NioUtil() {}
    
    /**
     * Vrátí složku, ve které byl program spuštěn.
     * Odpovídá <code>System.getProperty("user.dir");</code>
     * 
     * @return složka
     */
    public static Path workingDirectory() {
        return Paths.get(System.getProperty("user.dir"));
    }

    /**
     * Odstraní z názvu souboru koncovku.<br/>
     * <code>soubor.txt</code> => <code>soubor</code><br/>
     * <code>soubor.01.txt</code> => <code>soubor.01</code>
     * 
     * @param fileName název souboru
     * @return název souboru bez koncovky
     */
    public static String removeExtension(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }
    
    // -- -- TODO
    
    public static String getExtension(Path path) {
        return getExtension(path.getFileName().toString());
    }
    
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        
        return (index > 0)
                ? fileName.substring(index + 1)
                : "";
    }
    
    /**
     * Převede cestu z formátu <code>xx/yy/zz/File</code> nebo 
     * <code>/xx/yy/zz/File</code> do formátu <code>xx.yy.zz.File</code>
     * 
     * @param slashedPath cesta, kde jsou jednotlivé členy odděleny lomítky
     * @return cesta, kde jsou jednotlivé členy odděleny tečkami
     */
    public static String dottedPath(String slashedPath) {
        return (slashedPath.startsWith("/") 
                ? slashedPath.substring(1) : slashedPath).replace("/", ".");
    }
    
    /**
     * Načte ze souboru řetězec ve formátu UTF-8.
     * 
     * @param path cesta k souboru
     * @return obsah souboru
     * @throws RuntimeException pokud soubor neexistuje, nelze načíst atd.
     */
    public static String readSmallTextFile(Path path) {
        try {
            final byte[] byteArray = Files.readAllBytes(path);
            return new String(byteArray, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Zapíše řetězec do souboru. Pokud soubor neexistuje, tak bude vytvořen.
     * 
     * @param path cesta k souboru
     * @param string řetězec k zapsání
     * @throws IllegalArgumentException pokud cesta ukazuje na adresář
     * @throws RuntimeException pokud soubor nelze vytvořit nebo neexistuje atd.
     */
    public static void writeSmallTextFile(Path path, String string) {
        if (Files.isDirectory(path))
            throw new IllegalArgumentException("cesta ukazuje na složku");
        
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                throw new RuntimeException("soubor nelze vytvořit");
            }
        }
        
        try {
            Files.write(path, string.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Vrátí seznam souborů a adresářů v adresáři.
     * 
     * @param dir adresář
     * @return seznam všech objektů v adresáři
     */
    public static List<Path> listAll(final Path dir) {
        final List<Path> list = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                list.add(dir.resolve(it.next()));
            }
        } catch (Exception e) {}
        return list;
    }
    
    /**
     * Vrátí seznam adresářů obsahující adresář.
     * 
     * @param dir adresář
     * @return seznam adresářů v adresáři
     */
    public static List<Path> listDirectories(final Path dir) {
        final List<Path> dirList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                Path next = dir.resolve(it.next());
                if (Files.isDirectory(next)) dirList.add(next);
            }
        } catch (Exception e) {}
        return dirList;
    }
    
    /**
     * Vrátí seznam souborů obsahující adresář.
     * 
     * @param dir adresář
     * @return seznam souborů v adresáři
     */
    public static List<Path> listFiles(final Path dir) {
        final List<Path> fileList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
                Path next = dir.resolve(it.next());
                if (Files.isRegularFile(next)) fileList.add(next);
            }
        } catch (Exception e) {}
        return fileList;
    }
    
}