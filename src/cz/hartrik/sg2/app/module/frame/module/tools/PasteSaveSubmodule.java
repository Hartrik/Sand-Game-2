package cz.hartrik.sg2.app.module.frame.module.tools;

import cz.hartrik.sg2.app.module.frame.Frame;
import cz.hartrik.sg2.app.module.frame.FrameController;
import cz.hartrik.sg2.app.module.frame.module.MenuSubmodule;
import cz.hartrik.sg2.app.module.frame.module.ServiceManager;
import cz.hartrik.sg2.app.module.io.IOProvider;
import cz.hartrik.sg2.app.module.io.UIProvider;
import cz.hartrik.sg2.brush.Controls;
import cz.hartrik.sg2.tool.ToolPasteTemplateOnce;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.template.ElementAreaTemplate;
import java.nio.file.Path;
import javafx.scene.control.MenuItem;
import javafx.stage.Window;

/**
 * @version 2015-04-07
 * @author Patrik Harag
 */
public class PasteSaveSubmodule extends MenuSubmodule<Frame, FrameController> {
    
    private final IOProvider<? extends ElementArea> ioProvider;
    private final UIProvider<Window> uiProvider;

    public PasteSaveSubmodule(
            IOProvider<? extends ElementArea> ioProvider,
            UIProvider<Window> uiProvider) {
        
        super(false);
        this.ioProvider = ioProvider;
        this.uiProvider = uiProvider;
    }

    @Override
    public MenuItem[] createMenuItems(Frame stage, FrameController controller,
            ServiceManager manager) {
        
        MenuItem menuItem = new MenuItem("Vložit z uložené pozice");
        menuItem.setOnAction(event -> {
            controller.getSyncTools().pauseBothLazy(() -> {
                Path openFile = uiProvider.getFileChooser().openFile(stage);
                if (openFile == null) return;

                ElementArea area;
                try {
                    area = ioProvider.getProvider(openFile).get().load(openFile);
                } catch (Exception ex) {
                    uiProvider.onLoadIOException(ex, stage);
                    return;
                }

                Controls controls = controller.getControls();
                ElementAreaTemplate template = new ElementAreaTemplate(area);
                ToolPasteTemplateOnce.initWithCancel(template, controls);
            });
        });
        
        return new MenuItem[] { menuItem };
    }
    
}