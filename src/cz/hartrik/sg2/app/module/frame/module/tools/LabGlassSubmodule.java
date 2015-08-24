package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.common.Color;
import cz.hartrik.common.io.Resources;
import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.tool.ToolPasteTemplateOnce;
import cz.hartrik.sg2.world.Element;
import cz.hartrik.sg2.world.element.solid.RefractoryMetal;
import cz.hartrik.sg2.world.template.TemplateWPreview;
import cz.hartrik.sg2.world.template.ImageColorTemplate;
import java.util.function.Function;
import java.util.function.Supplier;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;

/**
 * Submodul, který vytvoří menu s šablonami laboratorního vybavení.
 * 
 * @version 2015-03-07
 * @author Patrik Harag
 */
public class LabGlassSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    public LabGlassSubmodule() {
        super(false);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        return new MenuItem[] {
                createItem(controller, "lg 1",   "Zkumavka",           "1"),
                createItem(controller, "lg 2-1", "Baňka - kuželová 1", "2"),
                createItem(controller, "lg 2-2", "Baňka - kuželová 2", "3"),
                createItem(controller, "lg 3-1", "Baňka - kulatá 1",   "4"),
                createItem(controller, "lg 3-2", "Baňka - kulatá 2",   "5"),
                createItem(controller, "lg 3-3", "Baňka - kulatá 3",   "6"),
        };
    }
    
    protected MenuItem createItem(FrameController controller,
            String text, Image icon, String accelerator,
            Supplier<TemplateWPreview> supplier) {
        
        ImageView imageView = new ImageView(icon);
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        
        MenuItem item = new MenuItem(text, imageView);
        item.setAccelerator(KeyCombination.keyCombination(accelerator));
        item.setOnAction((ActionEvent e) -> {
            Platform.runLater(() ->
                    ToolPasteTemplateOnce.initWithCancel(
                            supplier.get(), controller.getControls()));
        });
        
        return item;
    }
    
    protected MenuItem createItem(FrameController controller,
            String name, String title, String accelerator) {
        
        Function<Color, Element> function = c -> (c.getFloatAlpha() > 0.5f)
                ? new RefractoryMetal(c)
                : null;
        
        Supplier<TemplateWPreview> supp = () -> 
               new ImageColorTemplate(template(name), function);
         
        return createItem(controller, title, icon(name), accelerator, supp);
    }
    
    protected Image template(String name) {
        return Resources.image("template - " + name + ".png", getClass());
    }
    
    protected Image icon(String name) {
        return Resources.image("icon - " + name + ".png", getClass());
    }
    
}