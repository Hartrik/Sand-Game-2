
package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.extension.canvas.ToolPasteTemplateOnce;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.RefractoryMetal;
import cz.hartrik.sg2.world.template.ImageColorTemplate;
import cz.hartrik.sg2.world.template.TemplateWPreview;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * Sub-modul do menu, který vytvoří položky s šablonami laboratorního vybavení.
 *
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class LabGlassSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {
        Controls controls = app.getControls();

        return new MenuItem[] {
                createItem(controls, "lg 1",   "1"),
                createItem(controls, "lg 2-1", "2"),
                createItem(controls, "lg 2-2", "3"),
                createItem(controls, "lg 3-1", "4"),
                createItem(controls, "lg 3-2", "5"),
                createItem(controls, "lg 3-3", "6"),
        };
    }

    private MenuItem createItem(Controls controls, String name, String accelerator) {
        String key = "module.tools.template." + name.replace(' ', '.');
        String title = Strings.get(key);

        return createItem(controls, name, title, accelerator);
    }

    private MenuItem createItem(Controls controls,
            String name, String title, String accelerator) {

        Function<Color, Element> function = c -> (c.getFloatAlpha() > 0.5f)
                ? new RefractoryMetal(c)
                : null;

        Supplier<TemplateWPreview> supp = () ->
               new ImageColorTemplate(template(name), function);

        return createItem(controls, title, icon(name), accelerator, supp);
    }

    private MenuItem createItem(Controls controls,
            String text, Image icon, String accelerator,
            Supplier<TemplateWPreview> supplier) {

        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        MenuItem item = new MenuItem(text, imageView);
        item.setAccelerator(KeyCombination.keyCombination(accelerator));
        item.setOnAction((ActionEvent e) -> {
            Platform.runLater(() -> {
                ToolPasteTemplateOnce.initWithCancel(supplier.get(), controls);
            });
        });

        return item;
    }

    protected Image template(String name) {
        return Resources.image("template - " + name + ".png", getClass());
    }

    protected Image icon(String name) {
        return Resources.image("icon - " + name + ".png", getClass());
    }

}