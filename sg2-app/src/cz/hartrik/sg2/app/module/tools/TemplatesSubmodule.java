
package cz.hartrik.sg2.app.module.tools;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.Application;
import cz.hartrik.sg2.app.Strings;
import cz.hartrik.sg2.app.extension.canvas.ToolPasteTemplateOnce;
import cz.hartrik.sg2.app.module.MenuSubmodule;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.world.element.solid.Iron;
import cz.hartrik.sg2.world.template.ImageColorTemplate;
import cz.hartrik.sg2.world.template.ImageMapTemplate;
import cz.hartrik.sg2.world.template.TemplateWPreview;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * Sub-modul do menu, přidávající šablony.
 *
 * @version 2017-07-05
 * @author Patrik Harag
 */
public class TemplatesSubmodule implements MenuSubmodule {

    @Override
    public MenuItem[] createMenuItems(Application app) {

        String textCar = Strings.get("module.tools.template.car");
        Supplier<TemplateWPreview> suppCar = () ->
                new ImageColorTemplate(
                        image("car section"),
                        c -> c.getFloatAlpha() < 0.4f ? null : new Iron(c));

        String textEiffel = Strings.get("module.tools.template.eiffel");
        Supplier<TemplateWPreview> suppEiffel = () -> {
            Brush b = app.getControls().getPrimaryBrush();
            Map<Color, Brush> map = Collections.singletonMap(Color.BLACK, b);

            return new ImageMapTemplate(image("Eiffel Tower"), map,
                    Brush.EMPTY_BRUSH);
        };

        String textCastle = Strings.get("module.tools.template.castle");
        Supplier<TemplateWPreview> suppCastle = () -> {
            Brush b = app.getBrushManager().getBrush(15);
            Map<Color, Brush> map = Collections.singletonMap(Color.BLACK, b);

            return new ImageMapTemplate(image("castle"), map, Brush.EMPTY_BRUSH);
        };

        String textBarrel = Strings.get("module.tools.template.barrel");
        Supplier<TemplateWPreview> suppBarrel = () ->
                new ImageColorTemplate(
                        image("barrel"),
                        c -> c.getFloatAlpha() < 0.4f ? null : new Iron(c));

        Menu menu = new Menu(Strings.get("module.tools.template.lg"));
        menu.getItems().addAll(new LabGlassSubmodule().createMenuItems(app));

        Controls controls = app.getControls();

        return new MenuItem[] {
                menu,
                createItem(controls, textCar, suppCar),
                createItem(controls, textBarrel, suppBarrel),
                createItem(controls, textEiffel, suppEiffel),
                createItem(controls, textCastle, suppCastle),
        };
    }

    private Image image(String fileName) {
        return Resources.image("template - " + fileName + ".png", getClass());
    }

    private MenuItem createItem(Controls controls, String text,
                                Supplier<TemplateWPreview> supplier) {

        MenuItem item = new MenuItem(text);
        item.setOnAction((ActionEvent e) -> {
            Platform.runLater(() -> {
                ToolPasteTemplateOnce.initWithCancel(supplier.get(), controls);
            });
        });

        return item;
    }

}