package cz.hartrik.sg2.app.module.frame.module.options;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.engine.JFXRenderer;
import cz.hartrik.sg2.engine.JFXRendererBlur;
import cz.hartrik.sg2.engine.JFXRendererBlurCV;
import cz.hartrik.sg2.engine.JFXRendererCV;
import cz.hartrik.sg2.world.ChunkedArea;
import java.util.function.Function;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;

/**
 * @version 2015-01-09
 * @author Patrik Harag
 */
public class RendererTypeSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    public RendererTypeSubmodule() {
        super(true);
    }
    
    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {

        CheckMenuItem itemBlur = new CheckMenuItem("Motion blur");
        CheckMenuItem itemHC = new CheckMenuItem("Zvýraznit aktivní chunky");
        
        itemBlur.setSelected(true);
        
        itemBlur.selectedProperty().addListener((o, ov, selected) -> {
            controller.setRendererSupplier(select(selected, itemHC.isSelected()));
            controller.setUpCanvas(controller.getWorld());
        });
        
        itemHC.selectedProperty().addListener((o, ov, selected) -> {
            controller.setRendererSupplier(select(itemBlur.isSelected(), selected));
            controller.setUpCanvas(controller.getWorld());
        });
        
        return new MenuItem[] { itemBlur, itemHC };
    }

    protected Function<ChunkedArea, JFXRenderer> select(boolean blur,
            boolean highlight) {
        
        if (blur)
            return (highlight) ? JFXRendererBlurCV::new : JFXRendererBlur::new;
        else
            return (highlight) ? JFXRendererCV::new : JFXRenderer::new;
    }
    
}