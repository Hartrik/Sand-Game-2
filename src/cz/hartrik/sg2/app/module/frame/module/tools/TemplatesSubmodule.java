package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.brush.Brush;
import cz.hartrik.sg2.tool.ToolPasteTemplateOnce;
import cz.hartrik.sg2.world.element.solid.Iron;
import cz.hartrik.sg2.world.template.TemplateWPreview;
import cz.hartrik.sg2.world.template.ImageColorTemplate;
import cz.hartrik.sg2.world.template.ImageMapTemplate;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;

/**
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class TemplatesSubmodule extends MenuSubmodule<Frame, FrameController> {

    public TemplatesSubmodule() {
        super(false);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        Supplier<TemplateWPreview> suppCar = () -> 
                new ImageColorTemplate(
                        image("car section"),
                        c -> c.getFloatAlpha() < 0.4f ? null : new Iron(c));
        
        Supplier<TemplateWPreview> suppEiffel = () -> {
            Brush b = controller.getControls().getPrimaryBrush();
            Map<Color, Brush> map = Collections.singletonMap(Color.BLACK, b);
            
            return new ImageMapTemplate(image("Eiffel Tower"), map,
                    Brush.EMPTY_BRUSH);
        };
        
        Supplier<TemplateWPreview> suppCastle = () -> {
            Brush b = controller.getBrushManager().getBrush(15);
            Map<Color, Brush> map = Collections.singletonMap(Color.BLACK, b);
            
            return new ImageMapTemplate(
                    image("castle"), map, Brush.EMPTY_BRUSH);
        };
        
        Supplier<TemplateWPreview> suppBarr = () -> 
                new ImageColorTemplate(
                        image("barrel"),
                        c -> c.getFloatAlpha() < 0.4f ? null : new Iron(c));
        
        Menu menu = new Menu("Laboratorní nádoby");
        menu.getItems().addAll(
                new LabGlassSubmodule().createMenuItems(stage, controller, manager));
        
        return new MenuItem[] {
                menu,
                createItem(controller, "Šablona - auto (železo)", suppCar),
                createItem(controller, "Šablona - barel (železo)", suppBarr),
                createItem(controller, "Šablona - Eiffelova věž", suppEiffel),
                createItem(controller, "Šablona - zřícenina (kamenná zeď)", suppCastle),
        };
    }
    
    protected Image image(String fileName) {
        return Resources.image("template - " + fileName + ".png", getClass());
    }
    
    protected MenuItem createItem(FrameController controller,
            String text, Supplier<TemplateWPreview> supplier) {
        
        MenuItem item = new MenuItem(text);
        item.setOnAction((ActionEvent e) -> {
            Platform.runLater(() ->
                    ToolPasteTemplateOnce.initWithCancel(
                            supplier.get(), controller.getControls()));
        });
        
        return item;
    }
    
}