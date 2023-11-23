
package cz.hartrik.sg2.app.extension.dialog.script;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.script.ScriptEngineFactory;

/**
 * Formátuje položky objektu {@link ScriptEngineFactory}.
 * 
 * @version 2014-06-28
 * @author Patrik Harag
 */
public class EngineCellFactory implements
        Callback<ListView<ScriptEngineFactory>, ListCell<ScriptEngineFactory>> {

    @Override
    public ListCell<ScriptEngineFactory> call(
            ListView<ScriptEngineFactory> param) {

        return new ListCell<ScriptEngineFactory>() {

            @Override
            public void updateItem(ScriptEngineFactory item, boolean empty) {
                super.updateItem(item, empty);

                setText((empty || item == null)
                        ? null
                        : format(item));
            }
        };
    }

    protected String format(ScriptEngineFactory factory) {
        return factory.getLanguageName() + " (" + factory.getEngineName() + ")";
    }
    
}