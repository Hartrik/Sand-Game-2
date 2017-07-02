package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.script.ToolFactory;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.Randomizer;
import cz.hartrik.sg2.tool.Shape;
import cz.hartrik.sg2.tool.Tool;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 * Sub-modul do menu, který přidá nástroj randomizér.
 *
 * @version 2015-03-29
 * @author Patrik Harag
 */
public class RandomizerSubmodule implements MenuSubmodule {

    private ChangeListener<Tool> listener;

    @Override
    public MenuItem[] createMenuItems(Application app) {

        Controls controls = app.getControls();
        ToolFactory factory = ToolFactory.getInstance();

        this.listener = (o, ov, selected) -> {
            if (selected instanceof Shape)
                controls.setPrimaryTool(factory.randomizer((Shape) selected));
        };

        CheckMenuItem item = new CheckMenuItem("Povolit randomizaci");
        item.setSelected(false);
        item.selectedProperty().addListener((o, ov, selected) -> {
            if (ov == selected) return;

            if (selected) {
                listener.changed(null, null, controls.getPrimaryTool());
                controls.primaryToolProperty().addListener(listener);
            } else {
                controls.primaryToolProperty().removeListener(listener);

                Tool primary = controls.getPrimaryTool();
                if (primary instanceof Randomizer)
                    controls.setPrimaryTool(((Randomizer) primary).getShape());
            }
        });

        return new MenuItem[] { item };
    }

}