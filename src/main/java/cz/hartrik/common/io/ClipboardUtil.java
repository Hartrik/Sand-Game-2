package cz.hartrik.common.io;

import cz.hartrik.common.reflect.LibraryClass;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Obsahuje statické metody pro práci se systémovou schránkou.
 * 
 * @version 1.3 /2014-12-18
 * @author Patrik Harag
 */
@LibraryClass
public final class ClipboardUtil {

    private ClipboardUtil() {}
    
    private static Clipboard getClipboard() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getSystemClipboard();
    }
    
    /**
     * Umístí požadovaný text do schránky.
     * 
     * @param text řetězec k vložení
     * @return úspěšnost operace
     */
    public static boolean copy(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = getClipboard();
        try {
            clipboard.setContents(selection, null);
            return true;
            
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * Vrátí text, který je ve schránce. Pokud schránka žádný neobsahuje, nebo
     * dojde k chybě, vrací <code>null</code>.
     * 
     * @return text ze schránky
     */
    public static String paste() {
        Clipboard clipboard = getClipboard();
        try {
            return clipboard.getData(DataFlavor.stringFlavor).toString();
        } catch (UnsupportedFlavorException | IOException ex) {
            return null;
        }
    }
    
    /**
     * Vyčistí schránku.
     */
    public static void clear() {
        copy("");
    }
    
}