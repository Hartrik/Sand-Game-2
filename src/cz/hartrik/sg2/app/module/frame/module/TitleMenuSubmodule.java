package cz.hartrik.sg2.app.module.frame.module;

import cz.hartrik.sg2.app.module.frame.Application;
import javafx.geometry.Insets;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Sub-modul do menu, který přidává nadpis.
 *
 * @author Patrik Harag
 * @version 2016-07-10
 */
public class TitleMenuSubmodule implements MenuSubmodule {

    private final String title;

    public TitleMenuSubmodule(String title) {
        this.title = title;
    }

    @Override
    public MenuItem[] createMenuItems(Application app) {
        final Label lTitle = new Label(title);
        Font def = Font.getDefault();
        Font font = Font.font(def.getFamily(), FontWeight.BOLD, def.getSize());
        lTitle.setFont(font);

        final VBox box = new VBox(lTitle);
        box.setPadding(new Insets(0, 5, 0, 5));

        return new MenuItem[] { new CustomMenuItem(box, false) };
    }

}