
package cz.hartrik.sg2.app.extension.dialog.script;

import cz.hartrik.common.io.NioUtil;
import cz.hartrik.common.io.Resources;
import cz.hartrik.common.ui.javafx.FXMLControlledStage;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Supplier;
import javafx.stage.Modality;
import javafx.stage.Window;

/**
 * Dialog s textovými poli pro zdrojový kód a výstup, combo boxem pro výběr
 * libovolného podporovaného skriptovacího enginu a tlačítky pro vyhodnocení
 * a zkopírování výstupu. <p>
 * 
 * Vyhodnocení je také možné provést zmáčknutím <code>ctrl + space</code>. <p>
 * 
 * Dialog může pokročilému uživateli umožnit vytvořit si vlastní výstup z
 * aplikace.
 * 
 * @version 2023-11-23
 * @author Patrik Harag
 */
public class ScriptDialog extends FXMLControlledStage<ScriptDialogController> {

    private static final String PACKAGE = "/cz/hartrik/sg2/app/extension/dialog/script/";
    
    public static final String PATH_FXML = PACKAGE + "ScriptDialog.fxml";
    public static final String PATH_FRAME_ICON = PACKAGE + "icon - script.png";
    public static final String PATH_PROPERTIES = NioUtil.dottedPath(PACKAGE) + "Strings";
    
    // --- konstruktory
    
    /**
     * Vytvoří novou instanci. Všechny parametry mohou být <code>null</code>.
     * 
     * @param owner nadřazené okno, může být null
     * @param bindings objekty, které budou ve skriptu přístupné
     *                 (název proměnné - objekt)
     * @param defaultEngine jméno enginu, který bude po zobrazení vybrán
     * @param defaultCode výchozí zdrojový kód
     */
    public ScriptDialog(Window owner, Map<String, Supplier<?>> bindings,
            String defaultEngine, String defaultCode) {
        
        super(ScriptDialog.class.getResource(PATH_FXML),
              ResourceBundle.getBundle(PATH_PROPERTIES));

        controller.bindings = bindings;
        controller.defaultCode = (defaultCode == null ? "" : defaultCode);
        controller.defaultEngine = defaultEngine;

        initOwner(owner);
        getIcons().setAll(Resources.image(PATH_FRAME_ICON));
        
        setMinWidth(500);
        setMinHeight(300);

        initModality(Modality.APPLICATION_MODAL);
    }
    
    // settery

    public void setPreCode(String code) {
        controller.preCode = code;
    }

    public void setBindings(Map<String, Supplier<?>> bindings) {
        controller.bindings = bindings;
    }
    
    public void addBindings(Map<String, Supplier<?>> bindings) {
        controller.bindings.putAll(bindings);
    }
}